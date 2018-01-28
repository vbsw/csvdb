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
public abstract class DBIndex {

	protected static final int INITIAL_ROWS_CAPACITY = 10;

	protected int[] rows;
	protected int length;

	protected DBIndex ( ) {
		this(INITIAL_ROWS_CAPACITY);
	}

	protected DBIndex ( final int initialCapacity ) {
		rows = new int[initialCapacity];
		length = 0;
	}

	protected void clearIndex ( ) {
		length = 0;
	}

	protected void insertIndex ( final int index, final int row ) {
		ensureCapacity();
		if ( index == length ) {
			rows[index] = row;
		} else {
			System.arraycopy(rows,index,rows,index + 1,length - index);
			rows[index] = row;
		}
		length += 1;
	}

	protected void deleteIndex ( final int index ) {
		final int indexNext = index + 1;
		if ( indexNext < length ) {
			System.arraycopy(rows,indexNext,rows,index,length - indexNext);
		}
		length -= 1;
	}

	protected void ensureCapacity ( ) {
		if ( length == rows.length ) {
			final int[] rowsNew = new int[rows.length * 2];
			System.arraycopy(rows,0,rowsNew,0,rows.length);
			rows = rowsNew;
		}
	}

}
