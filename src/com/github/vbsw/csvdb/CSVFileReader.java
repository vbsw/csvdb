/**
 *   Copyright 2018, Vitali Baumtrok <vbsw@mailbox.org>.
 * Distributed under the Boost Software License, Version 1.0.
 *      (See accompanying file BSL-1.0.txt or copy at
 *        http://www.boost.org/LICENSE_1_0.txt)
 */


package com.github.vbsw.csvdb;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;


/**
 * @author Vitali Baumtrok
 */
public class CSVFileReader {

	protected static final int INITIAL_BUFFER_CAPACITY = 1000;

	protected ByteBuffer buffer;
	protected CSVLine lineReader;
	protected int bytesRead;
	protected int lineNumber;
	protected int lineBegin;
	protected int lineEnd;

	public CSVFileReader ( ) {
		this(INITIAL_BUFFER_CAPACITY);
	}

	public CSVFileReader ( final int initialBufferCapacity ) {
		buffer = createByteBuffer(initialBufferCapacity);
		lineReader = createCSVLineReader();
	}

	public CSVLine getLineReader ( ) {
		return lineReader;
	}

	public void initialize ( ) {
		lineBegin = lineEnd = bytesRead = 1;
		lineNumber = 0;
	}

	public int getLineNumber ( ) {
		return lineNumber;
	}

	public boolean readNextLine ( final SeekableByteChannel channel ) throws IOException {
		readWholeLineIntoBuffer(channel);
		while ( bytesRead > 0 && DBParser.isWhitespace(buffer.array(),lineBegin,lineEnd) ) {
			lineNumber += 1;
			readWholeLineIntoBuffer(channel);
		}
		if ( bytesRead > 0 ) {
			lineReader.readBytes(buffer.array(),lineBegin,lineEnd);
			lineNumber += 1;
			return true;
		}
		return false;
	}

	protected void readWholeLineIntoBuffer ( final SeekableByteChannel channel ) throws IOException {
		if ( lineEnd == bytesRead ) {
			lineBegin = lineEnd = 0;
			bytesRead = channel.read(buffer);
		}
		if ( bytesRead > 0 ) {
			byte[] bufferBytes = buffer.array();
			lineBegin = lineEnd;
			lineEnd = DBParser.skipLine(bufferBytes,lineBegin,bytesRead);

			while ( moreBytesToLoad(bufferBytes) ) {
				final int lineLength = bytesRead - lineBegin;
				if ( lineBegin > 0 ) {
					System.arraycopy(bufferBytes,lineBegin,bufferBytes,0,lineLength);
					lineBegin = 0;
				} else {
					buffer = createByteBufferExtended(bufferBytes,lineBegin,lineLength);
					bufferBytes = buffer.array();
				}
				buffer.position(lineLength);
				bytesRead = lineLength + channel.read(buffer);
				lineEnd = DBParser.skipLine(bufferBytes,lineLength,bytesRead);
			}
		}
	}

	protected CSVLine createCSVLineReader ( ) {
		final CSVLine lineReader = new CSVLine();
		return lineReader;
	}

	protected ByteBuffer createByteBuffer ( final int capacity ) {
		final ByteBuffer buffer = ByteBuffer.allocate(capacity);
		return buffer;
	}

	protected ByteBuffer createByteBufferExtended ( final byte[] bytes, final int from, final int copyLength ) {
		final ByteBuffer bufferNew = ByteBuffer.allocate(bytes.length * 2);
		final byte[] bufferBytesNew = bufferNew.array();
		System.arraycopy(bytes,from,bufferBytesNew,0,copyLength);
		return bufferNew;
	}

	private boolean moreBytesToLoad ( final byte[] bufferBytes ) {
		return !DBParser.endsWithNewLine(bufferBytes,lineEnd,lineBegin) && bytesRead == bufferBytes.length;
	}
}
