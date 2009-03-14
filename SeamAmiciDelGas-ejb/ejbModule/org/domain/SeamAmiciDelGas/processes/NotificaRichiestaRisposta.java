package org.domain.SeamAmiciDelGas.processes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.domain.SeamAmiciDelGas.session.Message;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.annotations.bpm.StartTask;
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
	
	private String tipo;
	
	private String mittente;
	
	@Out(value="nomeMittente",scope=ScopeType.BUSINESS_PROCESS, required=false)
	private String nomeMittente;
	
	@Out(value="nomeDestinatario",scope=ScopeType.BUSINESS_PROCESS, required=false)
	private String nomeDestinatario;
	
	@Out(value="dataTimer",scope=ScopeType.BUSINESS_PROCESS, required=false)
	private Date dataTimer;
	
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
		System.out.println("La richiesta � stata inoltrata");
		facesMessages.add("La richiesta � stata inoltrata");
	}
/*	
	@StartTask @EndTask(transition="inviaRequest")
	public void creaMessaggio()
	{
		System.out.println("Creo Messaggio");
		message=new Message();
		content = "Tutto bien?! Posso diventare Driver?";
		message.setContent(content);
		//message.setDestinatario("Mediatore");
		message.setDestinatario(destinatario);
		//message.setBroadcast(true);
		message.setBroadcast(broadcast);
		System.out.println("Il messaggio � stata inoltrato");
	}
	*/

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

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getMittente() {
		return mittente;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

}
