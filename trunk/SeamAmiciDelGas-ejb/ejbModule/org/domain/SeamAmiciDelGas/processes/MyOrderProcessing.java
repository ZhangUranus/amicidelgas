package org.domain.SeamAmiciDelGas.processes;

import java.util.ArrayList;
import java.util.List;

import org.domain.SeamAmiciDelGas.session.Message;
import org.domain.SeamAmiciDelGas.session.MyOrdine;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.bpm.BeginTask;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.security.Credentials;


@Name("myOrderProcessing")
@Scope(ScopeType.CONVERSATION)
public class MyOrderProcessing {

	@Out(value="myOrdine",scope=ScopeType.BUSINESS_PROCESS,required=false)
	private MyOrdine myOrdine;
	
	
	@CreateProcess(definition="myOrderProcessing")
	public String notifyMessage(){
		
		sender = credentials.getUsername();
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

	public MyOrdine getMyOrdine() {
		return myOrdine;
	}

	public void setMyOrdine(MyOrdine myOrdine) {
		this.myOrdine = myOrdine;
	}
	
}
