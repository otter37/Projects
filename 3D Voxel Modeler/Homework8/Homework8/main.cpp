
#include <iostream>
#include "CSG.h"
#include "Timer.h"

void RenderBallInBall()
{
    CSG csg;
    Point3D middle = { 0.0, 0.0, 0.0 };
    Point3D lb = { -1, -1, -1 };
    Point3D ub = { 1, 1, 1 };
    
    // Initial hollow sphere
    csg += new Sphere(middle, 1.0);
    csg ^= new Sphere(middle, 0.76);
    
    // 5 of 6 sides cut out (not bottom)
    csg -= new Sphere({ 1.0, 0.0,  0.0 }, 0.4);
    csg -= new Sphere({ -1.0, 0.0,  0.0 }, 0.4);
    csg -= new Sphere({ 0.0,  1.0,  0.0 }, 0.4);
    csg -= new Sphere({ 0.0, -1.0,  0.0 }, 0.4);
    csg -= new Sphere({ 0.0,  0.0,  1.0 }, 0.4);
    
    // Trim edges with cube
    csg &= new Cube(middle, 0.8);
    
    // Add floating ball in middle
    csg += new Sphere(middle, 0.44);
    
    Timer t;
    t.StartTimer();
    csg.RenderToFile("test.stl", lb, ub, 2.0, 64);
    t.EndTimer();
    printf("%1.2fs total time elapsed for test.stl\n", t.GetElapsedTime());
    
}

void ModelCar() {
	CSG car;
	car += new Cube({ 6,10,10 }, 10);
	car += new Cube({ 15,10,9 }, 8);
	car += new Sphere({ 4, 5, 4 }, 3);
	car += new Sphere({ 14, 5, 4 }, 3);	
	car += new Sphere({ 4, 15, 4 }, 3);
	car += new Sphere({ 14, 15, 4 }, 3);



    Timer t;
    t.StartTimer();
    car.RenderToFile("car.stl", { 0,0,0 }, { 20,20,20 }, 5, 20);
    t.EndTimer();
    printf("%1.2fs total time elapsed to build ModelCar\n", t.GetElapsedTime());
}

int main(int argn, char** argv) {
    ModelCar();
    RenderBallInBall();
    return 0;
}
