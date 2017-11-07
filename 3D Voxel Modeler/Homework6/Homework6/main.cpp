#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include "BitVector.h"
#include "VoxelShape.h"
#include "Export.h"

int main(int argc, char** argv) {

	VoxelShape a = VoxelShape(100,100,100);


	a.fillShape();
	for (int x = 0; x < 4; x++) {
		for (int y = 0; y < 4; y++) {
			for (int z = 0; z < 4; z++) {
				std::cout << x << ", " << y << ", " << z << " = " << a.getBit(x, y, z) << std::endl;
			}
		}
	}

	a.clearShape();
	for (int x = 0; x < 4; x++) {
		for (int y = 0; y < 4; y++) {
			for (int z = 0; z < 4; z++) {
				std::cout << x << ", " << y << ", " << z << " = " << a.getBit(x, y, z) << std::endl;
			}
		}
	}

	a.fillSphere(25, 25, 25, 20);
	a.setBit(0, 0, 0);
	a.setBit(50, 50, 50);
	a.setBit(0, 50, 50);
	a.setBit(50, 50, 0);
	a.setBit(50, 0, 50);

	writeSTL(a, "testPLS.stl");


	int ender;
	std::cin >> ender;
}