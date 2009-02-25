package org.domain.SeamAmiciDelGas.session;

import java.util.GregorianCalendar;
import java.util.List;

import org.domain.SeamAmiciDelGas.crud.AccountHome;
import org.domain.SeamAmiciDelGas.crud.AccountList;
import org.domain.SeamAmiciDelGas.crud.CybercontadinoList;
import org.domain.SeamAmiciDelGas.crud.RoleList;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

@Name(value="richiesteRegistrazioniBean")
@Scope(ScopeType.SESSION)
public class RichiesteRegistrazioniBean {

	@Logger
	private Log log;
	
	@In(value="accountList",create=true)
	private AccountList accountList;
	
	@In(value="roleList",create=true)
	private RoleList roleList;
	
	@In(value="accountHome", create=true)
	private AccountHome accountHome;
	
	/*
	@In(value="cybercontadinolist", create=true)
	private CybercontadinoList cybercontadinoList;
	*/
	private Account currentCustomer;
	
	private Cybercontadino currentContadino;
	
	private List<Account> richiesteCustomer;
	
	private List<Cybercontadino> richiesteCyberContadini;
	
	private boolean updeted = false;

	public boolean isUpdeted() {
		return updeted;
	}

	public void setRichiesteCyberContadini(
			List<Cybercontadino> richiesteCyberContadini) {
		this.richiesteCyberContadini = richiesteCyberContadini;
	}

	public void setUpdeted(boolean updeted) {
		this.updeted = updeted;
	}

	//seleziono gli utenti customer che sono nello stato attivato 0 bloccato 0 eliminato 0
	public List<Account> getRichiesteCustomer() {
		accountList.setEjbql("select account from Role role where role.account.username not in (select role1.account from Role role1 where role1.name='admin' or role1.name='mediatore' or role1.name='utenteContadino') and role.account.attivato='false' and role.account.bloccato='false' and role.account.elimato='false'");
		richiesteCustomer = accountList.getResultList();
		return richiesteCustomer;
	}
	
	//seleziono i cybercontadini che sono nello stato attivato 0 bloccato 0 eliminato 0
	/*
	public List<Cybercontadino> getRichiesteCyberContadini() {
		cybercontadinoList.setEjbql("select cyber from cybercontadino cyber where cyber.account.attivato='false' and cyber.account.bloccato='false' and cyber.account.elimato='false'");
		richiesteCyberContadini = cybercontadinoList.getResultList();
		return richiesteCyberContadini;
	}
	*/
	
	public void reset() {
		accountHome.setAccountUsername("emanuele");
		Account a = accountHome.find();
		if (a==null) {
			log.info("********E' NULL**********");
			this.setUpdeted(false);
		}
		log.info("*******"+a.getUsername()+"*********");
		a.setAttivato(false);
		a.setBloccato(false);
		a.setElimato(false);
		GregorianCalendar gc = new GregorianCalendar();
		a.setDataAccettazione(gc.getTime());
		accountHome.update();
		this.setUpdeted(true);
	}
	
	//modifico lo stato dell'account attivato 1 bloccato 0 eliminato 0
	public void accettaRegistrazioneCustomer() {
		try {
			accountHome.setAccountUsername(currentCustomer.getUsername());
			Account a = accountHome.find();
			if (a==null) {
				this.setUpdeted(false);
				return;
			}			
			a.setAttivato(true);
			a.setBloccato(false);
			a.setElimato(false);
			GregorianCalendar gc = new GregorianCalendar();
			a.setDataAccettazione(gc.getTime());
			accountHome.update();
			this.setUpdeted(true);
		}
		catch (Exception e) {
			log.info(e.toString());
			this.setUpdeted(false);
		}
	}
	
	//modifico lo stato dell'account attivato 0 bloccato 0 eliminato 1
	public void rifiutaRegistrazioneCustomer() {
		try {
			accountHome.setAccountUsername(currentCustomer.getUsername());
			Account a = accountHome.find();
			if (a==null) {
				this.setUpdeted(false);
				return;
			}
			a.setAttivato(false);
			a.setBloccato(false);
			a.setElimato(true);
			accountHome.update();
			this.setUpdeted(true);
	}
	catch (Exception e) {
		log.info(e.toString());
		this.setUpdeted(false);
	}
	}

	public void setRichiesteCustomer(List<Account> richiesteCustomer) {
		this.richiesteCustomer = richiesteCustomer;
	}
	
	public Account getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(Account currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

	
}
