#include <stdint.h>
#include <stdio.h>
#include <iostream>
#include "VoxelModel.h"
#include <assert.h>



int main(int argc, char** argv) {
	
	uint64_t full = fullModel();
	uint64_t empty = emptyModel();

	std::cout << "Empty model: " << std::hex << empty << std::endl;
	std::cout << "Full model: " << full << std::endl;

	empty = setBit(empty, 1, 1, 1);
	empty = setBit(empty, 2, 1, 0);
	empty = toggleBit(empty, 3, 1, 0);
	empty = clearBit(empty, 2, 1, 0);
	empty = setBit(empty,3, 3, 3);
	empty = toggleBit(empty,3, 3, 3);
	empty = toggleBit(empty,3, 3, 3);
	empty = setBit(empty, 3, 1, 2);
	empty = toggleBit(empty, 1, 2, 3);
	empty = toggleBit(empty, 0, 0, 0);

	std::cout << "New model: " << empty << std::endl;

	int ender;
	std::cin >> ender;
}

