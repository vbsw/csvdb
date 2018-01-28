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
public class DBIndexStringUnique extends DBIndex {

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
	 * @param valuesFrom the left index of the range to be build, inclusive
	 * @param valuesTo the right index of the range to be copied, exclusive (This index may lie outside the array.)
	 */
	protected void buildIndexOfRange ( final String[] values, final int valuesFrom, final int valuesTo ) {
		for ( int row = valuesFrom; row < valuesTo; row += 1 ) {
			addRow(values,row);
		}
	}

	public int searchRow ( final String[] values, final String key ) {
		return searchRowOfRange(values,0,super.length,key);
	}

	protected int searchRowOfRange ( final String[] values, final int rowsFrom, final int rowsTo, final String key ) {
		final int rowIndex = DBArrays.indexedBinarySearchOfRange(super.rows,values,rowsFrom,rowsTo,key);
		if ( rowIndex >= 0 ) {
			return super.rows[rowIndex];
		}
		return rowIndex;
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
