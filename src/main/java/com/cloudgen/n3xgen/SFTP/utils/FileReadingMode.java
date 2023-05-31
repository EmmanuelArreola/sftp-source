package com.cloudgen.n3xgen.SFTP.utils;

/**
 * Defines the supported modes of reading and processing files.
 *
 * @author Gunnar Hillert
 * @author David Turanski
 */
public enum FileReadingMode {
	/**
	 * ref mode.
	 */
	ref,
	/**
	 * lines mode.
	 */
	lines,
	/**
	 * contents mode.
	 */
	contents;
}