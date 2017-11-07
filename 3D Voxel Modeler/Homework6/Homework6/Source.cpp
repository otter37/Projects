#include "MyArray.h"
#include <stdio.h>
#include <stdint.h>
#include <iostream>
#include "MyArray.cpp"

int main(int argc, char** argv) {
	MyArray<int> a(15);
	a.set(0, 25);
	std::cout << a.get(0) << std::endl;
	a.PushBack(5);
	std::cout << a.Back() << std::endl;

	for (int i = 0; i < 64; i++) {
		a.PushBack(i);
	}
	std::cout << a.Back() << std::endl;
	a.PopBack();
	std::cout << a.Back() << std::endl;


	int ender;
	std::cin >> ender;

	return 0;
}