/**
 *   Copyright 2018, Vitali Baumtrok <vbsw@mailbox.org>.
 * Distributed under the Boost Software License, Version 1.0.
 *      (See accompanying file BSL-1.0.txt or copy at
 *        http://www.boost.org/LICENSE_1_0.txt)
 */


package com.github.vbsw.csvdb.test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.github.vbsw.csvdb.DBArrays;


/**
 * @author Vitali Baumtrok
 */
class DBArraysTest {

	@Test
	void testBinarySearch ( ) throws IOException {
		final String[] array1 = new String[] { "aa", "ab", "ad", "af", "ah", "aj" };
		final String[] array2 = new String[0];

		for ( int i = 0; i < array1.length; i += 1 ) {
			assertEquals(i,DBArrays.binarySearch(array1,array1[i]));
		}
		assertEquals(-1,DBArrays.binarySearch(array1,"a"));
		assertEquals(-3,DBArrays.binarySearch(array1,"ac"));
		assertEquals(-4,DBArrays.binarySearch(array1,"ae"));
		assertEquals(-5,DBArrays.binarySearch(array1,"ag"));
		assertEquals(-7,DBArrays.binarySearch(array1,"x"));

		assertEquals(-1,DBArrays.binarySearch(array2,array1[0]));
	}

	@Test
	void testIndexedBinarySearch ( ) {
		final int[] indices = new int[] { 4, 2, 1, 3, 5, 0 };
		final String[] values = new String[] { "aj", "ad", "ab", "af", "aa", "ah" };

		assertEquals(true,DBArrays.indexedSorted(indices,values,0,indices.length));

		assertEquals(5,DBArrays.indexedBinarySearch(indices,values,values[0]));
		assertEquals(2,DBArrays.indexedBinarySearch(indices,values,values[1]));
		assertEquals(1,DBArrays.indexedBinarySearch(indices,values,values[2]));
		assertEquals(3,DBArrays.indexedBinarySearch(indices,values,values[3]));
		assertEquals(0,DBArrays.indexedBinarySearch(indices,values,values[4]));
		assertEquals(4,DBArrays.indexedBinarySearch(indices,values,values[5]));
		assertEquals(-4,DBArrays.indexedBinarySearch(indices,values,"ae"));
	}

	@Test
	void testTrackLeft ( ) {
		final String[] array1 = new String[] { "aa", "ab", "ad", "af", "ah", "aj" };
		final String[] array2 = new String[] { "aa", "ab", "bb", "bb", "bb", "bj" };

		for ( int i = 0; i < array1.length; i += 1 ) {
			assertEquals(i,DBArrays.trackLeft(array1,i,0));
		}
		assertEquals(2,DBArrays.trackLeft(array2,2,0));
		assertEquals(2,DBArrays.trackLeft(array2,3,0));
		assertEquals(2,DBArrays.trackLeft(array2,4,0));
		assertEquals(2,DBArrays.trackLeft(array2,2,array2.length));
	}

	@Test
	void testTrackRight ( ) {
		final String[] array1 = new String[] { "aa", "ab", "ad", "af", "ah", "aj" };
		final String[] array2 = new String[] { "aa", "ab", "bb", "bb", "bb", "bj" };

		for ( int i = 0; i < array1.length; i += 1 ) {
			assertEquals(i + 1,DBArrays.trackRight(array1,i,array1.length));
		}
		assertEquals(5,DBArrays.trackRight(array2,2,array2.length));
		assertEquals(5,DBArrays.trackRight(array2,3,array2.length));
		assertEquals(5,DBArrays.trackRight(array2,4,array2.length));
		assertEquals(4,DBArrays.trackRight(array2,2,4));
		assertEquals(5,DBArrays.trackRight(array2,4,0));
	}

