package org.domain.SeamAmiciDelGas.processes;

import java.util.Date;
import javax.persistence.EntityManager;
import org.domain.SeamAmiciDelGas.crud.AccountHome;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Questionario;
import org.domain.SeamAmiciDelGas.entity.Role;
import org.domain.SeamAmiciDelGas.session.Message;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.bpm.StartTask;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.log.Log;

@Name("inviaRequestReply")
public class InviaRequestReply {
		
	@Logger
	private Log log;
	
	@In(value="entityManager")
    private EntityManager em;
	
	@In(value="currentAccount" , scope=ScopeType.SESSION , required=false)
	private Account account;
	
	@In(value="dataVisita", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataVisita;
	
    @In(value="contadino", scope= ScopeType.BUSINESS_PROCESS ,required =false)
    private Cybercontadino contadino;
	
	@In(value="notifyMessageRequest",scope=ScopeType.BUSINESS_PROCESS, required=false) 
	@Out(value="notifyMessageReply", scope= ScopeType.BUSINESS_PROCESS, required=false)
	private Message message;
	
	@In(value="nomeMittente", scope= ScopeType.BUSINESS_PROCESS, required=false)
	private String nomeMittente;
	
	@In(value="compilato", scope= ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="compilato", scope= ScopeType.BUSINESS_PROCESS, required=false)
	private boolean compilato;
	
	private Date dataCorrente;
	
	@In(value="accountHome", create=true)
	private AccountHome accounthome;
	
	@In(value="newQuestionario", required=false)
	private Questionario questionario;

	@StartTask @EndTask(transition="inviaReply")
	public String riceviMessaggio(int risposta)
	{
		if(questionario == null)
		{
			this.setDriver(risposta);
		}
		else
		{
			compilato = true;
			if(questionario == null)
			{
				return null;
			}
			else
			{
				boolean ret = this.registraQuestionario();
				if(ret)
				{
					message.setContent("Ho compilato il questionario in data "+dataCorrente+" in seguito alla visita nell'azienda" +
							 " " + contadino.getNomeAzienda() +" effettuata il giorno "+dataVisita);
				}else{
					
					message.setContent("ERRORE CRITICO NEL DATABASE in data:"+dataCorrente);
					return null;
				}
			}
		}
		return "OutQuestionario";
			
	}
	
	@Transactional public boolean registraQuestionario()
    {
		dataCorrente = new Date(System.currentTimeMillis());
		float somma = ((float)(questionario.getVotoAllevamento()+questionario.getVotoIgiene()+questionario.getVotoProdotti()+questionario.getVotoProfessionalita()+questionario.getVotoStabile()))/5.0f;
		questionario.setAccount(account);
		questionario.setCybercontadino(contadino);
		questionario.setDataCompilazione(dataCorrente);
		questionario.setDataVisita(dataVisita);
		questionario.setVisionato(false);
		questionario.setVotoGlobale(somma);
		em.persist(questionario);
		return true; 
		
    }
	
	public Message getMessage() {
		return message;
	}


	public void setMessage(Message message) {
		this.message = message;
	}
	
	public void update(){
		log.info("QESTIONARIO COMPILATO"+ questionario.toString());
	}
	
	@Transactional public boolean setDriver(int i)
    {
		if(i==1)
		{
			accounthome.setAccountUsername(nomeMittente);
			Account accountUtente = accounthome.find();
			Role r= new Role();
			r.setName("driver");
			r.setAccount(accountUtente);
			em.persist(r);
			message.setContent("La tua richiesta di divenire driver e' stata accettata.");
		}
		else
		{
			message.setContent("La tua richiesta di divenire driver e' stata rifiutata.");
			
		}
		message.setDestinatario(nomeMittente);
		message.setTipo("becomeDriver");
		return true;
    }

}