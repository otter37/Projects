#pragma once
#include <stdio.h>
#include <iostream>
#include <stdint.h>
#include "MyArray.h"



class BitVector {
public:

	BitVector();

	BitVector(int numBits);

	~BitVector();

	BitVector &operator=(BitVector &copy);

	BitVector::BitVector(BitVector & copy);

	void fillBitVector();

	void clearBitVector();

	bool getBit(int bit);

	void set(int bit, bool value);

	int size();

	
private:
	MyArray<bool> arr;
	int numberOfBits;
};