package org.domain.SeamAmiciDelGas.processes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import org.domain.SeamAmiciDelGas.crud.AccountHome;
import org.domain.SeamAmiciDelGas.crud.FeedbackListExtended;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Feedback;
import org.domain.SeamAmiciDelGas.entity.Questionario;
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
	
	@In(value="currentAccount" , scope=ScopeType.SESSION , required=false)
	private Account account;
	
	@In(value="dataVisita", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Date dataVisita;
	
    @In(value="contadino", scope= ScopeType.BUSINESS_PROCESS ,required =false)
    private Cybercontadino contadino;
    
    @In(value="nomeDestinatario", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private String nomeUtente;
	
	@In(value="compilato", scope= ScopeType.BUSINESS_PROCESS, required=false)
	private Boolean compilato;
	
	private Date dataCorrente;
	
	@In(value="accountHome", create=true)
	private AccountHome accounthome;

	@In(value="newFeedback" , create=true)
	private Feedback feedback;
	
	@In(value="newFeedbackListExtended",create=true)
	private FeedbackListExtended feedbackList;
	
	
	
	
	
	@StartTask @EndTask(beforeRedirect=true , transition="fine")
	public String riceviRisposta()
	{
		System.out.println("OKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
		
		if(compilato)
			this.inserisciFeedback(4.0f);
		else
			this.inserisciFeedback(2.0f);
		return "OutRiepilogoQuestionari";
		
		
	}
	
	@Transactional public boolean inserisciFeedback(float p)
    {
		dataCorrente = new Date(System.currentTimeMillis());
		accounthome.setAccountUsername(nomeUtente);
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
		float punteggioCorrente = this.calcolaPunteggioFeedback(p, accountUtente);
		//accountUtente.setPunteggioFeedback(punteggioCorrente);
		System.out.println("FIGARO");
		accountUtente.setPunteggioFeedback(punteggioCorrente);
		accounthome.update();
		em.persist(feedback);
		
		return true; 
		
    }
	
	private float calcolaPunteggioFeedback(float punteggioNuovo, Account accountUtente)
	{
		feedbackList.getFeedback().setAccountByDestinatario(accountUtente);
		List<Feedback> listaFeedback = feedbackList.getResultList();
		ArrayList<Float> listaFloat = new ArrayList<Float>();
		for (Feedback feedback : listaFeedback) {
			listaFloat.add(feedback.getPunteggio());
		}
		int size = listaFloat.size();
		float somma = 0.0f;
		for (Float float1 : listaFloat) {
			somma += float1.floatValue();
		}
		somma += punteggioNuovo;
		//situazione iniziale, nessun feedback punteggio di partenza 3
		if(size==0){
			somma+=3;
			size=1;
		}			
		return somma/(size+1);
	}
}