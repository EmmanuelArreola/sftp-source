package com.cloudgen.n3xgen.SFTP.utils;


import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.validation.annotation.Validated;

/**
 *
 *  @author David Turanski
 * @author Artem Bilan
 */
@ConfigurationProperties("file.consumer")
@Validated
public class FileConsumerProperties {

	/**
	 * The FileReadingMode to use for file reading sources.
	 * Values are 'ref' - The File object,
	 * 'lines' - a message per line, or
	 * 'contents' - the contents as bytes.
	 */
	private FileReadingMode mode = FileReadingMode.contents;

	/**
	 * Set to true to emit start of file/end of file marker messages before/after the data.
	 * Only valid with FileReadingMode 'lines'.
	 */
	private Boolean withMarkers = null;

	/**
	 * When 'fileMarkers == true', specify if they should be produced
	 * as FileSplitter.FileMarker objects or JSON.
	 */
	private boolean markersJson = true;

	@NotNull
	public FileReadingMode getMode() {
		return this.mode;
	}

	public void setMode(FileReadingMode mode) {
		this.mode = mode;
	}

	public Boolean getWithMarkers() {
		return this.withMarkers;
	}

	public void setWithMarkers(Boolean withMarkers) {
		this.withMarkers = withMarkers;
	}

	public boolean getMarkersJson() {
		return this.markersJson;
	}

	public void setMarkersJson(boolean markersJson) {
		this.markersJson = markersJson;
	}

	@AssertTrue(message = "withMarkers can only be supplied when FileReadingMode is 'lines'")
	public boolean isWithMarkersValid() {
		return this.withMarkers == null || FileReadingMode.lines == this.mode;
	}
}