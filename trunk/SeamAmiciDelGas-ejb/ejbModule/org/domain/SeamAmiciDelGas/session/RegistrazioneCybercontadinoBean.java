package org.domain.SeamAmiciDelGas.session;
import java.util.Date;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.domain.SeamAmiciDelGas.action.RegistrationMailer;
import org.domain.SeamAmiciDelGas.crud.ComuneList;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Comune;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Pagamentoelettronico;
import org.domain.SeamAmiciDelGas.entity.Role;
import org.domain.SeamAmiciDelGas.processes.ProcessoRegistrazione;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Log;
import org.jboss.seam.faces.FacesMessages;
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
    @Out(value="contadinoCreato", scope= ScopeType.BUSINESS_PROCESS)
    private Cybercontadino contadino;
    @In(value="newAccount")
    private Account account;
    @In(value="passwordBean")
    private PasswordBean passwordBean;
    @In(value="newComuneProvinciaBean")
    private ComuneProvinciaBean comuneProvinciaBean;
    @In(value="passwordManager",create=true)
    private PasswordManager passwordManager;
    @In(value="newFileUpload", create=true)
    private FileUpload fileUpload;
    @In(value="newPagamento", create=true)
    private Pagamentoelettronico pagamento;
    @In(value="newControlloBean", create=true)
    private ControlloBean controllo;
    @In(value="processoRegistrazione",create=true)
    private ProcessoRegistrazione processo;
//   @In(value="registrationMailer",create=true)
//    private RegistrationMailer registrationMailer;
    
    
    @In StatusMessages statusMessages;

    @Transactional public boolean registraCybercontadino()
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
    	contadino.setComune(comuneList.getResultList().get(0));
    	contadino.setPathAsl(fileUpload.getSavePath());
    	
    	em.persist(pagamento);
    	
    	account.setPagamentoelettronico(pagamento);
    	account.setAttivato(false);
    	account.setBloccato(false);
    	account.setCancellato(false);
    	account.setElimato(false);
    	Date date = new Date();
    	date.setTime(System.currentTimeMillis());
    	account.setDataRichiesta(date);
    	account.setPunteggioFeedback((float) 3.0);
    	em.persist(account);
    	
    	contadino.setAccount(account);
    	em.persist(contadino);
    	
    	Role role = new Role();
    	role.setAccount(account);
    	role.setName("utenteContadino");
    	em.persist(role);
    	
    	
    	//registrationMailer.sendWelcomeEmail();
    	
    	
    	// implement your business logic here
    	log.info("registrazionecybercontadino.registrazionecybercontadino() action called");
        statusMessages.add("Avvenuta Registrazione Cybercontadino");
        
        return true;
    }
    
    public boolean registrazioneCybercontadino()
    {  
    	boolean retValue=this.registraCybercontadino();
    	if(retValue)
    		processo.inviaRegistrazione();	
    	return retValue;
    }
    
    public String action()
    {
    	if(fileUpload.getFile() != null && controllo.getMyResponseUserName()== null)
    	{
    		fileUpload.setErrore(null);
    		return "passo2Outcome";
    	}	
    	if(controllo.getMyResponseUserName()!= null)
    	{
    		return null;
    	}
    	else
    	{
    		fileUpload.setErrore("Aggiungere la documentazione ASL");
    		//errore="Aggiungere la documentazione ASL";
    		return null;
    	}
    	
    }

    // add additional action methods
    
    @Destroy @Remove
    public void destroy() {}

}
