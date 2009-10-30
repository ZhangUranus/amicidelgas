package org.domain.GAS.processes;

import java.util.Date;

import org.domain.GAS.session.Message;
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
	
	private String tipo;
	
	@Out(value="compilato", scope= ScopeType.BUSINESS_PROCESS, required=false)
	private boolean compilato;
	
	@Out(value="nomeMittente",scope=ScopeType.BUSINESS_PROCESS, required=false)
	private String nomeMittente;
	
	@In(value="nomeDestinatario",scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="nomeDestinatario",scope=ScopeType.BUSINESS_PROCESS, required=false)
	private String nomeDestinatario;
	
	@Out(value="dataTimer",scope=ScopeType.BUSINESS_PROCESS, required=false)
	private Date dataTimer;
	
	@Out(value="notifyMessageRequest",scope=ScopeType.BUSINESS_PROCESS,required=false) 
	private Message message;
	
	@CreateProcess(definition="notificaRequestReply")
	public void notificaRequest()
	{
		nomeMittente= credentials.getUsername();
		message=new Message();
		message.setContent(content);
		message.setDestinatario(destinatario);
		message.setBroadcast(broadcast);
		message.setTipo(tipo);
		compilato = true;
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

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

}
