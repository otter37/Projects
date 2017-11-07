#include "BitVector.h"
#include <iostream>
#include <stdint.h>
#include <stdio.h>
#include "MyArray.cpp"


BitVector::BitVector(int numBits) {
	if (numBits < 20) {
		numBits = 20; }
	numberOfBits = numBits;
	arr = MyArray<bool>(numBits);
	for (int i = 0; i < numBits; i++) {
		arr.set(i, 0);
	}
}

BitVector::BitVector() {
}


BitVector::~BitVector() {
		arr = NULL;
		numberOfBits = 0;
	
}

BitVector::BitVector(BitVector & copy) {
	arr = MyArray<bool>(copy.numberOfBits);
	for (int i = 0; i < copy.size(); i++) {
		arr.set(i, copy.getBit(i));
	}
	numberOfBits = copy.numberOfBits;

}

BitVector & BitVector::operator=(BitVector &copy) {

	arr = MyArray<bool>(copy.numberOfBits);
	for (int i = 0; i < copy.size(); i++) {
		arr.set(i, copy.getBit(i));
	}
	numberOfBits = copy.numberOfBits;
	return *this;
}

void BitVector::fillBitVector() {
	for (int i = 0; i < numberOfBits; i++) {
		arr.set(i, 1);
	}
}

void BitVector::clearBitVector() {
	for (int i = 0; i < numberOfBits; i++) {
		arr.set(i, 0);
	}
}

bool BitVector::getBit(int bit) {
	return arr.get(bit);
}

void BitVector::set(int bit, bool value) {
	arr.set(bit, value);

}

int BitVector::size() {
	return numberOfBits;
}

