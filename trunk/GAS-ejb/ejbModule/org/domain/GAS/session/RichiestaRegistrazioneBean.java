package org.domain.GAS.session;

import java.util.Date;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.domain.GAS.crud.ComuneList;
import org.domain.GAS.entity.Account;
import org.domain.GAS.entity.Pagamentoelettronico;
import org.domain.GAS.entity.Patente;
import org.domain.GAS.entity.Role;
import org.domain.GAS.entity.Utente;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Log;
import org.jboss.seam.ScopeType;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessages;

@Stateful
@Scope(value=ScopeType.CONVERSATION)
@Name("richiestaRegistrazione")
public class RichiestaRegistrazioneBean implements RichiestaRegistrazione
{
    @Logger private Log log;

    @PersistenceContext
    private EntityManager em;
    @In(value="newUtente" , create=true)
    @Out(value="utenteCreato")
    private Utente utente;
    @In(value="newAccount" , create=true)
    private Account account;
    @In(value="newPatente", create=true)
    private Patente patente;
    @In(value="newPagamento", create=true)
    private Pagamentoelettronico pagamento;
    @In(value="newComuneProvinciaBean", create=true)
    private ComuneProvinciaBean comuneProvinciaBean;
    @In(value="newComuneProvinciaResidenzaBean", create=true)
    private ComuneProvinciaBean comuneProvinciaResidenzaBean;
    @In(value="passwordBean", create=true)
    private PasswordBean passwordBean;
    @In(value="passwordManager",create=true)
    private PasswordManager passwordManager;

    @In StatusMessages statusMessages;
    
    @Transactional public boolean richiestaRegistrazione()
    {
    	
    	if (!passwordBean.verify()) {
    		FacesMessages.instance()
    		.addToControl("confirm", "value does not match password");
    	}
    	
    	
    	
    	ComuneList comuneList = new ComuneList();
    	comuneList.setEjbql("select comune from Comune comune where comune.idcomune= "+ comuneProvinciaBean.getComune().getId());
    	
    	account.setPasswordHash(passwordManager.hash(passwordBean.getPassword()));
    	utente.setComuneByComuneNascita(comuneList.getResultList().get(0));
    	
    	comuneList = new ComuneList();
    	comuneList.setEjbql("select comune from Comune comune where comune.idcomune= "+comuneProvinciaResidenzaBean.getComune().getId());
    	
    	utente.setComuneByIdcomune(comuneList.getResultList().get(0));
    	String indirizzo = utente.getIndirizzo();
		indirizzo = indirizzo.toLowerCase();
		if(indirizzo.startsWith("via ") || indirizzo.startsWith("corso ") || indirizzo.startsWith("viale ") || indirizzo.startsWith("piazza ") || indirizzo.startsWith("contrada ") || indirizzo.startsWith("frazione ") || indirizzo.startsWith("strada ") || indirizzo.startsWith("piazzale ") || indirizzo.startsWith("rotonda ") || indirizzo.startsWith("rotonde "))
    		utente.setIndirizzo(indirizzo);
    	else
    		utente.setIndirizzo("via " + indirizzo);
    	em.persist(utente);
    	if(!(patente.getTipo().equals("NO")))
    	{
    		patente.setUtente(utente);
    		em.persist(patente);
    	}
    	
    	em.persist(pagamento);
    	
    	account.setUtente(utente);
    	account.setPagamentoelettronico(pagamento);
    	account.setAttivato(false);
    	account.setBloccato(false);
    	account.setCancellato(false);
    	account.setEliminato(false);
    	Date date = new Date();
    	date.setTime(System.currentTimeMillis());
    	account.setDataRichiesta(date);
    	account.setPunteggioFeedback((float) 3.0);
    	em.persist(account);
    	
    	Role role = new Role();
    	role.setAccount(account);
    	role.setName("utenteGas");
    	em.persist(role);
    	
        // implement your business logic here
        statusMessages.add("Avvenuta Registrazione");
        
        return true;
    }


    // add additional action methods

    @Destroy @Remove
    public void destroy() {}

}
