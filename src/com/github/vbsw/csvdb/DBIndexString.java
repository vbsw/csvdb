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
public class DBIndexString extends DBIndex {

	/**
	 * Builds the index from a String array.
	 * @param values the array from which the index is to be build
	 */
	public void buildIndex ( final String[] values ) {
		buildIndexOfRange(values,0,values.length);
	}

	/**
	 * Builds the index from a String array.
	 * @param values the array from which the index is to be build
	 * @param valuesFrom the left index of the range to be build, inclusive
	 * @param valuesTo the right index of the range to be copied, exclusive (This index may lie outside the array.)
	 */
	protected void buildIndexOfRange ( final String[] values, final int valuesFrom, final int valuesTo ) {
		for ( int row = valuesFrom; row < valuesTo; row += 1 ) {
			addRow(values,row);
		}
	}

	public int searchRowIndexBegin ( final String[] values, final String key ) {
		return searchRowIndexBeginOfRange(values,0,super.length,key);
	}

	protected int searchRowIndexBeginOfRange ( final String[] values, final int rowsFrom, final int rowsTo, final String key ) {
		final int rowIndex = DBArrays.indexedBinarySearchOfRange(super.rows,values,rowsFrom,rowsTo,key);
		if ( rowIndex >= 0 ) {
			final int rowLeftIndex = DBArrays.indexedTrackLeft(super.rows,values,rowIndex,rowsFrom);
			return rowLeftIndex;
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
		final int rowLeftIndex = searchRowIndexBegin(values,key);
		if ( rowLeftIndex >= 0 ) {
			if ( super.rows[rowLeftIndex] == row ) {
				deleteIndex(rowLeftIndex);
			} else {
				for ( int rowIndex = rowLeftIndex + 1; rowIndex < super.length && values[super.rows[rowIndex]].equals(key); rowIndex += 1 ) {
					if ( super.rows[rowIndex] == row ) {
						deleteIndex(rowIndex);
					}
				}
			}
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
