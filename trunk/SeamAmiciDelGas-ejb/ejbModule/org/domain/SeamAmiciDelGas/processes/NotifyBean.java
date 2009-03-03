package org.domain.SeamAmiciDelGas.processes;

import java.util.ArrayList;
import java.util.List;

import org.domain.SeamAmiciDelGas.session.Message;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.bpm.BeginTask;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.annotations.intercept.BypassInterceptors;


@Name("notifyBean")
@Scope(ScopeType.CONVERSATION)
public class NotifyBean {

	private String content;
	private String recipient;
	private String recipient1;
	private String recipient2;
	
	private boolean broadcast;
	@Out(value="notifyMessage",scope=ScopeType.BUSINESS_PROCESS,required=false) 
	private Message message;
	
	@CreateProcess(definition="notificatore")
	public String notifyMessage(){
		List<String> recipients= new ArrayList<String>();
		recipients.add(recipient1);
		recipients.add(recipient2);
		message=new Message();
		message.setContent(content);
		message.setRecipients(recipients);
		message.setBroadcast(broadcast);
		return "partito";
	}
	
	
	@BeginTask @EndTask(transition="ack")
	public void receive(){
		
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}


	public boolean isBroadcast() {
		return broadcast;
	}


	public void setBroadcast(boolean broadcast) {
		this.broadcast = broadcast;
	}


	public String getRecipient1() {
		return recipient1;
	}


	public void setRecipient1(String recipient1) {
		this.recipient1 = recipient1;
	}


	public String getRecipient2() {
		return recipient2;
	}


	public void setRecipient2(String recipient2) {
		this.recipient2 = recipient2;
	}

	@BypassInterceptors
	public Message getMessage() {
		return message;
	}

	@BypassInterceptors
	public void setMessage(Message message) {
		this.message = message;
	}
	
}
