/**
 *   Copyright 2018, Vitali Baumtrok <vbsw@mailbox.org>.
 * Distributed under the Boost Software License, Version 1.0.
 *      (See accompanying file BSL-1.0.txt or copy at
 *        http://www.boost.org/LICENSE_1_0.txt)
 */


package com.github.vbsw.csvdb;


import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;


/**
 * @author Vitali Baumtrok
 */
public class DBExampleFileRead {

	public static void readFromFile ( final DBExampleTable exampleTable, final Path filePath ) throws IOException {
		final CSVFileReader fileReader = new CSVFileReader();

		/* try-block is here to auto close channel */
		try ( final SeekableByteChannel channel = Files.newByteChannel(filePath,StandardOpenOption.READ) ) {
			fileReader.initialize();
			while ( fileReader.readNextLine(channel) ) {
				DBExampleFileRead.createEntryFromCSV(exampleTable,fileReader.getLineNumber(),fileReader.getLineReader());
			}
		} catch ( IOException e ) {
			throw e;
		}
	}

	private static void createEntryFromCSV ( final DBExampleTable exampleTable, final int lineNumber, final CSVLine csv ) {
		final Charset encoding = StandardCharsets.UTF_8;

		if ( csv.getValuesCount() == 3 ) {
			final String firstName = new String(csv.bytes,csv.valueSlices[0],csv.valueSlices[1] - csv.valueSlices[0],encoding);
			final String lastName = new String(csv.bytes,csv.valueSlices[2],csv.valueSlices[3] - csv.valueSlices[2],encoding);
			final String ageStr = new String(csv.bytes,csv.valueSlices[4],csv.valueSlices[5] - csv.valueSlices[4],encoding);
			final int age = Integer.valueOf(ageStr);

			final int row = exampleTable.createRow();
			exampleTable.setFirstName(row,firstName);
			exampleTable.setLastName(row,lastName);
			exampleTable.setAge(row,age);

		} else {
			System.err.println("wrong number of values in line " + lineNumber);
		}
	}

}
