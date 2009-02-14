package org.domain.SeamAmiciDelGas.session;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;
import org.jboss.seam.international.StatusMessages;
import org.domain.SeamAmiciDelGas.crud.AccountHome;
import org.domain.SeamAmiciDelGas.crud.ComuneHome;
import org.domain.SeamAmiciDelGas.crud.ProvincesHome;
import org.domain.SeamAmiciDelGas.crud.UtenteHome;
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
    @In(create = true)
    private ProvincesHome provincesHome;
    @In(create = true)
    private ComuneHome comuneHome;
    
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

	
	public void dummyAction(){
		
	}


	public AccountHome getAccountHome() {
		// TODO Auto-generated method stub
		return null;
	}


	public ComuneHome getComuneHome() {
		// TODO Auto-generated method stub
		return null;
	}


	public ProvincesHome getProvincesHome() {
		// TODO Auto-generated method stub
		return null;
	}


	public UtenteHome getUtenteHome() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setAccountHome(AccountHome accountHome) {
		// TODO Auto-generated method stub
		
	}


	public void setComuneHome(ComuneHome comuneHome) {
		// TODO Auto-generated method stub
		
	}


	public void setProvincesHome(ProvincesHome provincesHome) {
		// TODO Auto-generated method stub
		
	}


	public void setUtenteHome(UtenteHome utenteHome) {
		// TODO Auto-generated method stub
		
	}

}
