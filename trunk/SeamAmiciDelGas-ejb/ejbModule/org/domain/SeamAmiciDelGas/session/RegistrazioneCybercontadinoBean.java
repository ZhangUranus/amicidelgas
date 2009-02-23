package org.domain.SeamAmiciDelGas.session;


import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Comune;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Log;
import org.jboss.seam.international.StatusMessages;

@Stateful
@Name("registrazioneCybercontadino")
@Scope(value=ScopeType.SESSION)
public class RegistrazioneCybercontadinoBean implements RegistrazioneCybercontadino
{
    @Logger private Log log;
    
    @PersistenceContext
    private EntityManager em;
    @In(value="newCybercontadino")
    @Out(value="contadinoCreato")
    private Cybercontadino contadino;
    @In(value="newAccount")
    private Account account;
    @In(value="passwordBean")
    private PasswordBean passwordBean;
    @In(value="passwordManager",create=true)
    private PasswordManager passwordManager;
    @In(value="newFileUpload", create=true)
    private FileUpload fileUpload;
    
    
    @In StatusMessages statusMessages;

    @Transactional public boolean registrazioneCyberContadino()
    {  
    	/*
        if (!passwordBean.verify()) {
    		FacesMessages.instance()
    		.addToControl("confirm", "value does not match password");
    	}
    	
    	ComuneList comuneList = new ComuneList();
    	comuneList.setEjbql("select comune from Comune comune where comune.idcomune= "+ comuneAutoCompleteBean.getSelectedComune().getId());
//    	comuneList.getComune().setIdcomune(comuneNascita.getIdcomune());
//    	comuneList.refresh();
//    	log.error("prova id comune: "+comuneNascita.getIdcomune());
    	log.error("prova query: "+comuneList.getEjbql());
    	
    	account.setPasswordHash(passwordManager.hash(passwordBean.getPassword()));
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
    	role.setName("utenteGas");
    	em.persist(role);
    	
    	// implement your business logic here
        log.info("RegistrazioneCyberContadino.registrazioneCyberContadino() action called");
        statusMessages.add("registrazioneCyberContadino");
       */
    	return true;
    }

    // add additional action methods
    
    @Destroy @Remove
    public void destroy() {}

}
