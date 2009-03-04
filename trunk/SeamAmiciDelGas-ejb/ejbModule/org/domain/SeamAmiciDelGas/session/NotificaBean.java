package org.domain.SeamAmiciDelGas.session;

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
	
	private Account utenteScelto;
	
	private String messaggio;
	
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

	public Account getUtenteScelto() {
		return utenteScelto;
	}

	public void setUtenteScelto(Account utenteScelto) {
		this.utenteScelto = utenteScelto;
	}
	
	
	
}
