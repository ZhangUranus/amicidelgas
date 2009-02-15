package org.domain.SeamAmiciDelGas.session;

import java.util.Date;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.domain.SeamAmiciDelGas.crud.ComuneList;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Comune;
import org.domain.SeamAmiciDelGas.entity.Pagamentoelettronico;
import org.domain.SeamAmiciDelGas.entity.Patente;
import org.domain.SeamAmiciDelGas.entity.Role;
import org.domain.SeamAmiciDelGas.entity.Utente;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.ScopeType;
import org.jboss.seam.international.StatusMessages;

@Stateful
@Scope(value=ScopeType.SESSION)
@Name("richiestaRegistrazione")
public class RichiestaRegistrazioneBean implements RichiestaRegistrazione
{
    @Logger private Log log;

    @PersistenceContext
    private EntityManager em;
    @In(value="newUtente")
    private Utente utente;
    @In(value="newAccount")
    private Account account;
    @In(value="newPatente")
    private Patente patente;
    @In(value="newPagamento")
    private Pagamentoelettronico pagamento;
    @In(value="newComuneNascita")
    private Comune comuneNascita;
    @In(value="newComuneResidenza")
    private Comune comuneResidenza;
    
    @In StatusMessages statusMessages;

    
    public void richiestaRegistrazione()
    {
    	ComuneList comuneList = new ComuneList();
    	comuneList.setEjbql("select comune from Comune comune where comune.idcomune= 10252");
//    	comuneList.getComune().setIdcomune(comuneNascita.getIdcomune());
//    	comuneList.refresh();
//    	log.error("prova id comune: "+comuneNascita.getIdcomune());
    	log.error("prova query: "+comuneList.getEjbql());
    	
    	utente.setComuneByComuneNascita(comuneList.getResultList().get(0));
    	
    	comuneList = new ComuneList();
    	comuneList.setEjbql("select comune from Comune comune where comune.idcomune= 10252");
    	
    	utente.setComuneByIdcomune(comuneList.getResultList().get(0));
    	
    	em.persist(utente);
    	patente.setUtente(utente);
    	em.persist(patente);
    	
    	em.persist(pagamento);
    	
    	account.setUtente(utente);
    	account.setPagamentoelettronico(pagamento);
    	account.setAttivato(false);
    	account.setBloccato(false);
    	account.setCancellato(false);
    	account.setElimato(false);
    	Date date = new Date();
    	date.setTime(System.currentTimeMillis());
    	account.setDataRichiesta(date);
    	account.setPunteggioFeedback(3);
    	em.persist(account);
    	
    	Role role = new Role();
    	role.setAccount(account);
    	role.setName("prova");
    	em.persist(role);
    	
        // implement your business logic here
        log.info("richiestaRegistrazione.richiestaRegistrazione() action called");
        statusMessages.add("richiestaRegistrazione");
    }

    // add additional action methods

    
    
    @Destroy @Remove
    public void destroy() {}

}
