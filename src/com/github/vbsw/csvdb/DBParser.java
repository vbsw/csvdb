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
public class DBParser {

	public static int skipLine ( final byte[] bytes, int fromLeft, final int toRight ) {
		while ( fromLeft < toRight ) {
			byte c = bytes[fromLeft];
			if ( c != '\n' ) {
				fromLeft += 1;
			} else {
				return fromLeft + 1;
			}
		}
		return fromLeft;
	}

	public static boolean endsWithNewLine ( final byte[] bytes, final int fromRight, final int toLeft ) {
		return fromRight < toLeft && bytes[toLeft - 1] == '\n';
	}

	public static boolean isNotEmptyWhitespace ( final byte[] buffer, final int from, final int to ) {
		return from < to && DBParser.isWhitespace(buffer,from,to);
	}

	public static boolean isWhitespace ( final byte[] bytes, final int from, final int to ) {
		for ( int i = from; i < to; i += 1 ) {

			if ( isWhitespace(bytes[i]) == false ) {
				return false;
			}
		}
		return true;
	}

	public static boolean isWhitespace ( final byte character ) {
		return character >= 0 && character <= 32;
	}

	public static boolean isWhitespace ( final char character ) {
		return character >= 0 && character <= 32;
	}

	public static boolean isWhitespace ( final String string ) {
		for ( int i = 0; i < string.length(); i += 1 ) {
			final char character = string.charAt(i);

			if ( isWhitespace(character) == false ) {
				return false;
			}
		}
		return true;
	}

	public static int skipWhitespace ( final byte[] bytes, int fromLeft, final int toRight ) {
		while ( fromLeft < toRight && isWhitespace(bytes[fromLeft]) ) {
			fromLeft += 1;
		}
		return fromLeft;
	}

	public static int skipWhitespace ( final String string, int offset ) {
		while ( offset < string.length() && isWhitespace(string.charAt(offset)) ) {
			offset += 1;
		}
		return offset;
	}

	public static int skipNonWhitespace ( final String string, int offset ) {
		while ( offset < string.length() && !isWhitespace(string.charAt(offset)) ) {
			offset += 1;
		}
		return offset;
	}

	public static int skipLineBreak ( final byte[] bytes, int offset, final int limit ) {
		if ( offset < limit ) {

			if ( bytes[offset] == '\n' ) {
				return offset + 1;

			} else if ( bytes[offset] == '\r' ) {

				if ( offset + 1 < limit ) {

					if ( bytes[offset + 1] == '\n' ) {
						return offset + 2;

					} else {
						return offset + 1;
					}

				} else {
					return offset + 1;
				}

			} else {
				return offset;
			}

		} else {
			return offset;
		}
	}

	public static int skipToLineEnd ( final byte[] bytes, int offset, final int limit ) {
		while ( offset < limit ) {
			byte c = bytes[offset];

			if ( c != '\r' && c != '\n' ) {
				offset += 1;

			} else {
				break;
			}
		}

		return offset;
	}

	public static int skipWhitespaceReverse ( final byte[] bytes, int from ) {
		while ( from >= 0 ) {
			from -= 1;

			if ( !isWhitespace(bytes[from]) ) {
				break;
			}
		}
		return from + 1;
	}

	public static int skipWhitespaceReverse ( final byte[] bytes, int fromRigth, int toLeft ) {
		while ( fromRigth >= toLeft ) {
			fromRigth -= 1;

			if ( !isWhitespace(bytes[fromRigth]) ) {
				return fromRigth + 1;
			}
		}
		return fromRigth;
	}

	public static int skipWhitespaceReverse ( final String line, int offset ) {
		while ( offset >= 0 ) {
			offset -= 1;

			if ( !isWhitespace(line.charAt(offset)) ) {
				break;
			}
		}
		return offset + 1;
	}

}