	@Test
	void testTrackLeftByPrefix ( ) {
		final String[] array1 = new String[] { "aa", "ab", "ad", "af", "ah", "aj" };
		final String[] array2 = new String[] { "aa", "ab", "bb", "bb", "bb", "bj" };

		for ( int i = 0; i <= array1.length; i += 1 ) {
			assertEquals(0,DBArrays.trackLeftByPrefix(array1,i,0,"a"));
		}
		assertEquals(2,DBArrays.trackLeftByPrefix(array2,2,0,"b"));
		assertEquals(2,DBArrays.trackLeftByPrefix(array2,3,0,"b"));
		assertEquals(2,DBArrays.trackLeftByPrefix(array2,4,0,"b"));
		assertEquals(2,DBArrays.trackLeftByPrefix(array2,2,array2.length,"b"));
	}

	@Test
	void testTrackRightByPrefix ( ) {
		final String[] array1 = new String[] { "aa", "ab", "ad", "af", "ah", "aj" };
		final String[] array2 = new String[] { "aa", "ab", "bb", "bb", "bb", "bj" };

		for ( int i = 0; i < array1.length; i += 1 ) {
			assertEquals(array1.length,DBArrays.trackRightByPrefix(array1,i,array1.length,"a"));
		}
		assertEquals(array2.length,DBArrays.trackRightByPrefix(array2,2,array2.length,"b"));
		assertEquals(array2.length,DBArrays.trackRightByPrefix(array2,3,array2.length,"b"));
		assertEquals(array2.length,DBArrays.trackRightByPrefix(array2,4,array2.length,"b"));
		assertEquals(4,DBArrays.trackRightByPrefix(array2,2,4,"b"));
		assertEquals(5,DBArrays.trackRightByPrefix(array2,4,0,"b"));
		assertEquals(2,DBArrays.trackRightByPrefix(array2,0,array2.length,"a"));
	}

	@Test
	void testIndexedSorted ( ) {
		final int[] indicesSorted = new int[] { 3, 1, 0, 2, 4, 5 };
		final int[] indicesNotSorted = new int[] { 5, 1, 0, 2, 4, 3 };
		final String[] values = new String[] { "ad", "ab", "af", "aa", "ah", "aj" };

		assertEquals(true,DBArrays.indexedSorted(indicesSorted,values,0,indicesSorted.length));
		assertEquals(false,DBArrays.indexedSorted(indicesNotSorted,values,0,indicesNotSorted.length));
		assertEquals(true,DBArrays.indexedSorted(indicesNotSorted,values,0,0));
		assertEquals(true,DBArrays.indexedSorted(indicesNotSorted,values,0,1));
		assertEquals(true,DBArrays.indexedSorted(indicesNotSorted,values,1,indicesNotSorted.length - 1));
	}

	@Test
	void testIndexedSortedDesc ( ) {
		final int[] indicesSorted1 = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };
		final int[] indicesSorted2 = new int[] { 3, 1, 0, 2, 4, 5 };
		final int[] indicesNotSorted = new int[] { 5, 1, 0, 2, 4, 3 };
		final String[] values1 = new String[] { "aj", "aj", "ah", "ah", "af", "ad", "ab", "aa" };
		final String[] values2 = new String[] { "af", "ah", "ad", "aj", "ab", "aa" };

		assertEquals(true,DBArrays.indexedSortedDesc(indicesSorted1,values1,0,indicesSorted2.length));
		assertEquals(true,DBArrays.indexedSortedDesc(indicesSorted2,values2,0,indicesSorted2.length));
		assertEquals(false,DBArrays.indexedSortedDesc(indicesNotSorted,values2,0,indicesNotSorted.length));
		assertEquals(true,DBArrays.indexedSortedDesc(indicesNotSorted,values2,0,0));
		assertEquals(true,DBArrays.indexedSortedDesc(indicesNotSorted,values2,0,1));
		assertEquals(true,DBArrays.indexedSortedDesc(indicesNotSorted,values2,1,indicesNotSorted.length - 1));
	}

}
