package org.domain.SeamAmiciDeGas.customers;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Pagamentoelettronico;
import org.domain.SeamAmiciDelGas.entity.Patente;
import org.domain.SeamAmiciDelGas.entity.Utente;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Log;
import org.jboss.seam.ScopeType;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.international.StatusMessages;

@Stateful
@Scope(value=ScopeType.SESSION)
@Name("actionCustomer")
public class CustomersEditBean implements CustomersEdit 
{
    @Logger private Log log;
    
    @PersistenceContext
    private EntityManager em;
    @In(value="currentUser")
    private Utente utente;
    
	public Utente getUtente() {
		return utente;
		
	}

	public boolean updateContatti(){
		

		  try{
		    em.getTransaction().begin();
		    Utente user = em.find(Utente.class, utente.getCodiceFiscale());
		    user.setEmail(utente.getEmail()); 
		    user.setIndirizzo(utente.getIndirizzo());
		    user.setRecapitoTelefonico(utente.getRecapitoTelefonico());
		    em.getTransaction().commit();
		  } finally {
		    em.close();
		    return false;
		  }

	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}


    // add additional action methods

    @Destroy @Remove
    public void destroy() {}

}
