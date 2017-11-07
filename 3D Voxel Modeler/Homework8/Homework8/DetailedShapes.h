#pragma once
#include "Shape.h"

class Line : public Shape {
public:
	Line(Point3D p1, Point3D p2, float r);
	bool InsideShape(Point3D p);
private:
	float magnitude(Point3D p);
	Point3D cross(Point3D p1, Point3D p2);
	Point3D p1, p2;
	float r;
};

class Cube :public Shape {
public:
	Cube(Point3D middle, float length);
	virtual bool InsideShape(Point3D p);
private:
	Point3D mid;
	float halfLength;
};

class Sphere :public Shape {
public:
	Sphere(Point3D middle, float radius);
	virtual bool InsideShape(Point3D p);
	~Sphere() {};
private:
	Point3D middle;
	float r;
};