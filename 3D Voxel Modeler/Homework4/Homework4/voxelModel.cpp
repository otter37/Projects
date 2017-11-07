#define _CRT_SECURE_NO_WARNINGS
//
//  voxelModel.cpp
//  Homework2
//
//  Created by Ben Jones on 1/4/17.
//  Copyright © 2017 Ben Jones. All rights reserved.
//

#include <iostream>
#include <stdio.h>
#include "voxelModel.hpp"
#include <cmath>

int getIndex(const VoxelModel &model, int x, int y, int z) {
	return x * model.y * model.z + y* model.z + z;
}
void getXYZ(const VoxelModel &model, int index, int &x, int &y, int &z) {
	x = floor(index / model.y / model.z);
	y = index / model.z % model.y;
	z = index % model.z;
}

int getByteNumber(int index) {
	return floor(index / 8);
}

uint8_t getBitNumber(int index) {
	return index % 8;
}

VoxelModel allocateModel(int nx, int ny, int nz) {
	VoxelModel model;
	model.arr = new uint8_t[nx*ny*nz / 8 + 1];
	uint8_t zeros = 0x00000000;
	for (int i = 0; i < nx*ny*nz / 8 + 1; i++) {
		model.arr[i] = zeros;
	}
	model.x = nx;
	model.y = ny;
	model.z = nz;
	model.size = nx*ny*nz;
	return model;

}

void deleteModel(VoxelModel &model) {
	delete[] model.arr;
	model.size = 0;
	model.x = NULL;
	model.y = NULL;
	model.z = NULL;

}

void clearModel(VoxelModel &model) {
	for (int i = 0; i < model.size; i++) {
		int x, y, z;
		getXYZ(model, i, x, y, z);
		clearBit(model, x, y, z);
	}
}

void fillModel(VoxelModel &model) {
	for (int i = 0; i < model.size; i++) {
		int x, y, z;
		getXYZ(model, i, x, y, z);
		setBit(model, x, y, z);
	}
}
bool getBit(const VoxelModel &model, int x, int y, int z) {
	int index = getIndex(model, x, y, z);
	int byteNumber = getByteNumber(index);
	int number = getBitNumber(index);
	bool ret = ((model.arr[byteNumber] & (uint8_t{ 1 } << (7 - number))) >> (7 - number)) != 0;
	//std::cout << "Return is " << ret << std::endl;
	return ret;

	//return (model & (uint64_t{1} << getIndex(x, y, z))) != 0;
}

void setBit(VoxelModel& model, int x, int y, int z) {
	//std::cout << "Getting to setBit" << std::endl;
	int index = getIndex(model, x, y, z);
	int byteNumber = getByteNumber(index);
	int number = getBitNumber(index);
	//std::cout << "Setting bit " << x << ", " << y << ", " << z << std::endl;
	//std::cout << "It is at index " << index << std::endl;
	//std::cout << "This bit is found at byte number " << byteNumber << "and bit number " << number << std::endl;

	model.arr[byteNumber] = (model.arr[byteNumber] | (uint8_t{ 1 } << (7 - number)));

}
void clearBit(VoxelModel & model, int x, int y, int z) {
	int index = getIndex(model, x, y, z);
	int byteNumber = getByteNumber(index);
	uint8_t number = getBitNumber(index);
	model.arr[byteNumber] = model.arr[byteNumber] & ~(uint8_t{ 1 } << (7 - number));
}
void toggleBit(VoxelModel& model, int x, int y, int z) {

	if (getBit(model, x, y, z)) {
		return clearBit(model, x, y, z);
	}
	else {
		return setBit(model, x, y, z);
	}

}

void addSphere(VoxelModel &model, int cx, int cy, int cz, int r) {
	for (int i = 0; i < model.size; i++) {
		int x, y, z;
		getXYZ(model, i, x, y, z);
		if ((pow(x - cx, 2) + pow(y - cy, 2) + pow(z - cz, 2)) < pow(r, 2)) {
			setBit(model, x, y, z);
		}
	}
}

void subtractSphere(VoxelModel &model, int cx, int cy, int cz, int r) {
	for (int i = 0; i < model.size; i++) {
		int x, y, z;
		getXYZ(model, i, x, y, z);
		if ((pow(x - cx, 2) + pow(y - cy, 2) + pow(z - cz, 2)) < pow(r, 2)) {
			clearBit(model, x, y, z);
		}
	}
}

void toggleSphere(VoxelModel &model, int cx, int cy, int cz, int r) {
	for (int i = 0; i < model.size; i++) {
		int x, y, z;
		getXYZ(model, i, x, y, z);
		if ((pow(x - cx, 2) + pow(y - cy, 2) + pow(z - cz, 2)) < pow(r, 2)) {
			toggleBit(model, x, y, z);
		}
	}
}
