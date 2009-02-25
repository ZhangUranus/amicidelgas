package org.domain.SeamAmiciDelGas.session;

import java.io.Serializable;

public class Message implements Serializable{
	private String recipient;
	private String content;
	public String getRecipient() { return recipient; }
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getContent() { return content; }
	public void setContent(String content) {
		this.content = content;
	}
}

