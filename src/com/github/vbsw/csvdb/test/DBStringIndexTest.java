/**
 *   Copyright 2018, Vitali Baumtrok <vbsw@mailbox.org>.
 * Distributed under the Boost Software License, Version 1.0.
 *      (See accompanying file BSL-1.0.txt or copy at
 *        http://www.boost.org/LICENSE_1_0.txt)
 */


package com.github.vbsw.csvdb.test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.github.vbsw.csvdb.DBStringIndexUnique;


/**
 * @author Vitali Baumtrok
 */
class DBStringIndexTest extends DBStringIndexUnique {

	@Test
	void testBuildIndex ( ) {
		final DBStringIndexTest index = new DBStringIndexTest();
		final String[] values = new String[] { "ad", "ab", "ab", "af", "aa", "ah", "aj" };
		final int[] indicesSorted = new int[] { 4, 2, 1, 0, 3, 5, 6 };
		index.buildIndex(values);

		for ( int i = 0; i < indicesSorted.length; i += 1 ) {
			assertEquals(index.rows[i],indicesSorted[i]);
		}
	}

	@Test
	void testSearchRow ( ) {
		final DBStringIndexTest index = new DBStringIndexTest();
		final String[] values = new String[] { "ad", "ab", "af", "aa", "ah", "aj" };
		final int[] indicesSorted = new int[] { 3, 1, 0, 2, 4, 5 };
		index.buildIndex(values);

		for ( int i = 0; i < indicesSorted.length; i += 1 ) {
			assertEquals(i,index.searchRow(values,values[i]));
		}
		assertTrue(index.searchRow(values,"xyz") < 0);
	}

}
