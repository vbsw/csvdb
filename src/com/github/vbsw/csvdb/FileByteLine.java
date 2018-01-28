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
public class FileByteLine {

	public byte[] bytes;
	public int lineNumber;
	public int lineBegin;
	public int lineEnd;

	public void reset ( ) {
		bytes = null;
		lineNumber = 0;
		lineBegin = 0;
		lineEnd = 0;
	}

}
