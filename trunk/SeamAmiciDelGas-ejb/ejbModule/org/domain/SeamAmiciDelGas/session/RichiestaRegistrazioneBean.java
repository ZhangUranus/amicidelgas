package org.domain.SeamAmiciDelGas.session;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;
import org.jboss.seam.international.StatusMessages;
import org.hibernate.validator.Length;

@Stateful
@Name("richiestaRegistrazione")
public class RichiestaRegistrazioneBean implements RichiestaRegistrazione
{
    @Logger private Log log;

    @In StatusMessages statusMessages;
    @In(create = true)
    private UtenteHome utenteHome;
    @In(create = true)
    private AccountHome accountHome;
    
    public void richiestaRegistrazione()
    {
    	utenteHome.persist();
    	accountHome.getInstance().setUtente(utenteHome.getInstance());
    	accountHome.persist();
    	
        
    	// implement your business logic here
        //log.info("RichiestaRegistrazione.richiestaRegistrazione() action called with: #{richiestaRegistrazione.utenteHome}");
        //statusMessages.add("richiestaRegistrazione #{richiestaRegistrazione.utenteHome}");
    }
    
   
    // add additional action methods

  

    @Destroy @Remove
    public void destroy() {}


	public UtenteHome getUtenteHome() {
		return utenteHome;
	}


	public void setUtenteHome(UtenteHome utenteHome) {
		this.utenteHome = utenteHome;
	}


	public AccountHome getAccountHome() {
		return accountHome;
	}


	public void setAccountHome(AccountHome accountHome) {
		this.accountHome = accountHome;
	}
	
	public void dummyAction(){
		
	}
}
