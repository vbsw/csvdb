/**
 *   Copyright 2018, Vitali Baumtrok <vbsw@mailbox.org>.
 * Distributed under the Boost Software License, Version 1.0.
 *      (See accompanying file BSL-1.0.txt or copy at
 *        http://www.boost.org/LICENSE_1_0.txt)
 */


package com.github.vbsw.csvdb;


/**
 * @author Vitali Baumtrok
 */
public class DBStringIndexUnique extends DBIndex {

	/**
	 * Builds the index from a String array. All values must be unique.
	 * @param values the array from which the index is to be build
	 */
	public void buildIndex ( final String[] values ) {
		buildIndexOfRange(values,0,values.length);
	}

	/**
	 * Builds the index from a String array. All values must be unique.
	 * @param values the array from which the index is to be build
	 * @param from the initial index of the range to be build, inclusive
	 * @param to the final index of the range to be copied, exclusive (This index may lie outside the array.)
	 */
	protected void buildIndexOfRange ( final String[] values, final int from, final int to ) {
		for ( int i = from; i < to; i += 1 ) {
			addRow(values,i);
		}
	}

	public int searchRow ( final String[] values, final String key ) {
		return searchRowOfRange(values,0,super.length,key);
	}

	protected int searchRowOfRange ( final String[] values, final int from, final int to, final String key ) {
		final int index = DBArrays.indexedBinarySearchOfRange(super.rows,values,from,to,key);
		if ( index >= 0 ) {
			return super.rows[index];
		}
		return index;
	}

	public void updateRow ( final String[] values, final int row, final String keyNew ) {
		deleteRow(values,row);
		values[row] = keyNew;
		addRow(values,row);
	}

	public void deleteRow ( final String[] values, final int row ) {
		final String key = values[row];
		final int index = DBArrays.indexedBinarySearchOfRange(super.rows,values,0,super.length,key);
		if ( index >= 0 ) {
			values[row] = null;
			deleteIndex(index);
		}
	}

	public void addRow ( final String[] values, final int row ) {
		final String key = values[row];
		final int index = DBArrays.indexedBinarySearchOfRange(super.rows,values,0,super.length,key);
		if ( index < 0 ) {
			insertIndex(-index - 1,row);
		} else {
			insertIndex(index,row);
		}
	}

}
