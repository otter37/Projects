#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include "BitVector.h"
#include "VoxelShape.h"
#include "Export.h"

int main(int argc, char** argv) {

	//Testing resize
	BitVector v(10);
	std::cout << "Bit 5 after initialization = " << v.getBit(5) << std::endl;
	v.set(5,1);
	std::cout << "Bit 5 after setting = " << v.getBit(5) << std::endl;
	v.resize(50);
	std::cout << "After resizing to 50 bit array, bit 25 = " << v.getBit(25) << std::endl;
	v.set(25, 1);
	std::cout << "After setting bit 25, it = " << v.getBit(25) << std::endl;

	//Testing VoxelShape set, clear, sphere
	VoxelShape a = VoxelShape(50,50,50);

	for (int x = 0; x < 50; x++) {
		for (int y = 0; y < 50; y++) {
			for (int z = 0; z < 50; z++) {
				if (x < 10 && y < 20 && z < 25) {
					a.setBit(x, y, z);
				}
			}
		}
	}


	a.fillSphere(25, 25, 25, 20);
	a.setBit(0, 0, 0);
	a.setBit(50, 50, 50);
	a.setBit(0, 50, 50);
	a.setBit(50, 50, 0);
	a.clearBit(50, 50, 0);;
	a.setBit(50, 0, 50);
	a.clearSphere(10, 10, 10, 15);
	a.clearSphere(40, 40, 40, 15);

	//Writing to file
	writeSTL(a, "testFinal.stl");
	int ender;
	std::cin >> ender;

}