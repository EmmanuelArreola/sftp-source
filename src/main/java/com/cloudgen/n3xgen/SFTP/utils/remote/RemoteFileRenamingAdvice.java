package com.cloudgen.n3xgen.SFTP.utils.remote;

import org.springframework.expression.Expression;
import org.springframework.integration.aop.MessageSourceMutator;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.remote.RemoteFileTemplate;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;


/**
 * A {@link MessageSourceMutator} that renames a remote file on success.
 *
 * @author Andrea Montemaggio
 *
 */
public class RemoteFileRenamingAdvice implements MessageSourceMutator {

	private final RemoteFileTemplate<?> template;

	private final String remoteFileSeparator;

	private final Expression newName;

	/**
	 * Construct an instance with the provided template and separator.
	 * @param template the template.
	 * @param remoteFileSeparator the separator.
	 * @param newNameExp the SpEl expression for the new name.
	 */
	public RemoteFileRenamingAdvice(RemoteFileTemplate<?> template,
									String remoteFileSeparator,
									Expression newNameExp) {
		this.template = template;
		this.remoteFileSeparator = remoteFileSeparator;
		this.newName = newNameExp;
	}

	@Nullable
	@Override
	public Message<?> afterReceive(@Nullable Message<?> result, MessageSource<?> source) {
		if (result != null) {
			String remoteDir = (String) result.getHeaders().get(FileHeaders.REMOTE_DIRECTORY);
			String remoteFile = (String) result.getHeaders().get(FileHeaders.REMOTE_FILE);
			String newNameValue = this.newName.getValue(result, String.class);
			if (newNameValue != null && !newNameValue.isEmpty()) {
				this.template.rename(remoteDir + this.remoteFileSeparator + remoteFile, newNameValue);
			}
		}
		return result;
	}
}