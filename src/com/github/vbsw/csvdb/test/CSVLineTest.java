/**
 *   Copyright 2018, Vitali Baumtrok <vbsw@mailbox.org>.
 * Distributed under the Boost Software License, Version 1.0.
 *      (See accompanying file BSL-1.0.txt or copy at
 *        http://www.boost.org/LICENSE_1_0.txt)
 */


package com.github.vbsw.csvdb.test;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.github.vbsw.csvdb.CSVLine;


/**
 * @author Vitali Baumtrok
 */
class CSVLineTest extends CSVLine {

	private int lineBegin = 0;
	private int lineEnd = 0;

	@Test
	void testReadLineWithDefaultSeparator ( ) {
		final CSVLineTest csv = new CSVLineTest();
		final String[] values = new String[] { "asdf", "qwer", "yxcv" };
		final String fileStr = "   asdf | qwer   |yxcv    ";
		final byte[] fileBytes = fileStr.getBytes();
		lineBegin = lineEnd = 0;
		csv.readBytes(fileBytes,0,fileBytes.length);

		assertEquals(3,csv.lineBegin);
		assertEquals(fileBytes.length - 4,csv.lineEnd);
		for ( int i = 0; i < values.length; i += 1 ) {
			assertEquals(values[i],csv.getStringValue(i));
		}
	}

	@Override
	protected void readLineWithDefaultSeparator ( final int lineBegin, final int lineEnd ) {
		this.lineBegin = lineBegin;
		this.lineEnd = lineEnd;
		super.readLineWithDefaultSeparator(lineBegin,lineEnd);
	}

}
