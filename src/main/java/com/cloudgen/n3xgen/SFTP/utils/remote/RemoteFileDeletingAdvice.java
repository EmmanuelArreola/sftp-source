package com.cloudgen.n3xgen.SFTP.utils.remote;

import org.springframework.integration.aop.MessageSourceMutator;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.remote.RemoteFileTemplate;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

/**
 * A {@link MessageSourceMutator} that deletes a remote file on success.
 *
 * @author David Turanski
 * @author Artem Bilan
 *
 */
public class RemoteFileDeletingAdvice implements MessageSourceMutator {

	private final RemoteFileTemplate<?> template;

	private final String remoteFileSeparator;

	/**
	 * Construct an instance with the provided template and separator.
	 * @param template the template.
	 * @param remoteFileSeparator the separator.
	 */
	public RemoteFileDeletingAdvice(RemoteFileTemplate<?> template,
			String remoteFileSeparator) {
		this.template = template;
		this.remoteFileSeparator = remoteFileSeparator;
	}

	@Nullable
	@Override
	public Message<?> afterReceive(@Nullable Message<?> result, MessageSource<?> source) {
		if (result != null) {
			String remoteDir = (String) result.getHeaders().get(FileHeaders.REMOTE_DIRECTORY);
			String remoteFile = (String) result.getHeaders().get(FileHeaders.REMOTE_FILE);
			this.template.remove(remoteDir + this.remoteFileSeparator + remoteFile);
		}
		return result;
	}
}