package org.domain.SeamAmiciDelGas.crud;

import org.domain.SeamAmiciDelGas.entity.*;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;

import org.jboss.mq.pm.jdbc2.PersistenceManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;
import org.jboss.seam.persistence.EntityManagerFactory;

@Name("utenteHome")
@Scope(ScopeType.SESSION)
public class UtenteHome extends EntityHome<Utente> {
	
	@Logger private Log log;
	@In(create = true, value = "comuneHome")
	ComuneHome comuneHomeByComuneNascita;
	@In(create = true, value = "comuneHome")
	ComuneHome comuneHomeByIdcomune;
//	@In
//    private EntityManager entityManager;
//	
//	@In(value="currentUser")
//    private Utente utente;
//    
//	public Utente getUtente() {
//		return utente;
//	}
//
//
//	public void setUtente(Utente utente) {
//		this.utente = utente;
//	}

	public void setUtenteCodiceFiscale(String id) {
		setId(id);
	}

	public String getUtenteCodiceFiscale() {
		return (String) getId();
	}

	@Override
	protected Utente createInstance() {
		Utente utente = new Utente();
		return utente;
	}

	public void wire() {
		getInstance();
		Comune comuneByComuneNascita = comuneHomeByComuneNascita
				.getDefinedInstance();
		if (comuneByComuneNascita != null) {
			getInstance().setComuneByComuneNascita(comuneByComuneNascita);
		}
		Comune comuneByIdcomune = comuneHomeByIdcomune.getDefinedInstance();
		if (comuneByIdcomune != null) {
			getInstance().setComuneByIdcomune(comuneByIdcomune);
		}
	}

	public boolean isWired() {
		if (getInstance().getComuneByComuneNascita() == null)
			return false;
		if (getInstance().getComuneByIdcomune() == null)
			return false;
		return true;
	}
	
//	public void updateMePlease(){
//		log.info("update me...");
//		log.info("> "+utente.getCognome()+" > "+utente.getRecapitoTelefonico());
//		//update();
//		
//		EntityManager em = getEntityManager();
//
//		try{
//		    em.getTransaction().begin();
//		    Utente user = em.find(Utente.class, utente.getCodiceFiscale());
//		    user.setEmail(utente.getEmail()); 
//		    user.setIndirizzo(utente.getIndirizzo());
//		    user.setRecapitoTelefonico(utente.getRecapitoTelefonico());
//		    em.getTransaction().commit();
//		  } finally {
//			 if (em.getTransaction().isActive()) em.getTransaction().rollback();
//		    
//		  }
//	}
	public Utente getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Account> getAccounts() {
		return getInstance() == null ? null : new ArrayList<Account>(
				getInstance().getAccounts());
	}

	public List<Patente> getPatentes() {
		return getInstance() == null ? null : new ArrayList<Patente>(
				getInstance().getPatentes());
	}


}
