#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include "BitVector.h"
#include "VoxelShape.h"
#include "Export.h"
#include <chrono>
#include <ctime>

int main(int argc, char** argv) {

	VoxelShape a = VoxelShape(251,251,251);



	a.fillShape();
	a.clearShape();

	a.fillSphere(50, 20, 25, 20);

	a.setBit(0, 0, 0);
	a.setBit(50, 50, 50);
	a.setBit(0, 50, 50);
	a.setBit(50, 50, 0);
	a.setBit(50, 0, 50);


	std::chrono::time_point<std::chrono::system_clock> start, end;
	start = std::chrono::system_clock::now();
	std::cout << "Testing threaded version..." << std::endl;
	writeSTL(a, "testPLS.stl");
	end = std::chrono::system_clock::now();
	std::chrono::duration<double> elapsed_seconds = end - start;
	std::cout << "Elapsed time for threaded: " << elapsed_seconds.count() << "s\n";

	start = std::chrono::system_clock::now();
	std::cout << "Testing non-threaded version..." << std::endl;
	badWriteSTL(a, "badtestPLS.stl");
	end = std::chrono::system_clock::now();
	elapsed_seconds = end - start;
	std::cout << "Elapsed time for non-threaded: " << elapsed_seconds.count() << "s\n";

	std::cout << "Done" << std::endl;
	int ender;
	std::cin >> ender;
}