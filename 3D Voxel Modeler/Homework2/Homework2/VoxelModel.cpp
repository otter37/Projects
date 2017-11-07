#include <stdint.h>
#include <stdio.h>
#include <iostream>
#include "VoxelModel.h"


uint8_t getIndex(int x, int y, int z) {
	int result = (x * 16) + (y * 4) + z;
	return result;
}

uint8_t getX(uint8_t index) {
	return index / 16;
}

uint8_t getY(uint8_t index) {
	return index / 4 % 4;
}

uint8_t getZ(uint8_t index) {
	return index % 4;
}

uint64_t emptyModel() {
	return 0;
}

uint64_t fullModel() {
	return 0xFFFFFFFFFFFFFFFF;

}

bool getBit(uint64_t model, int x, int y, int z) {
	int index = getIndex(x, y, z);
	uint64_t shifted = model >> (63 - index);
	return shifted & 1;
}

uint64_t setBit(uint64_t model, int x, int y, int z) {
	int index = getIndex(x, y, z);
	uint64_t one = 1;
	uint64_t mask = one << (63 - index);
		return model | mask;

}

uint64_t clearBit(uint64_t model, int x, int y, int z) {
	int index = getIndex(x, y, z);
	uint64_t one = 1;
	uint64_t mask = one << (63 - index);
	mask = ~mask;
	return model & mask;
}

uint64_t toggleBit(uint64_t model, int x, int y, int z) {
	int index = getIndex(x, y, z);
	uint64_t one = 1;
	uint64_t mask = one << (63 - index);
	return model ^ mask;

}


