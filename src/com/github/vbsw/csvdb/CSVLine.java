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

	public void parseLine ( final byte[] bytes, int lineBegin, int lineEnd ) {
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
			separatorIndex = searchForDefaultSeparator(left,lineEnd);
		}
		right = DBParser.skipWhitespaceReverse(bytes,separatorIndex,left);
		addValue(left,right);
	}

	protected void readLineWithCustomSeparator ( final int lineBegin, final int lineEnd ) {
		int left = lineBegin;
		int separatorIndex = searchForCustomSeparator(lineBegin,lineEnd);
		int right;
		while ( separatorIndex < lineEnd ) {
			right = DBParser.skipWhitespaceReverse(bytes,separatorIndex,left);
			addValue(left,right);
			separatorIndex += separator.length;
			left = DBParser.skipWhitespace(bytes,separatorIndex,lineEnd);
			separatorIndex = searchForCustomSeparator(left,lineEnd);
		}
		right = DBParser.skipWhitespaceReverse(bytes,separatorIndex,left);
		addValue(left,right);
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

	private int searchForCustomSeparator ( int fromLeft, final int toRight ) {
		while ( fromLeft < toRight && !matchesCustomSeparator(fromLeft,toRight) ) {
			fromLeft += 1;
		}
		return fromLeft;
	}

	private boolean matchesCustomSeparator ( final int fromLeft, final int toRight ) {
		if ( toRight - fromLeft >= separator.length ) {
			for ( int i = 0; i < separator.length; i += 1 ) {
				if ( bytes[fromLeft + i] != separator[i] ) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

}
