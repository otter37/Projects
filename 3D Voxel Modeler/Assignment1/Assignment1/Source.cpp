#include <iostream>;
int main(int argc, char ** argv) {
	int numbers[10];
	int numbersSize = sizeof(numbers) / sizeof(numbers[0]);

	//Populate the array from user
	std::cout << "Please input " << numbersSize << " numbers\n" << std::endl;

	for (int i = 0; i < numbersSize; i++) {
		std::cin >> numbers[i];
	}

	//Print array before sort
	std::cout << "Numbers before sort:" << std::endl;
	for (int i = 0; i < numbersSize; i++) {
		std::cout << " " << numbers[i];
	}

	std::cout << "\n" << std::endl;

	//Sort the array 
	for(int i=1; i < numbersSize; i++) {
	int temp = numbers[i]; int j = i - 1; while (j >= 0 && numbers[j] > temp) {
		numbers[j + 1] = numbers[j];
		j--;
	} numbers[j + 1] = temp;
}

	//Print the array after sort
	std::cout << "Numbers after sort:" << std::endl;
	for (int i = 0; i < numbersSize; i++) {
		std::cout << " " << numbers[i];
	}

	int ender;
	std::cin >> ender;
}