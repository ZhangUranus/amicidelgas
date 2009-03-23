package org.domain.SeamAmiciDelGas.session;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.domain.SeamAmiciDelGas.crud.AccountHome;
import org.domain.SeamAmiciDelGas.crud.AccountList;
import org.domain.SeamAmiciDelGas.crud.FeedbackHome;
import org.domain.SeamAmiciDelGas.crud.FeedbackList;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Feedback;
import org.domain.SeamAmiciDelGas.entity.Ordine;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;

@Name(value="gestioneFeedback")
@Scope(ScopeType.SESSION)
public class GestioneFeedback  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6615681325977116252L;

	@In(value="accountHome", create=true)
	private AccountHome accountHome;
	
	@In(value="currentAccount")
	private Account currentAccount;

	@In(value="ordineBean", create=true)
	private OrdineBean ordineBean;
	
	@In(value="entityManager")
    private EntityManager em;
	
	@In 
	private Credentials credentials;
	
	@In(value="feedbackList",create=true)
	private FeedbackList feedbackList;
	
	@Logger private Log log;
	
	private Cybercontadino currentContadino;
	
	private List<Feedback> myFeedback;
	
	
	public Cybercontadino getCurrentContadino() {
		return currentContadino;
	}

	public void setCurrentContadino(Cybercontadino currentContadino) {
		this.currentContadino = currentContadino;
	}

	public GestioneFeedback() {
		
	}
	
	
	public void assegnaFeedback(String username, Ordine ordine, float feedbackAssegnato, String commento)
	{
		//account dell'username
		log.info("******** ssegnaFeedback *********");
		log.info("------> username = "+username);
		accountHome.setAccountUsername(username);
		Account accountDaModificare = accountHome.find();	
		log.info(" accountDaModificare username = "+accountDaModificare.getUsername());
		//modifico feedback
		float newMedia = calcolaPunteggioFeedback(accountDaModificare,feedbackAssegnato);
		accountDaModificare.setPunteggioFeedback(newMedia);
		accountDaModificare.setNumeroVotanti(accountDaModificare.getNumeroVotanti()+1);
		
		//salvo info feedback
		salvaFeedback(accountDaModificare, ordine, feedbackAssegnato, commento);
		
		//aggiorno account
		accountHome.update();
		log.info(" accountDaModificare username = "+accountDaModificare.getUsername()+ " aggiornato");
	}
	
	public void assegnaFeedbackFromToDefault(String usernameFrom, String usernameTo, Ordine ordine)
	{
		//account dell'username
		log.info("******* aaaaaaaaaaaaaaaaaaaaaaaaaa");
		log.info("******* from " +usernameFrom);
		log.info("******* to " +usernameTo);
		log.info("******* aaaaaaaaaaaaaaaaaaaaaaaaaa");
		log.info("******* ");
		accountHome.setAccountUsername(usernameFrom);
		Account accountFrom = accountHome.find();
		accountHome.setAccountUsername(usernameTo);
		Account accountDaModificare = accountHome.find();
		//modifico feedback
		float newMedia = calcolaPunteggioFeedback(accountDaModificare,3);
		accountDaModificare.setPunteggioFeedback(newMedia);
		accountDaModificare.setNumeroVotanti(accountDaModificare.getNumeroVotanti()+1);
		
		//salvo info feedback
		salvaFeedbackFromTo(accountFrom, accountDaModificare, ordine, 3, "default...");
		
		//aggiorno account
		accountHome.update();
	}

	private float calcolaPunteggioFeedback(Account accountDaModificare, float punteggioFeedback)
	{
		float oldMedia = accountDaModificare.getPunteggioFeedback();
		float numeroVotanti = accountDaModificare.getNumeroVotanti();
		
		float oldPunteggio = oldMedia*numeroVotanti;
		float newPunteggio = (oldPunteggio+punteggioFeedback)/(++numeroVotanti);
		
		return newPunteggio;
	}
	
	private void salvaFeedback(Account accountDaModificare, Ordine ordine, float feedback, String commento)
	{
		Feedback newFeedback = new Feedback();
		newFeedback.setDescrizione(commento);
		newFeedback.setAnalizzato(false);
		newFeedback.setDataSegnalazione(new GregorianCalendar().getTime());
		newFeedback.setPunteggio(feedback);
		if(ordine!=null)
			newFeedback.setOrdine(ordine);
		newFeedback.setAccountByValidatore(currentAccount);
		newFeedback.setAccountBySegnalatore(currentAccount);
		newFeedback.setAccountByDestinatario(accountDaModificare);
		em.persist(newFeedback);
		newFeedback = new Feedback();
	}
	
	private void salvaFeedbackFromTo(Account accountFrom, Account accountDaModificare, Ordine ordine, float feedback, String commento)
	{
		Feedback newFeedback = new Feedback();
		newFeedback.setDescrizione(commento);
		newFeedback.setAnalizzato(false);
		newFeedback.setDataSegnalazione(new GregorianCalendar().getTime());
		newFeedback.setPunteggio(feedback);
		if(ordine!=null)
			newFeedback.setOrdine(ordine);
		newFeedback.setAccountByValidatore(accountFrom);
		newFeedback.setAccountBySegnalatore(accountFrom);
		newFeedback.setAccountByDestinatario(accountDaModificare);
		em.persist(newFeedback);
		newFeedback = new Feedback();
	}
	
	public List<Feedback> getMyFeedback()
	 {
		feedbackList.setEjbql("select feedback from Feedback feedback where feedback.accountByDestinatario.username= '"+credentials.getUsername()+"' and feedback.analizzato = true");
		myFeedback = feedbackList.getResultList();
		return myFeedback;
	 }

}