//
//  voxelModel.hpp
//  Homework2
//
//  Created by Ben Jones on 1/4/17.
//  Copyright © 2017 Ben Jones. All rights reserved.
//

#pragma once
#include <cstdint>

//VoxelModel struct

struct VoxelModel {
	int x, y, z;
	uint8_t * arr;
	int size;
};

VoxelModel allocateModel(int nx, int ny, int nz); // create a VoxelModel struct and initialize it to be an empty model with appropriate dimensions

void clearModel(VoxelModel& model); //set all the voxels in the model to off

void fillModel(VoxelModel& model); // --set all the voxels in the model to on

void deleteModel(VoxelModel& model); // --deallocate all heap memory and set the model's size to be 0, and data to be null.

//indexing functions

int getIndex(const VoxelModel& model, int x, int y, int z);
void getXYZ(const VoxelModel &model, int index, int &x, int &y, int &z);
int getByteNumber(int index);
uint8_t getBitNumber(int index);
bool getBit(const VoxelModel &model, int x, int y, int z);
void setBit(VoxelModel &model, int x, int y, int z);
void clearBit(VoxelModel &model, int x, int y, int z);
void toggleBit(VoxelModel &model, int x, int y, int z);
void addSphere(VoxelModel &model, int cx, int cy, int cz, int r);
void subtractSphere(VoxelModel &model, int cx, int cy, int cz, int r);
void toggleSphere(VoxelModel &model, int cx, int cy, int cz, int r);


