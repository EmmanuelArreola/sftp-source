package com.cloudgen.n3xgen.SFTP.bean;

import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.remote.aop.RotatingServerAdvice;
import org.springframework.integration.file.remote.aop.StandardRotationPolicy;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * An {@link RotatingServerAdvice} for listing files on multiple directories/servers.
 *
 * @author Gary Russell
 * @author David Turanski
 * @since 2.0
 */
public class SftpSupplierRotator extends RotatingServerAdvice {

	private static String SFTP_SELECTED_SERVER_PROPERTY_KEY = "sftp_selectedServer";

	private final SftpSupplierProperties properties;

	private final StandardRotationPolicy rotationPolicy;

	public SftpSupplierRotator(SftpSupplierProperties properties, StandardRotationPolicy rotationPolicy) {
		super(rotationPolicy);
		this.properties = properties;
		this.rotationPolicy = rotationPolicy;
	}

	public String getCurrentKey() {
		return this.rotationPolicy.getCurrent().getKey().toString();
	}

	public String getCurrentDirectory() {
		return this.rotationPolicy.getCurrent().getDirectory();
	}

	@Override
	public Message<?> afterReceive(Message<?> result, MessageSource<?> source) {
		if (result != null) {
			result = MessageBuilder.fromMessage(result)
					.setHeader(SFTP_SELECTED_SERVER_PROPERTY_KEY, this.getCurrentKey()).build();
		}
		this.rotationPolicy.afterReceive(result != null, source);
		return result;
	}
}