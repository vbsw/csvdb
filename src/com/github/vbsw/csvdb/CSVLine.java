/**
 *   Copyright 2018, Vitali Baumtrok <vbsw@mailbox.org>.
 * Distributed under the Boost Software License, Version 1.0.
 *      (See accompanying file BSL-1.0.txt or copy at
 *        http://www.boost.org/LICENSE_1_0.txt)
 */


package com.github.vbsw.csvdb;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * @author Vitali Baumtrok
 */
public class CSVLine {

	protected static final int SLICES_INITIAL_CAPACITY = 10;
	protected static final byte DEFAULT_SEPARATOR = '|';

	public byte[] separator;
	public byte[] bytes;
	public int[] valueSlices;
	public int valueSlicesLength;

	public void reset ( ) {
		valueSlicesLength = 0;
	}

	public int getValuesCount ( ) {
		return valueSlicesLength / 2;
	}

	public void readBytes ( final byte[] bytes, int lineBegin, int lineEnd ) {
		lineBegin = DBParser.skipWhitespace(bytes,lineBegin,lineEnd);
		lineEnd = DBParser.skipWhitespaceReverse(bytes,lineEnd,lineBegin);
		this.bytes = bytes;

		if ( separator == null ) {
			readLineWithDefaultSeparator(lineBegin,lineEnd);
		} else {
			readLineWithCustomSeparator(lineBegin,lineEnd);
		}
	}

	public String getStringValue ( final int index ) {
		return getStringValue(index,StandardCharsets.UTF_8);
	}

	public String getStringValue ( final int index, final Charset encoding ) {
		final int offset = index * 2;
		final String value = new String(bytes,valueSlices[offset],valueSlices[offset + 1] - valueSlices[offset],encoding);
		return value;
	}

	protected void readLineWithDefaultSeparator ( final int lineBegin, final int lineEnd ) {
		int left = lineBegin;
		int separatorIndex = searchForDefaultSeparator(lineBegin,lineEnd);
		int right;
		while ( separatorIndex < lineEnd ) {
			right = DBParser.skipWhitespaceReverse(bytes,separatorIndex,left);
			addValue(left,right);
			separatorIndex += 1;
			left = DBParser.skipWhitespace(bytes,separatorIndex,lineEnd);
			separatorIndex = searchForDefaultSeparator(separatorIndex,lineEnd);
		}
		right = DBParser.skipWhitespaceReverse(bytes,separatorIndex,left);
		addValue(left,right);
	}

	protected void readLineWithCustomSeparator ( final int lineBegin, final int lineEnd ) {
		// TODO Auto-generated method stub
	}

	protected void ensureValueSlicesCapacity ( ) {
		if ( valueSlices == null ) {
			valueSlices = new int[SLICES_INITIAL_CAPACITY];
		} else if ( valueSlices.length == valueSlicesLength ) {
			final int capacityNew = valueSlices.length * 2;
			final int[] valueSlicesNew = new int[capacityNew];
			System.arraycopy(valueSlices,0,valueSlicesNew,0,valueSlices.length);
			valueSlices = valueSlicesNew;
		}
	}

	protected void addValue ( final int from, final int to ) {
		ensureValueSlicesCapacity();
		valueSlices[valueSlicesLength] = from;
		valueSlices[valueSlicesLength + 1] = to;
		valueSlicesLength += 2;
	}

	private int searchForDefaultSeparator ( int fromLeft, final int toRight ) {
		while ( fromLeft < toRight && bytes[fromLeft] != DEFAULT_SEPARATOR ) {
			fromLeft += 1;
		}
		return fromLeft;
	}

}
