package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Itinerario;
import org.domain.SeamAmiciDelGas.processes.NotifyBean;
import org.hibernate.validator.Future;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.ScopeType;

@Name("notificaBean")
@Scope(ScopeType.SESSION)
public class NotificaBean {

	@Logger
	private Log log;
	
	private String destinatario;
	private String gruppoScelto;
	private Account utenteScelto;
	private Account utenteCancellato;
	private String messaggio;
	
	@In(value="notifyBean", create=true)
	private NotifyBean notifyBean;
	
	private List<Account> myAccountList = new ArrayList<Account>();
	
	public NotificaBean() 	{}
	
	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	
	public void sendMessagge()
	{
		if(destinatario.equals("gruppo"))
			sendMessageForGroup();
		else
			sendMessageForUtente();
		myAccountList = new ArrayList<Account>();
		messaggio="";
	}
	
	public void sendMessageForGroup()
	{
		notifyBean.setContent(messaggio);
		notifyBean.addRecipient(gruppoScelto);
		notifyBean.setBroadcast(true);	
		notifyBean.notifyMessage();
	}
	
	public void sendMessageForUtente()
	{
		notifyBean.setContent(messaggio);
		for(Account a : myAccountList)
			notifyBean.addRecipient(a.getUsername());
		notifyBean.setBroadcast(false);
		notifyBean.notifyMessage();
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getGruppoScelto() {
		return gruppoScelto;
	}

	public void setGruppoScelto(String gruppoScelto) {
		this.gruppoScelto = gruppoScelto;
	}

	public List<Account> getMyAccountList() {
		return myAccountList;
	}

	public void setMyAccountList(List<Account> myAccountList) {
		this.myAccountList = myAccountList;
	}
	
	public void receive()
	{
		notifyBean.receive();
	}

	public Account getUtenteScelto() {
		return utenteScelto;
	}

	public void setUtenteScelto(Account utenteScelto) {
		this.utenteScelto = utenteScelto;
		for(Account a : myAccountList)
			if(a.getUsername().equals(utenteScelto.getUsername()))
				return;
		myAccountList.add(utenteScelto);
	}

	public Account getUtenteCancellato() {
		return utenteCancellato;
	}

	public void setUtenteCancellato(Account utenteCancellato) {
		this.utenteCancellato = utenteCancellato;
		for(Account a : myAccountList)
			if(a.getUsername().equals(utenteCancellato.getUsername()))
			{	
				myAccountList.remove(a);	
				return;
			}
	}	
	
}
