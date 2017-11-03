import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {

		// Creating write file
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("NewData3.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		StringBuilder builder = new StringBuilder();

		// Creating header for csv file
		String ColumnNamesList = "n,Sort,CPU Seconds";
		builder.append(ColumnNamesList + "\n");

		Scanner sc = null;
		try {
			sc = new Scanner(new File("p1alldata.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (sc.hasNext()) {
			String nextFile = sc.next();
		
		// Loading in new file
		ArrayList<String> list = new ArrayList<String>();
		list = ArrayHandler.ReadArrayFromFile(nextFile);
		// creating arraylists

		ArrayList<String> list2 = new ArrayList<String>(list.size());
		ArrayList<String> list3 = new ArrayList<String>(list.size());
		ArrayList<String> list4 = new ArrayList<String>(list.size());
		ArrayList<String> list5 = new ArrayList<String>(list.size());
		ArrayList<String> list6 = new ArrayList<String>(list.size());
		// Timing copy time
		CpuTimer timer = new CpuTimer();
		int timingIterations = (256 * 1024) / list.size();
		for (int i = 0; i < timingIterations; i++) {
			list2 = ArrayHandler.MakeCopy(list, list2);
		}

		double avgCopyTime = timer.getElapsedCpuTime() / timingIterations;

		// Timing Random insertion sort
		timer = new CpuTimer();
		for (int i = 0; i < timingIterations; i++) {
			list3 = ArrayHandler.insertionSort(list2, list3);
		}
		
		double averageInsertionSortTime = timer.getElapsedCpuTime() / timingIterations - avgCopyTime;
		System.out.println("Random Insertion sort took: " + averageInsertionSortTime);
		System.out.println("List is sorted is : " + ArrayHandler.isSorted(list3));

		// Timing random Merge sort
		timer = new CpuTimer();
		for (int i = 0; i < timingIterations; i++) {
			list3 = ArrayHandler.mergeSort(list2);
		}
		double averageMergeSortTime = timer.getElapsedCpuTime() / timingIterations - avgCopyTime;

		// Giving results and appending to write file
		System.out.println("Random Merge Sort took: " + averageMergeSortTime);
		System.out.println("List is sorted is : " + ArrayHandler.isSorted(list3));
		builder.append(list.size() + ",");
		builder.append("IR" + ",");
		builder.append(averageInsertionSortTime);
		builder.append('\n');
		builder.append(list.size() + ",");
		builder.append("MR" + ",");
		builder.append(averageMergeSortTime);
		builder.append('\n');

		// Timing sorting already ascending order sorted file (insertion)

		timer = new CpuTimer();
		for (int i = 0; i < timingIterations; i++) {
			list4 = ArrayHandler.insertionSort(list3, list4);
		}
		averageInsertionSortTime = timer.getElapsedCpuTime() / timingIterations - avgCopyTime;
		System.out.println("Ascending Insertion sort took: " + averageInsertionSortTime);
		System.out.println("List is sorted is : " + ArrayHandler.isSorted(list4));
		
		// Timing sorting already ascending order sorted file (merge)
		timer = new CpuTimer();
		for (int i = 0; i < timingIterations; i++) {
			list4 = ArrayHandler.mergeSort(list3);
		}
		averageMergeSortTime = timer.getElapsedCpuTime() / timingIterations - avgCopyTime;
		System.out.println("Ascending Merge Sort took: " + averageMergeSortTime);
		System.out.println("List is sorted is : " + ArrayHandler.isSorted(list4));
		builder.append(list.size() + ",");
		builder.append("IA" + ",");
		builder.append(averageInsertionSortTime);
		builder.append('\n');
		builder.append(list.size() + ",");
		builder.append("MA" + ",");
		builder.append(averageMergeSortTime);
		builder.append('\n');

		// Sorting the list in descending order
		list5 = ArrayHandler.insertionSortBackwards(list4, list5);

		// Timing sorting descending order sorted file (insertion)

		timer = new CpuTimer();
		for (int i = 0; i < timingIterations; i++) {
			list6 = ArrayHandler.insertionSort(list5, list6);
		}
		averageInsertionSortTime = timer.getElapsedCpuTime() / timingIterations - avgCopyTime;
		System.out.println("Descending Insertion sort took: " + averageInsertionSortTime);
		System.out.println("List is sorted is : " + ArrayHandler.isSorted(list6));

		// Timing sorting already descending order sorted file (merge)
		timer = new CpuTimer();
		for (int i = 0; i < timingIterations; i++) {
			list6 = ArrayHandler.mergeSort(list5);
		}
		averageMergeSortTime = timer.getElapsedCpuTime() / timingIterations - avgCopyTime;
		System.out.println("Descending Merge Sort took: " + averageMergeSortTime);
		System.out.println("List is sorted is : " + ArrayHandler.isSorted(list6));
		builder.append(list.size() + ",");
		builder.append("ID" + ",");
		builder.append(averageInsertionSortTime);
		builder.append('\n');
		builder.append(list.size() + ",");
		builder.append("MD" + ",");
		builder.append(averageMergeSortTime);
		builder.append('\n');

		// Final write to csv file


	}
		pw.write(builder.toString());
		pw.close();
	}

}
