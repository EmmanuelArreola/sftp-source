package com.cloudgen.n3xgen.SFTP.utils;

import java.util.Collections;

import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.file.splitter.FileSplitter;
import org.springframework.integration.file.transformer.FileToByteArrayTransformer;
import org.springframework.integration.transformer.StreamTransformer;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.MimeTypeUtils;

/**
 * @author Gary Russell
 * @author Artem Bilan
 * @author Christian Tzolov
 */
public final class FileUtils {

	private FileUtils() {

	}

	/**
	 * Enhance an {@link IntegrationFlowBuilder} to add flow snippets, depending on
	 * {@link FileConsumerProperties}.
	 *
	 * @param flowBuilder            the flow builder.
	 * @param fileConsumerProperties the properties.
	 * @return the updated flow builder.
	 */
	public static IntegrationFlowBuilder enhanceFlowForReadingMode(IntegrationFlowBuilder flowBuilder,
																FileConsumerProperties fileConsumerProperties) {
		switch (fileConsumerProperties.getMode()) {
			case contents:
				flowBuilder.enrichHeaders(Collections.<String, Object>singletonMap(MessageHeaders.CONTENT_TYPE,
						MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE))
						.transform(new FileToByteArrayTransformer());
				break;
			case lines:
				Boolean withMarkers = fileConsumerProperties.getWithMarkers();
				if (withMarkers == null) {
					withMarkers = false;
				}
				flowBuilder.enrichHeaders(Collections.<String, Object>singletonMap(MessageHeaders.CONTENT_TYPE,
						MimeTypeUtils.TEXT_PLAIN_VALUE))
						.split(new FileSplitter(true, withMarkers, fileConsumerProperties.getMarkersJson()));
				break;
			case ref:
				flowBuilder.enrichHeaders(Collections.<String, Object>singletonMap(MessageHeaders.CONTENT_TYPE,
						MimeTypeUtils.APPLICATION_JSON_VALUE));
				break;
			default:
				throw new IllegalArgumentException(fileConsumerProperties.getMode().name() +
						" is not a supported file reading mode.");
		}
		return flowBuilder;
	}

	/**
	 * Enhance an {@link IntegrationFlowBuilder} to add flow snippets, depending on
	 * {@link FileConsumerProperties}; used for streaming sources.
	 *
	 * @param flowBuilder            the flow builder.
	 * @param fileConsumerProperties the properties.
	 * @return the updated flow builder.
	 */
	public static IntegrationFlowBuilder enhanceStreamFlowForReadingMode(IntegrationFlowBuilder flowBuilder,
																		FileConsumerProperties fileConsumerProperties) {
		switch (fileConsumerProperties.getMode()) {
			case contents:
				flowBuilder.enrichHeaders(Collections.<String, Object>singletonMap(MessageHeaders.CONTENT_TYPE,
						MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE))
						.transform(new StreamTransformer());
				break;
			case lines:
				Boolean withMarkers = fileConsumerProperties.getWithMarkers();
				if (withMarkers == null) {
					withMarkers = false;
				}
				flowBuilder.enrichHeaders(Collections.<String, Object>singletonMap(MessageHeaders.CONTENT_TYPE,
						MimeTypeUtils.TEXT_PLAIN_VALUE))
						.split(new FileSplitter(true, withMarkers, fileConsumerProperties.getMarkersJson()));
				break;
			case ref:
			default:
				throw new IllegalArgumentException(fileConsumerProperties.getMode().name() +
						" is not a supported file reading mode when streaming.");
		}
		return flowBuilder;
	}

}