package org.domain.SeamAmiciDelGas.session;
import java.util.Date;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.domain.SeamAmiciDelGas.crud.ComuneList;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.CodiceContadino;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Pagamentoelettronico;
import org.domain.SeamAmiciDelGas.entity.Role;
import org.domain.SeamAmiciDelGas.processes.ProcessoRegistrazione;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessages;

@Stateful
@Name("registrazioneCybercontadino")
@Scope(value=ScopeType.SESSION)
public class RegistrazioneCybercontadinoBean implements RegistrazioneCybercontadino
{
    @PersistenceContext
    private EntityManager em;
    @In(value="newCybercontadino" , create=true)
    @Out(value="contadinoCreato", scope= ScopeType.BUSINESS_PROCESS)
    private Cybercontadino contadino;
    @In(value="newAccount" , create=true)
    private Account account;
    @In(value="passwordBean" , create=true)
    private PasswordBean passwordBean;
    @In(value="newComuneProvinciaBean" , create=true)
    private ComuneProvinciaBean comuneProvinciaBean;
    @In(value="passwordManager",create=true)
    private PasswordManager passwordManager;
    @In(value="newFileUpload", create=true)
    private FileUpload fileUpload;
    @In(value="newCodiceContadino", required=false)
    private CodiceContadino codiceContadino;
    @In(value="newPagamento", create=true)
    private Pagamentoelettronico pagamento;
    @In(value="newControlloBean", create=true)
    private ControlloBean controllo;
    @In(value="processoRegistrazione",create=true)
    private ProcessoRegistrazione processo;

    
    
    @In StatusMessages statusMessages;

    @Transactional public boolean registraCybercontadino()
    {  
    	
        if (!passwordBean.verify()) {
    		FacesMessages.instance()
    		.addToControl("confirm", "value does not match password");
    	} 	
    	ComuneList comuneList = new ComuneList();
    	comuneList.setEjbql("select comune from Comune comune where comune.idcomune= "+ comuneProvinciaBean.getComune().getId()); 	
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
    	codiceContadino = new CodiceContadino(); 
    	codiceContadino.setDescrizione(contadino.getNomeAzienda());
    	em.persist(codiceContadino);
    	contadino.setCodiceContadino(codiceContadino);
    	contadino.setAccount(account);
    	em.persist(contadino);
    	Role role = new Role();
    	role.setAccount(account);
    	role.setName("utenteContadino");
    	em.persist(role);
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
    		return null;
    	else
    	{
    		fileUpload.setErrore("Aggiungere la documentazione ASL");
    		return null;
    	}
    }
    
    @Destroy @Remove
    public void destroy() {}

	public Cybercontadino getContadino() {
		return contadino;
	}

	public void setContadino(Cybercontadino contadino) {
		this.contadino = contadino;
	}

}
