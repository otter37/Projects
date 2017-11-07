#include "DetailedShapes.h"
#include <cmath>
//Cube
Cube::Cube(Point3D middle, float length)
{
	this->mid = middle;
	this->halfLength = length / 2;
}

bool Cube::InsideShape(Point3D p)
{
	return (p.x >= (mid.x - halfLength) && p.y >= (mid.y - halfLength) &&
		p.z >= (mid.z - halfLength) &&
		p.x <= (mid.x + halfLength) && p.y <= (mid.y + halfLength) &&
		p.z <= (mid.z + halfLength));
}

// Line
Line::Line(Point3D p1, Point3D p2, float r)
	:p1(p1), p2(p2), r(r)
{
}

bool Line::InsideShape(Point3D p0)
{
	Point3D a = { p0.x - p1.x, p0.y - p1.y, p0.z - p1.z };
	Point3D b = { p0.x - p2.x, p0.y - p2.y, p0.z - p2.z };
	Point3D c = { p2.x - p1.x, p2.y - p1.y, p2.z - p1.z };
	float dist = magnitude(cross(a, b)) / magnitude(c);
	return dist <= r;
}

float Line::magnitude(Point3D p)
{
	return sqrt(p.x * p.x + p.y * p.y + p.z * p.z);
}

Point3D Line::cross(Point3D p1, Point3D p2)
{
	Point3D result;
	result.x = p1.y*p2.z - p1.z*p2.y;
	result.y = p1.z*p2.x - p1.x*p2.z;
	result.z = p1.x*p2.y - p1.y*p2.x;
	return result;
}

//Sphere
Sphere::Sphere(Point3D middle, float radius)
{
	this->middle = middle;
	this->r = radius;
}

bool Sphere::InsideShape(Point3D p)
{
	return (pow(p.x - middle.x, 2) + pow(p.y - middle.y, 2) + pow(p.z - middle.z, 2) <= r*r);
}
