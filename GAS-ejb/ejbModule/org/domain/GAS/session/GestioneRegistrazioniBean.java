package org.domain.GAS.session;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.domain.GAS.crud.AccountHome;
import org.domain.GAS.crud.AccountList;
import org.domain.GAS.crud.UtenteList;
import org.domain.GAS.entity.Account;
import org.domain.GAS.entity.Cybercontadino;
import org.domain.GAS.processes.RegistrationConfirmation;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Log;

@Name(value="gestioneRegistrazioniBean")
@Scope(ScopeType.PAGE)
public class GestioneRegistrazioniBean {

	@Logger
	private Log log;
	
	@In(value="accountList",create=true)
	private AccountList accountList;
	
	@In(value="accountHome", create=true)
	private AccountHome accountHome;
	
    @In(value="registrationMailer",create=true)
    private RegistrationMailer registrationMailer;
	
	@In(value="utenteList", create=true)
	private UtenteList utenteList;
	
	private Date dataIntervista;
	
	private Cybercontadino currentContadino;
	
	private Account currentCustomer;
	
	private List<Account> richiesteCustomer;
	
	
	@In(value="registrationConfirmation",create=true)
    private RegistrationConfirmation  confirm;
	
	private boolean updeted = false;

	public boolean isUpdeted() {
		return updeted;
	}


	public void setUpdeted(boolean updeted) {
		this.updeted = updeted;
	}

	//seleziono gli utenti customer che sono nello stato attivato 0 bloccato 0 eliminato 0
	public List<Account> getRichiesteCustomer() {
		//accountList.setEjbql("select account from Role role where role.account.username not in (select role1.account from Role role1 where role1.name='admin' or role1.name='mediatore' or role1.name='utenteContadino') and role.account.attivato='false' and role.account.bloccato='false' and role.account.eliminato='false'");
		accountList.setEjbql("select account from Account account where account.attivato=false and account.utente != null and account.eliminato=false");
		richiesteCustomer = accountList.getResultList();
		return richiesteCustomer;
	}
	
	//modifico lo stato dell'account attivato 1 bloccato 0 eliminato 0
	public void accettaRegistrazioneCustomer() 
	{
		if(this.accetta())
		{
			confirm.initiateConfirmation(currentCustomer);
			this.updeted = true;
		}
		
	}
	
	@Transactional public boolean rifiuta()
    {  
		accountHome.setAccountUsername(currentCustomer.getUsername());
		Account a = accountHome.find();	
		a.setAttivato(false);
		a.setBloccato(false);
		a.setEliminato(true);
		GregorianCalendar gc = new GregorianCalendar();
		a.setDataAccettazione(gc.getTime());
		accountHome.update();
		return true;
    }
	
	@Transactional public boolean accetta()
    {  
		accountHome.setAccountUsername(currentCustomer.getUsername());
		Account a = accountHome.find();	
		a.setAttivato(true);
		a.setBloccato(false);
		a.setEliminato(false);
		GregorianCalendar gc = new GregorianCalendar();
		a.setDataAccettazione(gc.getTime());
		accountHome.update();
		return true;
    }
	
	public void inviaEmail()
	{
		//registrationMailer.sendWelcomeEmail(dataIntervista);
	}
	
	//modifico lo stato dell'account attivato 0 bloccato 0 eliminato 1
	public void rifiutaRegistrazioneCustomer() 
	{
		this.rifiuta();
		this.updeted = true;
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

	public Date getDataIntervista() {
		return dataIntervista;
	}


	public void setDataIntervista(Date dataIntervista) {
		this.dataIntervista = dataIntervista;
	}


	public void setCurrentContadino(Cybercontadino currentContadino) {
		this.currentContadino = currentContadino;
	}


	public Cybercontadino getCurrentContadino() {
		return currentContadino;
	}




	
}
