/**
 *   Copyright 2018, Vitali Baumtrok <vbsw@mailbox.org>.
 * Distributed under the Boost Software License, Version 1.0.
 *      (See accompanying file BSL-1.0.txt or copy at
 *        http://www.boost.org/LICENSE_1_0.txt)
 */


package com.github.vbsw.csvdb;


/**
 * Functions on Arrays.
 * 
 * @author Vitali Baumtrok
 */
public class DBArrays {

	/**
	 * Searches the specified array for the specified key using the binary search algorithm. The array must be sorted prior to making this call. If it is not sorted, the results are undefined. If the array contains multiple elements with the specified value, the lowest index is returned.
	 * @param array the array to be searched
	 * @param key the value to be searched for
	 * @return index of the search key, if it is contained in the array; otherwise, (-(insertion point) - 1). The insertion point is defined as the point at which the key would be inserted into the array: the index of the first element greater than the key, or a.length if all elements in the array are less than the specified key. Note that this guarantees that the return value will be >= 0 if and only if the key is found.
	 */
	public static int binarySearch ( final String[] array, final String key ) {
		return DBArrays.binarySearchOfRange(array,0,array.length,key);
	}

	/**
	 * Searches a range of the specified array for the specified key using the binary search algorithm. The range must be sorted prior to making this call. If it is not sorted, the results are undefined. If the range contains multiple elements with the specified value, the lowest index is returned.
	 * @param array the array to be searched
	 * @param from the index of the first element (inclusive) to be searched
	 * @param to the index of the last element (exclusive) to be searched
	 * @param key the value to be searched for
	 * @return index of the search key, if it is contained in the array; otherwise, (-(insertion point) - 1). The insertion point is defined as the point at which the key would be inserted into the array: the index of the first element greater than the key, or a.length if all elements in the array are less than the specified key. Note that this guarantees that the return value will be >= 0 if and only if the key is found.
	 */
	public static int binarySearchOfRange ( final String[] array, final int from, final int to, final String key ) {
		int left = 0;
		int right = to - 1;

		while ( left <= right ) {
			final int middle = (left + right) / 2;
			final int compareResult = array[middle].compareTo(key);

			if ( compareResult < 0 ) {
				left = middle + 1;

			} else if ( compareResult > 0 ) {
				right = middle - 1;

			} else {
				return middle;
			}
		}
		return -left - 1;
	}

	/**
	 * Searches the specified array for the specified key using the binary search algorithm. The range must be sorted prior to making this call. If it is not sorted, the results are undefined. If the range contains multiple elements with the specified value, the lowest index is returned.
	 * @param indices references to values in a sorted way
	 * @param values the values to compare
	 * @param key the value to be searched for
	 * @return index of indices of the search key, if it is contained in the array; otherwise, (-(insertion point) - 1). The insertion point is defined as the point at which the key would be inserted into the array: the index of the first element greater than the key, or a.length if all elements in the array are less than the specified key. Note that this guarantees that the return value will be >= 0 if and only if the key is found.
	 */
	public static int indexedBinarySearch ( final int[] indices, final String[] values, final String key ) {
		return DBArrays.indexedBinarySearchOfRange(indices,values,0,indices.length,key);
	}

	/**
	 * Searches a range of the specified array for the specified key using the binary search algorithm. The range must be sorted prior to making this call. If it is not sorted, the results are undefined. If the range contains multiple elements with the specified value, the lowest index is returned.
	 * @param indices references to values in a sorted way
	 * @param values the values to compare
	 * @param from the indices index of the first element (inclusive) to be searched
	 * @param to the indices index of the last element (exclusive) to be searched
	 * @param key the value to be searched for
	 * @return index of indices of the search key, if it is contained in the array; otherwise, (-(insertion point) - 1). The insertion point is defined as the point at which the key would be inserted into the array: the index of the first element greater than the key, or a.length if all elements in the array are less than the specified key. Note that this guarantees that the return value will be >= 0 if and only if the key is found.
	 */
	public static int indexedBinarySearchOfRange ( final int[] indices, final String[] values, final int from, final int to, final String key ) {
		int left = 0;
		int right = to - 1;

		while ( left <= right ) {
			final int middle = (left + right) / 2;
			final int compareResult = values[indices[middle]].compareTo(key);

			if ( compareResult < 0 ) {
				left = middle + 1;

			} else if ( compareResult > 0 ) {
				right = middle - 1;

			} else {
				return middle;
			}
		}
		return -left - 1;
	}

