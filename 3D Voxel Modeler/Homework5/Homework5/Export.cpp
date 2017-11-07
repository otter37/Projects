#define _CRT_SECURE_NO_WARNINGS


#include <stdio.h>
#include <stdint.h>
#include <iostream>
#include "VoxelShape.h"
enum FaceType {
	NX,
	NY,
	NZ,
	PX,
	PY,
	PZ
};

struct Triangle {
	float normal[3];
	float v1[3];
	float v2[3];
	float v3[3];
};


inline void fillPlane(int a1, int a2, int b1, int b2, int c, int cInd, Triangle& t1, Triangle& t2) {
	t1.v1[cInd] = c;
	t2.v1[cInd] = c;
	t1.v2[cInd] = c;
	t2.v2[cInd] = c;
	t1.v3[cInd] = c;
	t2.v3[cInd] = c;
	int aInd = (cInd + 1) % 3;
	int bInd = (cInd + 2) % 3;

	t1.v1[aInd] = a1;
	t1.v2[aInd] = a2;
	t1.v3[aInd] = a2;

	t2.v1[aInd] = a1;
	t2.v2[aInd] = a2;
	t2.v3[aInd] = a1;

	t1.v1[bInd] = b1;
	t1.v2[bInd] = b1;
	t1.v3[bInd] = b2;

	t2.v1[bInd] = b1;
	t2.v2[bInd] = b2;
	t2.v3[bInd] = b2;

}

void extractFace(int x, int y, int z, FaceType face, Triangle& t1, Triangle& t2) {
	for (int i = 0; i < 3; i++) {
		t1.normal[i] = 0;
		t2.normal[i] = 0;
	}
	switch (face) {
	case NX:
		t1.normal[0] = -1;
		t2.normal[0] = -1;
		fillPlane(y + 1, y, z, z + 1, x, 0, t1, t2);
		break;
	case NY:
		t1.normal[1] = -1;
		t2.normal[1] = -1;
		fillPlane(z + 1, z, x, x + 1, y, 1, t1, t2);
		break;
	case NZ:
		t1.normal[2] = -1;
		t2.normal[2] = -1;
		fillPlane(x + 1, x, y, y + 1, z, 2, t1, t2);
		break;
	case PX:
		t1.normal[0] = 1;
		t2.normal[0] = 1;
		fillPlane(y, y + 1, z, z + 1, x + 1, 0, t1, t2);
		break;
	case PY:
		t1.normal[1] = 1;
		t2.normal[1] = 1;
		fillPlane(z, z + 1, x, x + 1, y + 1, 1, t1, t2);
		break;
	case PZ:
		t1.normal[2] = 1;
		t2.normal[2] = 1;
		fillPlane(x, x + 1, y, y + 1, z + 1, 2, t1, t2);
		break;
	}
}

void writeTriangle(FILE* f, const Triangle& t) {
	fwrite(t.normal, sizeof(float), 3, f);
	fwrite(t.v1, sizeof(float), 3, f);
	fwrite(t.v2, sizeof(float), 3, f);
	fwrite(t.v3, sizeof(float), 3, f);
	uint16_t zero = 0;
	fwrite(&zero, sizeof(zero), 1, f);
}

void writeSTL(VoxelShape model, const char* filename) {

	FILE* f = fopen(filename, "wb");
	uint8_t header[80] = { 0 };
	fwrite(header, sizeof(uint8_t), 80, f);
	uint32_t numTris = 0;
	fwrite(&numTris, sizeof(numTris), 1, f);
	Triangle t1, t2;
	int numX, numY, numZ;
	model.getNumXYZ(numX, numY, numZ);
	for (int x = 0; x < numX; x++) {
		for (int y = 0; y < numY; y++) {
			for (int z = 0; z < numZ; z++) {
				if (model.getBit(x, y, z)) {
					if (((x - 1) < 0) || !model.getBit(x - 1, y, z)) {
						extractFace(x, y, z, NX, t1, t2);
						writeTriangle(f, t1);
						writeTriangle(f, t2);
						numTris += 2;
					}

					if (((x + 1) > numX - 1) || !model.getBit(x + 1, y, z)) {
						extractFace(x, y, z, PX, t1, t2);
						writeTriangle(f, t1);
						writeTriangle(f, t2);
						numTris += 2;
					}

					if (((y - 1) < 0) || !model.getBit(x, y - 1, z)) {
						extractFace(x, y, z, NY, t1, t2);
						writeTriangle(f, t1);
						writeTriangle(f, t2);
						numTris += 2;
					}
					if (((y + 1) > numY - 1) || !model.getBit(x, y + 1, z)) {
						extractFace(x, y, z, PY, t1, t2);
						writeTriangle(f, t1);
						writeTriangle(f, t2);
						numTris += 2;
					}


					if ((z - 1 < 0) || !model.getBit(x, y, z - 1)) {

						extractFace(x, y, z, NZ, t1, t2);
						writeTriangle(f, t1);
						writeTriangle(f, t2);
						numTris += 2;

					}

					if ((z + 1 > numZ - 1) || !model.getBit(x, y, z + 1)) {
						extractFace(x, y, z, PZ, t1, t2);
						writeTriangle(f, t1);
						writeTriangle(f, t2);
						numTris += 2;

					}

				}
			}
		}
	}
	fseek(f, 80, SEEK_SET);
	fwrite(&numTris, sizeof(numTris), 1, f);
	fclose(f);

}
