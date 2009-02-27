package org.domain.SeamAmiciDelGas.session;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable{
	private String recipient;
	private String content;
	private boolean broadcast;
	public List<String> recipients;
	public String getRecipient() { return recipient; }
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getContent() { return content; }
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isBroadcast() {
		return broadcast;
	}
	public void setBroadcast(boolean broadcast) {
		this.broadcast = broadcast;
	}
	public List<String> getRecipients() {
		return recipients;
	}
	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}
}

