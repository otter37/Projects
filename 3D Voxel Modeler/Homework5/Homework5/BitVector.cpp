#include "BitVector.h"
#include <iostream>
#include <stdint.h>
#include <stdio.h>


BitVector::BitVector(int numBits) {
	numberOfBits = numBits;
	arr = new bool[numBits];
	for (int i = 0; i < numBits; i++) {
		arr[i] = 0;
	}
}

BitVector::BitVector() {
}


BitVector::~BitVector() {
		delete[] arr;
		arr = NULL;
	
}

BitVector::BitVector(BitVector & copy) {
	arr = new bool[copy.size()];
	for (int i = 0; i < copy.size(); i++) {
		arr[i] = copy.arr[i];
	}
	numberOfBits = copy.numberOfBits;

}

BitVector & BitVector::operator=(BitVector &copy) {
	arr = new bool[copy.size()];
	for (int i = 0; i < copy.size(); i++) {
		arr[i] = copy.arr[i];
	}
	numberOfBits = copy.numberOfBits;
	return *this;
}

void BitVector::resize(int newSize) {
	bool * arrTemp = new bool[newSize];
	if (newSize > numberOfBits) {
		for (int i = 0; i < numberOfBits; i++) {
			arrTemp[i] = arr[i];
		}
		for (int i = numberOfBits; i < newSize; i++) {
			std::cout << "Setting " << i << " to 0." << std::endl;
			arrTemp[i] = 0;
		}
	}
	else {
		for (int i = 0; i < newSize; i++) {
			arrTemp[i] = arr[i];
		}
	}
	numberOfBits = newSize;

	delete[] arr;
	arr = new bool[numberOfBits];
	for (int i = 0; i < numberOfBits; i++) {
		arr[i] = arrTemp[i];
	}
	delete[] arrTemp;

}

void BitVector::fillBitVector() {
	for (int i = 0; i < numberOfBits; i++) {
		arr[i] = 1;
	}
}

void BitVector::clearBitVector() {
	for (int i = 0; i < numberOfBits; i++) {
		arr[i] = 0;
	}
}

bool BitVector::getBit(int bit) {
	return arr[bit];
}

void BitVector::set(int bit, bool value) {
	arr[bit] = value;

}

int BitVector::size() {
	return numberOfBits;
}

