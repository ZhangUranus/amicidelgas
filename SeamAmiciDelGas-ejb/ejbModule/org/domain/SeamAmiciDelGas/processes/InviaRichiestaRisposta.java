package org.domain.SeamAmiciDelGas.processes;

import java.util.Date;
import javax.persistence.EntityManager;
import org.domain.SeamAmiciDelGas.crud.AccountHome;
import org.domain.SeamAmiciDelGas.crud.FeedbackListExtended;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Feedback;
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

@Name("inviaRichiestaRisposta")
public class InviaRichiestaRisposta {
		
	@Logger
	private Log log;
	
	@In(value="entityManager")
    private EntityManager em;
	
	@In(value="notifyMessageReply",scope=ScopeType.BUSINESS_PROCESS, required=false) 
	private Message message;
	
	@In(value="currentAccount" , scope=ScopeType.SESSION , required=false)
	private Account account;
	
	@In(value="dataVisita", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataVisita;
	
    @In(value="contadino", scope= ScopeType.BUSINESS_PROCESS ,required =false)
    private Cybercontadino contadino;
    
    @In(value="nomeDestinatario", scope= ScopeType.BUSINESS_PROCESS, required =false)
    @Out(value="nomeDestinatario",scope=ScopeType.BUSINESS_PROCESS, required=false)
	private String nomeDestinatario;
	
	@In(value="compilato", scope= ScopeType.BUSINESS_PROCESS, required=false)
	private Boolean compilato;
	
	@In(value="accountHome", create=true)
	private AccountHome accounthome;

	@In(value="newFeedback" , create=true)
	private Feedback feedback;
	
	@In(value="newFeedbackListExtended",create=true)
	private FeedbackListExtended feedbackList;
	
	private Date dataCorrente;

	@StartTask @EndTask(beforeRedirect=true , transition="fine")
	public String riceviRisposta()
	{
		System.out.println("OKKKK");
		if(message != null && message.getTipo().equals("becomeDriver"))
		{
			

		}
		else
		{
			if(compilato)
				this.inserisciFeedback(4.0f);
			else
				this.inserisciFeedback(2.0f);
		}
		return "OutRiepilogoQuestionari";
		
		
	}
	
	@Transactional public boolean inserisciFeedback(float p)
    {
		dataCorrente = new Date(System.currentTimeMillis());
		accounthome.setAccountUsername(nomeDestinatario);
		Account accountUtente = accounthome.find();
		feedback.setAccountBySegnalatore(account);
		feedback.setAccountByValidatore(account);
		feedback.setAccountByDestinatario(accountUtente);
		feedback.setDataSegnalazione(dataCorrente);
		feedback.setDataValidazione(dataCorrente);
		if(compilato)
			feedback.setDescrizione("Feedback Positivo inserito perchè l'utente " +
				"ha compilato il questionario in tempo per l'azienda "+contadino.getNomeAzienda()+" in seguito alla visita" +
						" in data "+dataVisita);
		else
			feedback.setDescrizione("Feedback Negativo inserito perchè l'utente non " +
					"ha compilato il questionario in tempo per l'azienda "+contadino.getNomeAzienda()+" in seguito alla visita" +
							" in data "+dataVisita);
		feedback.setAnalizzato(true);
		feedback.setPunteggio(p);
		float punteggioCorrente = accountUtente.getPunteggioFeedback();
		int numvotanti = accountUtente.getNumeroVotanti()+1;
		punteggioCorrente = ((punteggioCorrente * (numvotanti-1))+p)/((float) numvotanti);
		accountUtente.setPunteggioFeedback(punteggioCorrente);
		accountUtente.setNumeroVotanti(numvotanti);
		accounthome.update();
		em.persist(feedback);
		
		return true; 
		
    }
}