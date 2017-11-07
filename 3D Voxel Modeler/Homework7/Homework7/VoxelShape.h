#pragma once
#include <stdint.h>
#include <iostream>
#include "BitVector.h"

class VoxelShape {
public:
	VoxelShape(int x, int y, int z);

	~VoxelShape();

	void fillShape();

	void clearShape();

	bool getBit(int x, int y, int z);

	void setBit(int x, int y, int z);

	void clearBit(int x, int y, int z);

	void toggleBit(int x, int y, int z);

	void fillSphere(int cx, int cy, int cz, int r);

	void clearSphere(int cx, int cy, int cz, int r);

	void toggleSphere(int cx, int cy, int cz, int r);

	void getNumXYZ(int &numX, int &numY, int &numZ);


private:
	BitVector vector;
	int getIndex(int x, int y, int z);
	int numx;
	int numy;
	int numz;
	void getXYZ(int index, int &x, int &y, int &z);
};
