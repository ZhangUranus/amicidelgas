package org.domain.GAS.processes;

import java.util.ArrayList;
import java.util.List;

import org.domain.GAS.session.Message;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.annotations.bpm.StartTask;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.security.Credentials;


@Name("notifyBean")
@Scope(ScopeType.CONVERSATION)
public class NotifyBean {

	private String content;
	private List<String> recipients = new ArrayList<String>();
	
	private boolean broadcast;
	@Out(value="notifyMessage",scope=ScopeType.BUSINESS_PROCESS,required=false) 
	private Message message;
	@Out(value="sender", scope=ScopeType.BUSINESS_PROCESS, required=false)
	private String sender;
	@In private Credentials credentials;
	
	@CreateProcess(definition="notificatore")
	public String notifyMessage(){
		sender = credentials.getUsername();
		message=new Message();
		message.setContent(content);
		message.setRecipients(recipients);
		message.setBroadcast(broadcast);
		return "partito";
	}
	
	@StartTask @EndTask(transition="ack")
	public void receive(){
		
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isBroadcast() {
		return broadcast;
	}


	public void setBroadcast(boolean broadcast) {
		this.broadcast = broadcast;
	}


	@BypassInterceptors
	public Message getMessage() {
		return message;
	}

	@BypassInterceptors
	public void setMessage(Message message) {
		this.message = message;
	}


	public List<String> getRecipients() {
		return recipients;
	}
	
	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}
	
	
	public void addRecipient(String r)
	{
		recipients.add(r);
	}
	
	public void deleteRecipient(String r)
	{
		recipients.remove(r);
	}
	
}
