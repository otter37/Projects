//
//  BitVector.hpp
//  Homework 5
//

#pragma once

#ifndef BitVector_hpp
#define BitVector_hpp

#include <stdio.h>
#include <cstdint>
#include "MyArray.h"


class BitVector {
public:
	BitVector();
	BitVector(size_t elements);
	BitVector(BitVector &);
	~BitVector();
	BitVector &operator=(const BitVector &other);
	
	void Fill();
	void Clear();
	bool Get(size_t whichBit) const;
	void Set(size_t whichBit, bool value);
	void Toggle(size_t whichBit);
	size_t Size() const;
	void Resize(size_t newSize);
private:
	size_t GetStorageSize(size_t numElements) const;
	void GetElementAndOffset(size_t index, size_t &element, int &offset) const;
	MyArray<uint64_t> storage;
	size_t numElements;
	size_t storageSize;
};

#endif /* BitVector_hpp */
