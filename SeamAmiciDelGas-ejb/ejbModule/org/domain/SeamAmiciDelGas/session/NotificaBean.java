package org.domain.SeamAmiciDelGas.session;

import java.util.Set;

import org.domain.SeamAmiciDelGas.entity.Account;
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
	
	private String messaggio;
	
	private Set<Account> accountSet;
	
	private Account accountSelected;

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public NotificaBean() {}

	public String getGruppoScelto() {
		return gruppoScelto;
	}

	public void setGruppoScelto(String gruppoScelto) {
		this.gruppoScelto = gruppoScelto;
	}

	public Set<Account> getAccountSet() {
		return accountSet;
	}

	public void setAccountSet(Set<Account> accountSet) {
		this.accountSet = accountSet;
	}

	public Account getAccountSelected() {
		return accountSelected;
	}

	public void setAccountSelected(Account accountSelected) {
		log.info("*******************************");
		log.info("*********Account selezionato***********");
		log.info("*******************************");
		this.accountSelected = accountSelected;
	}
	
	
	
}
