/*******************************************************************/
/*   Program Name:     Lab 4    Sorts                             */
/*                                                                 */
/*   Student Name:     Harrison Engel                              */
/*   Semester:         Fall 2014                                   */
/*   Class-Section:    COSC 20803-035                              */
/*   Instructor:       Dr. Comer                                   */
/*******************************************************************/

package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Model {

	private static int compareCount; // Used to keep track of compares
	private static String[] ascendingArray;

	public Model() throws IOException {
		// Creates the ascending array to be searched by
		// the various algorithms
		ascendingArray = createAscendingArray();
	}

	/************************* Sequential Search **********************************/

	public int sequentialSearch(String fileName, int searchKeys, int skipKeys)
			throws IOException {

		String[] searchArray = createSearchArray(fileName, searchKeys, skipKeys);
		compareCount = 0;

		for (String s : searchArray) {

			/***** Begin Search Logic **********/
			int count = 0;
			do {
				compareCount++;
				if (s.compareTo(ascendingArray[count]) <= 0)
					break;
				else {
					count++;
				}
			} while (count < ascendingArray.length - 1);

			compareCount++;
			if (ascendingArray[count].equalsIgnoreCase(s)) {
				// Found the Key
				continue;
			}
		}

		return compareCount;
	}

	/************************* Binary Search **********************************/
	public int binarySearch(String fileName, int searchKeys, int skipKeys)
			throws IOException {
		String[] searchArray = createSearchArray(fileName, searchKeys, skipKeys);
		ascendingArray = createAscendingArray();
		compareCount = 0;

		// For every key to search for
		for (String s : searchArray) {

			/*********** Search Logic *******************/
			int low = 0;
			int high = ascendingArray.length - 1;
			int mid;

			while (low < high) {
				mid = (low + high) / 2;// Find middle value
				compareCount++;
				if (ascendingArray[mid].equalsIgnoreCase(s)) {

					break;
				}
				compareCount++;
				if (ascendingArray[mid].compareToIgnoreCase(s) < 0) {
					low = mid + 1;
				} else {
					high = mid - 1;
				}
			}
		}

		return compareCount;

	}

	/************************* Fibonacci Search **********************************/

	public int fibonacciSearch(String fileName, int searchKeys, int skipKeys)
			throws IOException {

		String[] searchArray = createSearchArray(fileName, searchKeys, skipKeys);
		compareCount = 0;

		// Find how many Fibonacci numbers are required to fit the data
		int mStart = 2; //Calculated once outside of key searching
		int cur = 1;
		int prev = 1;
		int next;
		while (true) {
			next = cur + prev;
			if (next >= ascendingArray.length)
				break;
			prev = cur;
			cur = next;
			mStart++;//This counts the maximum number of fibonacci
			//numbers that will be needed
		}
		

		// Create an array to quickly find Fibonacci numbers
		int[] findFib = getFibNumbers(mStart);
		
		System.out.println(findFib.length);
		for (String s : searchArray) {
			/***************** Begin Search Logic ******************/
			
			int l = mStart - 1; 
			int r = mStart - 2;
			int m = findFib[mStart];
			
			while (true) {

				compareCount++;
				if (s.equals(ascendingArray[m])) {
					//Found the Key
					break;
				}
				compareCount++;
				if (s.compareTo(ascendingArray[m]) < 0) {
					if (l == 0) {
						//Key is not in array
						break;
					}
					m = m - findFib[r];
					r--;
					l--;
				} else {
					m = m + findFib[r];
					l = r - 1;
					r = r - 2;
					while (m > ascendingArray.length) {
						/*This loop searches for the next
						value of m in the fibonacci tree
						that isn't out of the search range
						 */
						m = m - findFib[r];
						r--;
						l--;
					}
					if (r <= 1) {
						//Key is not in array
						break;
					}

				}

			}

		}

		return compareCount;

	}

	// Creates an array of Fibonacci numbers where array[i] = Fibonacci i.
	private int[] getFibNumbers(int n) {
		//n is assumed to be greater than 2
		int[] toReturn = new int[n + 1];
		toReturn[0] = 0;
		toReturn[1] = 1;
		toReturn[2] = 1;

		for (int i = 3; i <= n; i++) {
			toReturn[i] = toReturn[i - 1] + toReturn[i - 2];
		}
		return toReturn;
	}

	/************* Interpolation Search **********************************/
	public int interpolationSearch(String fileName, int searchKeys, int skipKeys)
			throws IOException {

		String[] searchArray = createSearchArray(fileName, searchKeys, skipKeys);
		compareCount = 0;

		for (String s : searchArray) {

			/************ Begin Search Logic ***************/

			int high = ascendingArray.length - 1;
			int low = 0;
			
			int hornerKey = hornersConversion(s.charAt(0), s.charAt(1),
					s.charAt(2), s.charAt(3));
			/* hornerKey is the numeric representation of
			 * the key that we're searching for. See hornersConversion
			 */
			int hornerLow, hornerHigh, mid;
			double hornerMid;

			while (low < high) { //Ends the same way Binary Search does
				hornerLow = hornersConversion(ascendingArray[low].charAt(0),
						ascendingArray[low].charAt(1),
						ascendingArray[low].charAt(2),
						ascendingArray[low].charAt(3));
				//hornerLow is a numeric representation of the lowest 4 character key
				hornerHigh = hornersConversion(ascendingArray[high].charAt(0),
						ascendingArray[high].charAt(1),
						ascendingArray[high].charAt(2),
						ascendingArray[high].charAt(3));
				//hornerHigh is a numeric representation of the highest 4 character key
				hornerMid = ((double) (hornerKey - hornerLow) / (double) (hornerHigh - hornerLow));
				/*hornerMid is the decimal representation of where hornerKey would fall within
				the range from hornerLow to hornerHigh.
				mid then uses hornerMid to calculate where this decimal would be
				within the low to high integer value range that represents the
				array being searched
				*/
				mid = low + (int) ((high - low) * hornerMid);
				
				if (mid < low || mid > high) {
					break;// Not in List
				}
				
				compareCount++;
				if (ascendingArray[mid].equals(s)) {
					//We found the key
					break;
				} else {
					if (ascendingArray[mid].compareTo(s) < 0) {
						//search from mid+1 to high
						low = mid + 1;
					} else {
						//search from low to mid-1
						high = mid - 1;
					}
				}
			}
		}

		return compareCount;

	}

	/***************** createSearchArray **********************/
	// Used to create the Array of keys we're searching for
	private String[] createSearchArray(String fileName, int searchKeys,
			int skipKeys) throws IOException {
		
		if ((500 - skipKeys) < searchKeys)
			return null; 
		
		String[] toReturn = new String[searchKeys];
		BufferedReader br = new BufferedReader(new InputStreamReader(this
				.getClass().getResourceAsStream(fileName)), 1024);

		// Skip over the given first keys
		for (int i = 0; i < skipKeys; i++) {
			br.readLine();
		}
		//Now read in the number of keys we want
		for (int i = 0; i < searchKeys; i++) {
			toReturn[i] = br.readLine().trim();
		}
		br.close();//always close a BufferedReader!!
		return toReturn;
	}

	/***************** createAscendingArray **********************/
	//Creates the array we will be searching
	private String[] createAscendingArray() throws IOException {
		String[] toReturn = new String[5000];
		BufferedReader br = new BufferedReader(new InputStreamReader(this
				.getClass().getResourceAsStream("Ascending.dat")));
		// src/model/
		for (int i = 0; i < 5000; i++) {
			toReturn[i] = br.readLine().trim();
		}
		br.close();
		return toReturn;
	}

	/***************** hornersConversion **********************/
	/* In written logic: take the first character in bits, shift 
	 * it left 8. add the second character, then shift left 8.
	 * add the third character then shift left 8. add the fourth 
	 * character and shift left 8 
	 */
	private int hornersConversion(char c1, char c2, char c3, char c4) {
		return ((((((int) c1 << 8) + (int) c2) << 8) + (int) c3) << 8)
				+ (int) c4;
	}

}
