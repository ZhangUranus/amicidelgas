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
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Log;
import org.jboss.seam.ScopeType;
import org.jboss.seam.faces.FacesMessages;
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
    @In(value="newComuneProvinciaBean")
    private ComuneProvinciaBean comuneProvinciaBean;
    @In(value="newComuneProvinciaResidenzaBean")
    private ComuneProvinciaBean comuneProvinciaResidenzaBean;
    @In(value="passwordBean")
    private PasswordBean passwordBean;
    @In(value="passwordManager",create=true)
    private PasswordManager passwordManager;
    @In StatusMessages statusMessages;
    
    @Transactional public void richiestaRegistrazione()
    {
    	
    	if (!passwordBean.verify()) {
    		FacesMessages.instance()
    		.addToControl("confirm", "value does not match password");
    	}
    	
    	ComuneList comuneList = new ComuneList();
    	comuneList.setEjbql("select comune from Comune comune where comune.idcomune= "+ comuneProvinciaBean.getComune().getId());
//    	comuneList.getComune().setIdcomune(comuneNascita.getIdcomune());
//    	comuneList.refresh();
//    	log.error("prova id comune: "+comuneNascita.getIdcomune());
    	log.error("prova query: "+comuneList.getEjbql());
    	
    	account.setPasswordHash(passwordManager.hash(passwordBean.getPassword()));
    	utente.setComuneByComuneNascita(comuneList.getResultList().get(0));
    	
    	comuneList = new ComuneList();
    	comuneList.setEjbql("select comune from Comune comune where comune.idcomune= "+comuneProvinciaResidenzaBean.getComune().getId());
    	
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
    	role.setName("utenteGas");
    	em.persist(role);
    	
        // implement your business logic here
        log.info("richiestaRegistrazione.richiestaRegistrazione() action called");
        statusMessages.add("richiestaRegistrazione");
    }


    // add additional action methods

    @Destroy @Remove
    public void destroy() {}

}
