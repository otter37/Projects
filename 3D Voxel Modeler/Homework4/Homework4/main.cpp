#define _CRT_SECURE_NO_WARNINGS

//
//  main.cpp
//  Homework2
//
//  Created by Ben Jones on 1/4/17.
//  Copyright © 2017 Ben Jones. All rights reserved.
//

#include <iostream>

#include <random>
#include <cassert>
#include "voxelModel.hpp"
#include "surfaceExtraction.hpp"

int main(int argc, const char * argv[]) {
    
	VoxelModel m1 = allocateModel(100,100,100);
	fillModel(m1);
	clearModel(m1);
	//fillModel(m1);
	addSphere(m1, 50, 30, 30, 20);
	subtractSphere(m1, 50, 30, 15, 10);
	subtractSphere(m1, 50, 30, 45, 10);

	writeSTL(m1, "test.stl");
   
    
}
