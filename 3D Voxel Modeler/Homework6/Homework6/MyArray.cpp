#include "MyArray.h"
#include <iostream>
#include <stdint.h>
#include <stdio.h>

template <typename T>
MyArray<T>::MyArray() {
	size = 0;
	capacity = 20;
	array = new T[20];

}

template <typename T>
MyArray<T>::MyArray(size_t arraySize) {
	size = 0;
	if (arraySize < 20) {
		array = new T[20];	
		capacity = 20;

	}
	else {
		array = new T[arraySize];
		capacity = arraySize;

	}
}
template <typename T>
MyArray<T>::~MyArray() {
	delete[] array;
	size = 0;

}

template <typename T>
MyArray<T>::MyArray(const MyArray &other) {
	T* temparray = new T[other.capacity];
	for (int i = 0; i < other.capacity; i++) {
		temparray[i] = other.array[i];
	}
	delete[] array;
	array = temparray;
	size = other.size;
	capacity = other.capacity;
}

template<typename T>
MyArray<T> &MyArray<T>::operator=(const MyArray<T> &other)
{

	{
		if (&other == this)
			return (*this);
		this->resize(other.capacity);
		for (int i = 0; i < other.capacity; i++)
			(this->array)[i] = other.array[i];
		return (*this);
	}
}

template <typename T>
T MyArray<T>::get(size_t index) const {

	return array[index];
}

template <typename T>
void MyArray<T>::set(size_t index, T item) {
	if (index >= capacity) {
		if (index > capacity * 2) {
			resize(index + 50);
		}
		else {
			resize(capacity * 2);
		}
	}

	array[index] = item;
}

template <typename T>
void MyArray<T>::resize(int newSize) {
	std::cout << "Resizing array to " << newSize << std::endl;
	if (newSize <= capacity) {

	}
	else {
		T * tempArray = new T[newSize];
		for (int i = 0; i < capacity; i++) {
			tempArray[i] = array[i];
		}
		delete[] array;
		array = tempArray;
	}
	capacity = newSize;
}

template <typename T>
void MyArray<T>::PushBack(T item) {
	if (size >= sizeof(array) / sizeof(T)) {
		resize(size * 2);
	}

	array[size] = item;
	size++;
}

template <typename T>
T MyArray<T>::Back() {
	return array[size - 1];
}

template <typename T>
T MyArray<T>::PopBack() {
	T ret = array[size - 1];
	array[size - 1] = 0;
	size--;
	return ret;
}