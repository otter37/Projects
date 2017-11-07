#define _CRT_SECURE_NO_WARNINGS


#include <assert.h>
#include <iostream>
#include "CSG.h"
#include "surfaceExtraction.hpp"

int writeTriangle(FILE* pFILE, Triangle &triangle)
{
	if (fwrite(triangle.normal, sizeof(float), 3, pFILE) != 3)
		return 1;
	if (fwrite(triangle.v1, sizeof(float), 3, pFILE) != 3)
		return 1;
	if (fwrite(triangle.v2, sizeof(float), 3, pFILE) != 3)
		return 1;
	if (fwrite(triangle.v3, sizeof(float), 3, pFILE) != 3)
		return 1;
	uint16_t endOfTri = ((int)(triangle.v1)[0] % 32 << 16) + ((int)(triangle.v1)[1] % 32 << 8) + ((int)(triangle.v1)[2] % 32);
	if (fwrite(&endOfTri, sizeof(uint16_t), 1, pFILE) != 1)
		return 1;
	return 0;
}


CSG::CSG() {};

CSG::CSG(const CSG & other)
{
	shapes = other.shapes;
}

CSG CSG::operator=(const CSG & otherCSG)
{
	if (this == &otherCSG)
		return *this;
	shapes = otherCSG.shapes;
	return *this;
}


CSG::~CSG() {
	while (!shapes.empty()) {
		delete (shapes.back().shape);
		shapes.pop_back();
	}
};


bool CSG::getVoxel(int x, int y, int z, Point3D min, Point3D max, float widthInInches, int voxelsPerInch)
{
	bool voxel = false;
	
	Point3D point = { widthInInches / voxelsPerInch * x + min.x,
						widthInInches / voxelsPerInch * y + min.y,
						widthInInches / voxelsPerInch * z + min.z};

	for (int i = 0; i < shapes.size(); i++) {
		switch (shapes[i].opr) {
		case ADD:
			voxel += shapes[i].shape->InsideShape(point);
			break;
		case SUBTRACT:
			voxel = false;
			break;
		case MASK:
			voxel &= shapes[i].shape->InsideShape(point);
			break;
		case TOGGLE:
			voxel = !(shapes[i].shape->InsideShape(point));
		}
	}
				

	return voxel;
}

void CSG::RenderToFile(const char * filename, Point3D min, Point3D max, float widthInInches, int voxelsPerInch)
{
	int maxX = (max.x - min.x) / widthInInches * voxelsPerInch;
	int maxY = (max.y - min.y) / widthInInches * voxelsPerInch;
	int maxZ = (max.z - min.z) / widthInInches * voxelsPerInch;

	FILE *pFILE = fopen(filename, "wb");
	assert(pFILE != nullptr);
	uint8_t header = 0;
	for (int i = 0; i < 80; i++)
		assert(fwrite(&header, sizeof(uint8_t), 1, pFILE) == 1);
	uint32_t triNum = 0;
	assert(fwrite(&triNum, sizeof(uint32_t), 1, pFILE) == 1);
	for (int x = 0; x<maxX; x++)
		for (int y = 0; y<maxY; y++)
			for (int z = 0; z<maxZ; z++)
			{
				if (getVoxel(x,y,z,min,max,widthInInches,voxelsPerInch)) {
					if (x - 1 < 0 || !getVoxel(x - 1, y, z, min, max, widthInInches, voxelsPerInch))
					{
						Triangle t1, t2;
						extractFace(x, y, z, NX, t1, t2);
						writeTriangle(pFILE, t1);
						writeTriangle(pFILE, t2);
						triNum += 2;
					}
					if (y - 1 < 0 || !getVoxel(x, y - 1, z, min, max, widthInInches, voxelsPerInch))
					{
						Triangle t1, t2;
						extractFace(x, y, z, NY, t1, t2);
						writeTriangle(pFILE, t1);
						writeTriangle(pFILE, t2);
						triNum += 2;
					}
					if (z - 1 < 0 || !getVoxel(x, y, z - 1, min, max, widthInInches, voxelsPerInch))
					{
						Triangle t1, t2;
						extractFace(x, y, z, NZ, t1, t2);
						writeTriangle(pFILE, t1);
						writeTriangle(pFILE, t2);
						triNum += 2;
					}
					if (x + 1 >= maxX || !getVoxel(x + 1, y, z, min, max, widthInInches, voxelsPerInch))
					{
						Triangle t1, t2;
						extractFace(x, y, z, PX, t1, t2);
						writeTriangle(pFILE, t1);
						writeTriangle(pFILE, t2);
						triNum += 2;
					}
					if (y + 1 >= maxY || !getVoxel(x, y + 1, z, min, max, widthInInches, voxelsPerInch))
					{
						Triangle t1, t2;
						extractFace(x, y, z, PY, t1, t2);
						writeTriangle(pFILE, t1);
						writeTriangle(pFILE, t2);
						triNum += 2;
					}
					if (z + 1 >= maxZ || !getVoxel(x, y, z + 1, min, max, widthInInches, voxelsPerInch))
					{
						Triangle t1, t2;
						extractFace(x, y, z, PZ, t1, t2);
						writeTriangle(pFILE, t1);
						writeTriangle(pFILE, t2);
						triNum += 2;
					}
				}
			}
	fseek(pFILE, 80, SEEK_SET);
	assert(fwrite(&triNum, sizeof(uint32_t), 1, pFILE) == 1);
	fclose(pFILE);

}

void CSG::Add(Shape * inputShape)
{
	shapes.push_back({inputShape, ADD});
}

void CSG::Subtract(Shape * inputShape)
{
	shapes.push_back({ inputShape, SUBTRACT });
}

void CSG::Mask(Shape * inputShape)
{
	shapes.push_back({inputShape, MASK});
}

void CSG::Toggle(Shape * inputShape)
{
	shapes.push_back({ inputShape, TOGGLE });
}

CSG & CSG::operator+=(Shape * inputShape)
{
	this->Add(inputShape);
	return (*this);
}

CSG & CSG::operator-=(Shape * inputShape)
{
	this->Subtract(inputShape);
	return (*this);
}

CSG & CSG::operator&=(Shape *inputShape)
{

	this->Mask(inputShape);
	return (*this);
}

CSG & CSG::operator^=(Shape *inputShape)
{

	this->Toggle(inputShape);
	return (*this);
}