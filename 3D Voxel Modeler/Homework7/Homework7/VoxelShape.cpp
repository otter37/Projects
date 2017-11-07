#define _CRT_SECURE_NO_WARNINGS

#include "BitVector.h"
#include "VoxelShape.h"
#include <stdint.h>
#include <iostream>


VoxelShape::VoxelShape(int x, int y, int z) {
	int arrSize = x*y*z;
	vector = BitVector(arrSize);
	numx = x;
	numy = y;
	numz = z;
}

VoxelShape::~VoxelShape() {
	numx = numy = numz = 0;
}

void VoxelShape::fillShape() {
	vector.fillBitVector();
}

void VoxelShape::clearShape() {
	vector.clearBitVector();
}

bool VoxelShape::getBit(int x, int y, int z) {
	int index = getIndex(x, y, z);
	return vector.getBit(index);
	
}

void VoxelShape::setBit(int x, int y, int z) {
	int index = getIndex(x, y, z);
	vector.set(index, 1);
}

void VoxelShape::clearBit(int x, int y, int z) {
	int index = getIndex(x, y, z);
	vector.set(index, 0);
}

void VoxelShape::toggleBit(int x, int y, int z) {
	if (getBit(x, y, z)) {
		clearBit(x, y, z);
	}
	else {
		setBit(x, y, z);
	}
}

void VoxelShape::fillSphere(int cx, int cy, int cz, int r) {
	for (int i = 0; i < vector.size(); i++) {
		int x, y, z;
		getXYZ(i, x, y, z);
		if ((pow(x - cx, 2) + pow(y - cy, 2) + pow(z - cz, 2)) < pow(r, 2)) {
			setBit(x, y, z);
		}
	}
}


void VoxelShape::clearSphere(int cx, int cy, int cz, int r) {
	for (int i = 0; i < vector.size(); i++) {
		int x, y, z;
		getXYZ(i, x, y, z);
		if ((pow(x - cx, 2) + pow(y - cy, 2) + pow(z - cz, 2)) < pow(r, 2)) {
			clearBit(x, y, z);
		}
	}
}


void VoxelShape::toggleSphere(int cx, int cy, int cz, int r) {
	for (int i = 0; i < vector.size(); i++) {
		int x, y, z;
		getXYZ(i, x, y, z);
		if ((pow(x - cx, 2) + pow(y - cy, 2) + pow(z - cz, 2)) < pow(r, 2)) {
			toggleBit(x, y, z);
		}
	}
}

int VoxelShape::getIndex(int x, int y, int z) {
	return (x * numy * numz + y * numz + z);

}

void VoxelShape::getXYZ(int index, int &x, int &y, int &z) {
	x = floor(index / numy / numz);
	y = index / numz % numy;
	z = index % numz;
}

void VoxelShape::getNumXYZ(int &x, int &y, int &z) {
	x = numx;
	y = numy;
	z = numz;

}


