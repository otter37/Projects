#pragma once
#include <stdint.h>

uint8_t getIndex(int x, int y, int z);

uint8_t getX(uint8_t index);

uint8_t getY(uint8_t index);

uint8_t getZ(uint8_t index);

uint64_t emptyModel(); // -- return a model with all bits set to 0

uint64_t fullModel(); // -- return a model with all bits set to 1

bool getBit(uint64_t model, int x, int y, int z); // --return true if the bit for x, y, z is 1, and false if it's 0.

uint64_t setBit(uint64_t model, int x, int y, int z); // --returns a model that's the same as the parameter, except that the bit corresponding to x, y, z is set to 1.

uint64_t clearBit(uint64_t model, int x, int y, int z); // --same as setBit, but the bit for x, y, z should be set to 0

uint64_t toggleBit(uint64_t model, int x, int y, int z); // --same as set / clearBit, but the value of bit x, y, z should be "flipped."  If it is 1 in the parameter passed in, set it to 0, and vice versa.