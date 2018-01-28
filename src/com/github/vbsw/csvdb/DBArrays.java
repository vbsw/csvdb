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
	 * @param indicesFrom the indices index of the first element (inclusive) to be searched
	 * @param indicesTo the indices index of the last element (exclusive) to be searched
	 * @param key the value to be searched for
	 * @return index of indices of the search key, if it is contained in the array; otherwise, (-(insertion point) - 1). The insertion point is defined as the point at which the key would be inserted into the array: the index of the first element greater than the key, or a.length if all elements in the array are less than the specified key. Note that this guarantees that the return value will be >= 0 if and only if the key is found.
	 */
	public static int indexedBinarySearchOfRange ( final int[] indices, final String[] values, final int indicesFrom, final int indicesTo, final String key ) {
		int left = indicesFrom;
		int right = indicesTo - 1;

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
	 * @param arrayFromRight the index of the first element (inclusive) to be searched
	 * @param arrayToLeft the index of the last element (inclusive) to be searched
	 * @return the most left index equal to array[from]
	 */
	public static int trackLeft ( final String[] array, final int arrayFromRight, final int arrayToLeft ) {
		if ( arrayFromRight >= arrayToLeft ) {
			final String key = array[arrayFromRight];
			int i;
			for ( i = arrayFromRight - 1; i >= arrayToLeft; i -= 1 ) {
				if ( !array[i].equals(key) ) {
					return i + 1;
				}
			}
			return i + 1;
		}
		return arrayFromRight;
	}

	public static int indexedTrackLeft ( final int[] indices, final String[] values, final int indicesFromRight, final int indicesToLeft ) {
		if ( indicesFromRight >= indicesToLeft ) {
			final String key = values[indices[indicesFromRight]];
			int i;
			for ( i = indicesFromRight - 1; i >= indicesToLeft; i -= 1 ) {
				if ( !values[indices[i]].equals(key) ) {
					return i + 1;
				}
			}
			return i + 1;
		}
		return indicesFromRight;
	}

	/**
	 * Searches for the most right index equal to the value of array[from].
	 * @param array the array to be searched
	 * @param arrayFromLeft the index of the first element (inclusive) to be searched
	 * @param arrayToRight the index of the last element (exclusive) to be searched
	 * @return the most right index not equal to array[from]
	 */
	public static int trackRight ( final String[] array, final int arrayFromLeft, final int arrayToRight ) {
		if ( arrayFromLeft < arrayToRight ) {
			final String key = array[arrayFromLeft];
			int i;
			for ( i = arrayFromLeft + 1; i < arrayToRight; i += 1 ) {
				if ( !array[i].equals(key) ) {
					return i;
				}
			}
			return i;
		}
		return arrayFromLeft + 1;
	}

	/**
	 * Searches for the most left index starting with a certain prefix.
	 * @param array the array to be searched
	 * @param arrayFromRight the index of the first element (inclusive) to be searched
	 * @param arrayToLeft the index of the last element (inclusive) to be searched
	 * @param prefix the prefix to match
	 * @return the most left index of a value with the prefix
	 */
	public static int trackLeftByPrefix ( final String[] array, final int arrayFromRight, final int arrayToLeft, final String prefix ) {
		if ( arrayFromRight >= arrayToLeft ) {
			int i;
			for ( i = arrayFromRight - 1; i >= arrayToLeft; i -= 1 ) {
				if ( !array[i].startsWith(prefix) ) {
					return i + 1;
				}
			}
			return i + 1;
		}
		return arrayFromRight;
	}

	/**
	 * Searches for the most right index starting with a certain prefix.
	 * @param array the array to be searched
	 * @param arrayFromLeft the index of the first element (inclusive) to be searched
	 * @param arrayToRight the index of the last element (exclusive) to be searched
	 * @param prefix the prefix to match
	 * @return the most right index of a value without the prefix
	 */
	public static int trackRightByPrefix ( final String[] array, final int arrayFromLeft, final int arrayToRight, final String prefix ) {
		if ( arrayFromLeft < arrayToRight ) {
			int i;
			for ( i = arrayFromLeft + 1; i < arrayToRight; i += 1 ) {
				if ( !array[i].startsWith(prefix) ) {
					return i;
				}
			}
			return i;
		}
		return arrayFromLeft + 1;
	}

	public static boolean indexedSorted ( final int[] indices, final String[] values, final int indicesFrom, final int indicesTo ) {
		final int fromStart = indicesFrom + 1;
		if ( fromStart < indicesTo ) {
			String elementPrev = values[indices[indicesFrom]];
			for ( int i = fromStart; i < indicesTo; i += 1 ) {
				final String elementCurr = values[indices[i]];
				if ( elementPrev.compareTo(elementCurr) > 0 ) {
					return false;
				}
				elementPrev = elementCurr;
			}
		}
		return true;
	}

	public static boolean indexedSortedDesc ( final int[] indices, final String[] values, final int indicesFrom, final int indicesTo ) {
		final int fromStart = indicesFrom + 1;
		if ( fromStart < indicesTo ) {
			String elementPrev = values[indices[indicesFrom]];
			for ( int i = fromStart; i < indicesTo; i += 1 ) {
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
