package org.domain.SeamAmiciDelGas.processes;

import java.util.ArrayList;
import java.util.List;

import org.domain.SeamAmiciDelGas.session.Message;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;


@Name("notificaRichiestaRisposta")
@Scope(ScopeType.SESSION)
public class NotificaRichiestaRisposta {
	
	@Logger
	private Log log;
	
	@In private Credentials credentials;
	
	@In private FacesMessages facesMessages;
	
	private String content;
	
	private String destinatario;

	
	private boolean broadcast;
	
	@Out(value="nomeMittente",scope=ScopeType.BUSINESS_PROCESS, required=false)
	private String nomeMittente;
	
	@Out(value="notifyMessageRequest",scope=ScopeType.BUSINESS_PROCESS,required=false) 
	private Message message;
	
	@CreateProcess(definition="notificaRequestReply")
	public void notificaRequest()
	{
		log.info("Inoltrata la richiesta RequestReply");
		nomeMittente= credentials.getUsername();
		message=new Message();
		content = "Tutto bien?! Posso diventare Driver?";
		message.setContent(content);
		//message.setDestinatario("Mediatore");
		message.setDestinatario(destinatario);
		//message.setBroadcast(true);
		message.setBroadcast(broadcast);
		facesMessages.add("La richiesta � stata inoltrata");
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

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

}
