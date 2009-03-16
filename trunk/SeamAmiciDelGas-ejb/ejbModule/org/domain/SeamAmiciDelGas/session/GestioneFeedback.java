package org.domain.SeamAmiciDelGas.session;


import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.domain.SeamAmiciDelGas.crud.AccountHome;
import org.domain.SeamAmiciDelGas.crud.AccountList;
import org.domain.SeamAmiciDelGas.crud.FeedbackHome;
import org.domain.SeamAmiciDelGas.crud.FeedbackListExtended;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Feedback;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name(value="gestioneFeedback")
@Scope(ScopeType.SESSION)
public class GestioneFeedback {
	
	@In(value="feedbackHome", create=true)
	private FeedbackHome feedBackHome;
	
	@In(value="newFeedbackListExtended",create=true)
	private FeedbackListExtended feedbackListExtended;
	
	@In(value="accountHome", create=true)
	private AccountHome accountHome;
	
	@In(value="currentAccount")
	private Account currentAccount;
	
	@In(value="accountList", create=true)
	private AccountList accountList;
	
	@In(value="ordineBean", create=true)
	private OrdineBean ordineBean;
	
	@In(value="entityManager")
    private EntityManager em;
	
	//informazioni sul feedback
	@In(value="newFeedback",create=true)
	private Feedback newFeedback;
	
	
	
	public Feedback getNewFeedback() {
		return newFeedback;
	}


	public void setNewFeedback(Feedback newFeedback) {
		this.newFeedback = newFeedback;
	}


	public GestioneFeedback() {
		
	}
	
	
	public void assegnaFeedback(String username, float feedbackAssegnato)
	{
		//account dell'username
		accountHome.setAccountUsername(username);
		Account accountDaModificare = accountHome.find();	
		
		//modifico feedback
		float newMedia = calcolaPunteggioFeedback(accountDaModificare,feedbackAssegnato);
		accountDaModificare.setPunteggioFeedback(newMedia);
		accountDaModificare.setNumeroVotanti(accountDaModificare.getNumeroVotanti()+1);
		
		//salvo info feedback
		salvaFeedback(accountDaModificare, feedbackAssegnato);
		
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
	
	private void salvaFeedback(Account accountDaModificare, float feedback)
	{
		newFeedback.setAnalizzato(false);
		newFeedback.setDataSegnalazione(new GregorianCalendar().getTime());
		newFeedback.setPunteggio(feedback);
		newFeedback.setOrdine(ordineBean.getCurrentOrdine());
		newFeedback.setAccountByValidatore(currentAccount);
		newFeedback.setAccountBySegnalatore(currentAccount);
		newFeedback.setAccountByDestinatario(accountDaModificare);
		em.persist(newFeedback);

		
	}
	
	
	
	
}