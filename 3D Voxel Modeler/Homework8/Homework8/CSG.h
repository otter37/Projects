#pragma once
#include <vector>
#include "DetailedShapes.h"

enum Operation
{
	ADD, SUBTRACT, MASK, TOGGLE
};
struct ShapeData
{
	Shape* shape;
	Operation opr;
};

class CSG {
public:
	CSG();
	CSG(const CSG &);
	CSG operator=(const CSG & otherCSG);
	~CSG();
	void RenderToFile(const char *filename, Point3D min, Point3D max, float widthInInches, int voxelsPerInch = 256);
	void Add(Shape *);
	void Subtract(Shape *);
	void Mask(Shape *);
	void Toggle(Shape *);

	CSG &operator+=(Shape *);
	CSG &operator-=(Shape *);
	CSG &operator&=(Shape *);
	CSG &operator^=(Shape *);
private:
	std::vector<ShapeData> shapes;
	bool getVoxel(int x, int y, int z, Point3D min, Point3D max, float widthInInches, int voxelsPerInch);
};