	/**
	 * Searches for the most left index equal to the value of array[from].
	 * @param array the array to be searched
	 * @param fromRight the index of the first element (inclusive) to be searched
	 * @param toLeft the index of the last element (inclusive) to be searched
	 * @return the most left index equal to array[from]
	 */
	public static int trackLeft ( final String[] array, final int fromRight, final int toLeft ) {
		if ( fromRight >= toLeft ) {
			final String key = array[fromRight];
			int i;
			for ( i = fromRight - 1; i >= toLeft; i -= 1 ) {
				if ( !array[i].equals(key) ) {
					return i + 1;
				}
			}
			return i + 1;
		}
		return fromRight;
	}

	/**
	 * Searches for the most right index equal to the value of array[from].
	 * @param array the array to be searched
	 * @param fromLeft the index of the first element (inclusive) to be searched
	 * @param toRight the index of the last element (exclusive) to be searched
	 * @return the most right index not equal to array[from]
	 */
	public static int trackRight ( final String[] array, final int fromLeft, final int toRight ) {
		if ( fromLeft < toRight ) {
			final String key = array[fromLeft];
			int i;
			for ( i = fromLeft + 1; i < toRight; i += 1 ) {
				if ( !array[i].equals(key) ) {
					return i;
				}
			}
			return i;
		}
		return fromLeft + 1;
	}

	/**
	 * Searches for the most left index starting with a certain prefix.
	 * @param array the array to be searched
	 * @param fromRight the index of the first element (inclusive) to be searched
	 * @param toLeft the index of the last element (inclusive) to be searched
	 * @param prefix the prefix to match
	 * @return the most left index of a value with the prefix
	 */
	public static int trackLeftByPrefix ( final String[] array, final int fromRight, final int toLeft, final String prefix ) {
		if ( fromRight >= toLeft ) {
			int i;
			for ( i = fromRight - 1; i >= toLeft; i -= 1 ) {
				if ( !array[i].startsWith(prefix) ) {
					return i + 1;
				}
			}
			return i + 1;
		}
		return fromRight;
	}

	/**
	 * Searches for the most right index starting with a certain prefix.
	 * @param array the array to be searched
	 * @param fromLeft the index of the first element (inclusive) to be searched
	 * @param toRight the index of the last element (exclusive) to be searched
	 * @param prefix the prefix to match
	 * @return the most right index of a value without the prefix
	 */
	public static int trackRightByPrefix ( final String[] array, final int fromLeft, final int toRight, final String prefix ) {
		if ( fromLeft < toRight ) {
			int i;
			for ( i = fromLeft + 1; i < toRight; i += 1 ) {
				if ( !array[i].startsWith(prefix) ) {
					return i;
				}
			}
			return i;
		}
		return fromLeft + 1;
	}

	public static boolean indexedSorted ( final int[] indices, final String[] values, final int from, final int to ) {
		final int fromStart = from + 1;
		if ( fromStart < to ) {
			String elementPrev = values[indices[from]];
			for ( int i = fromStart; i < to; i += 1 ) {
				final String elementCurr = values[indices[i]];
				if ( elementPrev.compareTo(elementCurr) > 0 ) {
					return false;
				}
				elementPrev = elementCurr;
			}
		}
		return true;
	}

	public static boolean indexedSortedDesc ( final int[] indices, final String[] values, final int from, final int to ) {
		final int fromStart = from + 1;
		if ( fromStart < to ) {
			String elementPrev = values[indices[from]];
			for ( int i = fromStart; i < to; i += 1 ) {
				final String elementCurr = values[indices[i]];
				if ( elementPrev.compareTo(elementCurr) < 0 ) {
					return false;
				}
				elementPrev = elementCurr;
			}
		}
		return true;
	}

}
