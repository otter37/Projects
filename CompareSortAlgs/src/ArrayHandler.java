import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ArrayHandler {

	public static ArrayList<String> ReadArrayFromFile(String fileName) {
		ArrayList<String> list = new ArrayList<String>();

		Scanner sc = null;
		try {
			sc = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (sc.hasNext()) {
			String nextString = sc.next();
			list.add(nextString);
		}

		return list;

	}
	

	public static ArrayList<String> MakeCopy(ArrayList<String> list, ArrayList<String> copiedList) {
		copiedList = new ArrayList<String>(list);
		return copiedList;
	}
	
	public static boolean isSorted(ArrayList<String> list) {
		for(int i = 0; i < list.size()-1; i++) {
			if((list.get(i).compareTo(list.get(i+1)) > 0)) {
				return false;
			}
		}
		return true;
	}
	
	public static ArrayList<String> insertionSort(ArrayList<String> input, ArrayList<String> output) {
		output = MakeCopy(input, output);
		
		 String temp;
	        for (int i = 1; i < output.size(); i++) {
	            for(int j = i ; j > 0 ; j--){
	                if(output.get(j).compareTo(output.get(j-1)) < 0){
	                    temp = output.get(j);
	                    output.set(j, output.get(j-1));
	                    output.set(j-1, temp);
	                }
	            }
	        }
	        return output;
	}
	
	public static ArrayList<String> insertionSortBackwards(ArrayList<String> input, ArrayList<String> output) {
		output = MakeCopy(input, output);
		
		 String temp;
	        for (int i = 1; i < output.size(); i++) {
	            for(int j = i ; j > 0 ; j--){
	                if(output.get(j).compareTo(output.get(j-1)) > 0){
	                    temp = output.get(j);
	                    output.set(j, output.get(j-1));
	                    output.set(j-1, temp);
	                }
	            }
	        }
	        return output;
	}
	
	public static ArrayList< String > mergeSort(ArrayList< String > list)
	  {
	    ArrayList < String > sorted = MakeCopy(list,list);
	    if (list.size() == 1)
	    {
	        sorted = list;
	    } else {
	        int mid1 = list.size() /2;

	        ArrayList< String > left = new ArrayList< String >();
	        ArrayList< String > right = new ArrayList< String >();

	        for ( int x = 0; x < mid1; x++) {
	            left.add(list.get(x));

	        }
	        for ( int x = mid1; x < list.size(); x++) {
	            right.add(list.get(x));
	        }

	        left = mergeSort(left);
	        right = mergeSort(right);
	        sorted = mergeArray(left,right);
	    }

	    return sorted;
	  }

	private static ArrayList< String > mergeArray(ArrayList< String > left, ArrayList< String > right)
	{
	    ArrayList< String > merged = new ArrayList< String >();

	    int i = 0;
	    int l = 0;
	    int r = 0;

	    while (l < left.size() && r < right.size())
	           {
	              if ((left.get(l)).compareTo(right.get(r)) < 0)
	              {
	                 merged.add(left.get(l));
	                 l++;
	              }
	              else
	              {
	                 merged.add(right.get(r));
	                 r++;
	              }

	              i++;
	           }


	           while (l < left.size())
	           {
	              merged.add(left.get(l));
	              l++;
	              i++;
	           }

	           while (r < right.size())
	           {
	              merged.add(right.get(r));
	              r++;
	                  i++;
	           }

	    return merged;


	  }

	}

