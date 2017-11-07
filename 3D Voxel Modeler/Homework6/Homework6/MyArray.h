#pragma once

#ifndef MYARRAY_H
#define MYARRAY_H


#include <iostream>
#include <stdint.h>
#include <stdio.h>

template <typename T>
class MyArray {
public:
	MyArray();
	MyArray(size_t arraySize);
	~MyArray();
	MyArray(const MyArray &other);
	MyArray & operator=(const MyArray &other);
	T get(size_t index) const;
	void set(size_t index, T item);
	void resize(int newSize);
	void PushBack(T item);
	T Back();
	T PopBack();

private:
	size_t size;
	T * array;
	size_t capacity;
};

#endif //MYARRAY_H