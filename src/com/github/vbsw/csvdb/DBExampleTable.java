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
public class DBExampleTable extends DBTable {

	private static final int INITIAL_ARRAY_CAPACITY = 10;

	private String[] firstNames;
	private String[] lastNames;
	private int[] ages;
	private int length;

	private final DBIndexStringUnique lastNameIndex = new DBIndexStringUnique();
	private final DBFreeRowManager rowManager = new DBFreeRowManager();

	public DBExampleTable ( ) {
		this(INITIAL_ARRAY_CAPACITY);
	}

	public DBExampleTable ( final int initialCapacity ) {
		firstNames = new String[initialCapacity];
		lastNames = new String[initialCapacity];
		ages = new int[initialCapacity];
		length = 0;
	}

	public String getFirstName ( final int row ) {
		return firstNames[row];
	}

	public String getLastName ( final int row ) {
		return lastNames[row];
	}

	public int getAge ( final int row ) {
		return ages[row];
	}

	public void setFirstName ( final int row, final String firstName ) {
		firstNames[row] = firstName;
	}

	public void setLastName ( final int row, final String lastName ) {
		lastNameIndex.updateRow(lastNames,row,lastName);
	}

	public void setAge ( final int row, final int age ) {
		ages[row] = age;
	}

	public void deleteRow ( final int row ) {
		lastNameIndex.deleteRow(lastNames,row);
		rowManager.addFreeRow(row);
		length -= 1;
	}

	public int createRow ( ) {
		final int row = rowManager.getFreeRow();
		ensureCapacity();
		length += 1;
		return row;
	}

	protected void ensureCapacity ( ) {
		if ( length == firstNames.length ) {
			final int capacityNew = firstNames.length * 2;
			final String[] firstNamesNew = new String[capacityNew];
			final String[] lastNamesNew = new String[capacityNew];
			final int[] agesNew = new int[capacityNew];
			System.arraycopy(firstNames,0,firstNamesNew,0,firstNames.length);
			System.arraycopy(lastNames,0,lastNamesNew,0,lastNames.length);
			System.arraycopy(ages,0,agesNew,0,ages.length);
			firstNames = firstNamesNew;
			lastNames = lastNamesNew;
			ages = agesNew;
		}
	}

}
