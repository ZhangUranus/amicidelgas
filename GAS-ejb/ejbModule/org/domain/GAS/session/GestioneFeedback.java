package org.domain.GAS.session;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.domain.GAS.crud.AccountHome;
import org.domain.GAS.crud.AccountList;
import org.domain.GAS.crud.FeedbackHome;
import org.domain.GAS.crud.FeedbackList;
import org.domain.GAS.entity.Account;
import org.domain.GAS.entity.Cybercontadino;
import org.domain.GAS.entity.Feedback;
import org.domain.GAS.entity.Ordine;
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
		accountHome.setAccountUsername(username);
		Account accountDaModificare = accountHome.find();	
		//modifico feedback
		float newMedia = calcolaPunteggioFeedback(accountDaModificare,feedbackAssegnato);
		accountDaModificare.setPunteggioFeedback(newMedia);
		accountDaModificare.setNumeroVotanti(accountDaModificare.getNumeroVotanti()+1);
		
		//salvo info feedback
		salvaFeedback(accountDaModificare, ordine, feedbackAssegnato, commento);
		
		//aggiorno account
		accountHome.update();
	}
	
	public void assegnaFeedbackFromToDefault(String usernameFrom, String usernameTo, Ordine ordine)
	{
		//account dell'username
		accountHome.setAccountUsername(usernameFrom);
		Account accountFrom = accountHome.find();
		accountHome.setAccountUsername(usernameTo);
		Account accountDaModificare = accountHome.find();
		//modifico feedback
		float newMedia = calcolaPunteggioFeedback(accountDaModificare,3);
		accountDaModificare.setPunteggioFeedback(newMedia);
		accountDaModificare.setNumeroVotanti(accountDaModificare.getNumeroVotanti()+1);
		
		//salvo info feedback
		salvaFeedbackFromTo(accountFrom, accountDaModificare, ordine, 3, "Feedback assegnato automaticamente per l'ordine "+ordine.getIdordine()+".");
		
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
		newFeedback.setDataValidazione(new GregorianCalendar().getTime());
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
		newFeedback.setAnalizzato(true);
		newFeedback.setDataSegnalazione(new GregorianCalendar().getTime());
		newFeedback.setDataValidazione(new GregorianCalendar().getTime());
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
		feedbackList.setEjbql("select feedback from Feedback feedback where feedback.accountByDestinatario.username= '"+credentials.getUsername()+"'");
		myFeedback = feedbackList.getResultList();
		return myFeedback;
	 }

}