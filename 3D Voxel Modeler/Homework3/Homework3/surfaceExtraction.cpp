//
//  surfaceExtraction.cpp
//  Homework3
//
//  Created by Ben Jones on 1/6/17.
//  Copyright © 2017 Ben Jones. All rights reserved.
//
#define _CRT_SECURE_NO_WARNINGS

#include "surfaceExtraction.hpp"
#include <stdint.h>
#include <iostream>
#include <stdlib.h>
#include <stdio.h>


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





inline void fillPlane(int a1, int a2, int b1, int b2, int c, int cInd, Triangle& t1, Triangle& t2){
    t1.v1[cInd] = c;
    t2.v1[cInd] = c;
    t1.v2[cInd] = c;
    t2.v2[cInd] = c;
    t1.v3[cInd] = c;
    t2.v3[cInd] = c;
    int aInd = (cInd +1) % 3;
    int bInd = (cInd +2) % 3;
    
    t1.v1[aInd] = a1;
    t1.v2[aInd] = a2;
    t1.v3[aInd] = a2;
    
    t2.v1[aInd] = a1;
    t2.v2[aInd] = a2;
    t2.v3[aInd] = a1;
    
    t1.v1[bInd] = b1;
    t1.v2[bInd] = b1;
    t1.v3[bInd] = b2;
    
    t2.v1[bInd] = b1;
    t2.v2[bInd] = b2;
    t2.v3[bInd] = b2;
    
}

void extractFace(int x, int y, int z, FaceType face, Triangle& t1, Triangle& t2){
    for(int i= 0; i < 3; i++){
        t1.normal[i] = 0;
        t2.normal[i] = 0;
    }
    switch(face){
        case NX:
            t1.normal[0] = -1;
            t2.normal[0] = -1;
            fillPlane(y + 1, y, z, z+1, x, 0, t1, t2);
            break;
        case NY:
            t1.normal[1] = -1;
            t2.normal[1] = -1;
            fillPlane(z + 1, z, x, x+1, y, 1, t1, t2);
            break;
        case NZ:
            t1.normal[2] = -1;
            t2.normal[2] = -1;
            fillPlane(x + 1, x, y, y + 1, z, 2, t1, t2);
            break;
        case PX:
            t1.normal[0] = 1;
            t2.normal[0] = 1;
            fillPlane(y, y + 1, z, z +1, x + 1, 0, t1, t2);
            break;
        case PY:
            t1.normal[1] = 1;
            t2.normal[1] = 1;
            fillPlane(z, z + 1, x, x + 1, y + 1, 1, t1, t2);
            break;
        case PZ:
            t1.normal[2] = 1;
            t2.normal[2] = 1;
            fillPlane(x, x +1, y, y + 1, z + 1, 2, t1, t2);
            break;
    }

}

void writeSTL(uint64_t model, const char* filename) {
	FILE* fp = fopen(filename, "wb+");
	uint8_t buffer =0;

	for (int i = 0; i < 80; i++) {
		fwrite(&buffer, sizeof(buffer), 1, fp);
	}

	uint32_t buff[] = { 0 };
	fwrite(buff, sizeof(uint32_t), 1, fp);

	uint32_t numTriangles = 0;

	for (int i = 0; i < 64; i++) {
		if (getBit(model, getX(i), getY(i), getZ(i)) == 1) {
			//nx
			if ((getBit(model, (getX(i) - 1), getY(i), getZ(i))) != 1 || (getX(i) - 1 < 0)) {
				Triangle t1;
				Triangle t2;
				extractFace(getX(i), getY(i), getZ(i), NX, t1, t2);
				fwrite(&t1, sizeof(t1), 1, fp);
				uint16_t buff16[] = { 0 };
				fwrite(buff16, sizeof(uint16_t), 1, fp);
				fwrite(&t2, sizeof(t2), 1, fp);
				fwrite(buff16, sizeof(uint16_t), 1, fp);
				numTriangles += 2;


			}
			//px
			if ((getBit(model, (getX(i) + 1), getY(i), getZ(i))) != 1 || (getX(i) + 1 > 3)) {
				Triangle t1;
				Triangle t2;
				extractFace(getX(i), getY(i), getZ(i), PX, t1, t2);
				fwrite(&t1, sizeof(t1), 1, fp);
				uint16_t buff16[] = { 0 };
				fwrite(buff16, sizeof(uint16_t), 1, fp);
				fwrite(&t2, sizeof(t2), 1, fp);
				fwrite(buff16, sizeof(uint16_t), 1, fp);
				numTriangles += 2;

			}

			//ny
			if ((getBit(model, getX(i), (getY(i) - 1), getZ(i))) != 1 || (getY(i) - 1 < 0)) {
				Triangle t1;
				Triangle t2;
				extractFace(getX(i), getY(i), getZ(i), NY, t1, t2);
				fwrite(&t1, sizeof(t1), 1, fp);
				uint16_t buff16[] = { 0 };
				fwrite(buff16, sizeof(uint16_t), 1, fp);
				fwrite(&t2, sizeof(t2), 1, fp);
				fwrite(buff16, sizeof(uint16_t), 1, fp);
				numTriangles += 2;


			}
			//py
			if ((getBit(model, getX(i), (getY(i) + 1), getZ(i))) != 1 || (getY(i) + 1 > 3)) {
				Triangle t1;
				Triangle t2;
				extractFace(getX(i), getY(i), getZ(i), PY, t1, t2);
				fwrite(&t1, sizeof(t1), 1, fp);
				uint16_t buff16[] = { 0 };
				fwrite(buff16, sizeof(uint16_t), 1, fp);
				fwrite(&t2, sizeof(t2), 1, fp);
				fwrite(buff16, sizeof(uint16_t), 1, fp);
				numTriangles += 2;

			}
			//nz
			if ((getBit(model, getX(i), getY(i), (getZ(i) - 1)) != 1) || (getZ(i) - 1 < 0)) {
				Triangle t1;
				Triangle t2;
				extractFace(getX(i), getY(i), getZ(i), NZ, t1, t2);
				fwrite(&t1, sizeof(t1), 1, fp);
				uint16_t buff16[] = { 0 };
				fwrite(buff16, sizeof(uint16_t), 1, fp);
				fwrite(&t2, sizeof(t2), 1, fp);
				fwrite(buff16, sizeof(uint16_t), 1, fp);
				numTriangles += 2;


			}
			//pz
			if ((getBit(model, getX(i), getY(i), (getZ(i) + 1))) != 1 || (getZ(i) + 1 > 3)) {
				Triangle t1;
				Triangle t2;
				extractFace(getX(i), getY(i), getZ(i), PZ, t1, t2);
				fwrite(&t1, sizeof(t1), 1, fp);
				uint16_t buff16[] = { 0 };
				fwrite(buff16, sizeof(uint16_t), 1, fp);
				fwrite(&t2, sizeof(t2), 1, fp);
				fwrite(buff16, sizeof(uint16_t), 1, fp);
				numTriangles += 2;

			}


		}
	}

	fseek(fp, sizeof(uint8_t) * 80, SEEK_SET);
	fwrite(&numTriangles, sizeof(numTriangles), 1, fp);

	fclose(fp);

}

int main(int argc, char** argv) {
	uint64_t model = fullModel();
	model = toggleBit(model, 0, 0, 0);
	model = toggleBit(model, 1, 0, 0);

	model = toggleBit(model, 3, 3, 3);
	writeSTL(model, "testSTL.stl");
}