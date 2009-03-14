package org.domain.SeamAmiciDelGas.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable{

	private static final long serialVersionUID = 8305655916287583818L;
	private String content;
	private String mittente;
	private boolean broadcast;
	private List<String> recipients = new ArrayList<String>();
	private String destinatario;
	private String infoFilter;
	private String tipo;
	
	
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
	public List<String> getRecipients() {
		return recipients;
	}
	
	public String getRecipient()
	{
		return recipients.get(0);
	}
	
	public void addRecipient(String r)
	{
		recipients.add(r);
	}
	
	public void deleteRecipient(String r)
	{
		recipients.remove(r);
	}
	
	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getInfoFilter() {
		return infoFilter;
	}
	public void setInfoFilter(String infoFilter) {
		this.infoFilter = infoFilter;
	}
	public String getMittente() {
		return mittente;
	}
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipo() {
		return tipo;
	}
}

