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
public class DBFreeRowManager {

	protected static final int INITIAL_ARRAY_CAPACITY = 10;

	protected int[] freeRows;
	protected int length;

	public DBFreeRowManager ( ) {
		this(INITIAL_ARRAY_CAPACITY);
	}

	public DBFreeRowManager ( final int initialCapacity ) {
		freeRows = new int[initialCapacity];
		freeRows[0] = 0;
		length = 1;
	}

	public void addFreeRow ( final int row ) {
		ensureCapacity();
		freeRows[length] = row;
		length += 1;
	}

	public int getFreeRow ( ) {
		final int row;
		if ( length == 1 ) {
			row = freeRows[0];
			freeRows[0] = row + 1;
		} else {
			length -= 1;
			row = freeRows[length];
		}
		return row;
	}

	protected void ensureCapacity ( ) {
		if ( length == freeRows.length ) {
			final int[] freeRowsNew = new int[freeRows.length * 2];
			System.arraycopy(freeRows,0,freeRowsNew,0,freeRows.length);
			freeRows = freeRowsNew;
		}
	}

}
