package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Ordine;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jbpm.JbpmContext;
import org.jbpm.taskmgmt.exe.TaskInstance;



@Name("closeTaskInstance")
@Scope(ScopeType.SESSION)
public class CloseTaskInstance {
	
	@In(value="gestioneFeedback", create=true)
	private GestioneFeedback gestioneFeedback;
	
	@In private Credentials credentials;
	
	@In(value="takeInHandForDriver", create=true)
	@Out(value="takeInHandForDriver",scope=ScopeType.SESSION,required=false)
	private TakeInHandForDriver takeInHandForDriver;
	
	@In(value="takeInHandForContadinoToDriver", create=true)
	@Out(value="takeInHandForContadinoToDriver",scope=ScopeType.SESSION,required=false)
	private TakeInHandForContadinoToDriver takeInHandForContadinoToDriver;
	
	@In(value="takeInHandForContadinoToCustomer", create=true)
	@Out(value="takeInHandForContadinoToCustomer",scope=ScopeType.SESSION,required=false)
	private TakeInHandForContadinoToCustomer takeInHandForContadinoToCustomer;
	
	
	@Logger private Log log;
	
	@In protected JbpmContext jbpmContext;
	
	
	//chiudo i task di tipo fb_responsabile_to_customer
	public String closefbResponsabileConsegnaToCustomer() {
		
		Hashtable<String, InfoFeedback> hashTableCustomer = takeInHandForDriver.getHashTableCustomer();
		log.info("******* task size customer " +takeInHandForDriver.getTaskInstanceCustomerForItinerario().size());
		
		for (TaskInstance t: takeInHandForDriver.getTaskInstanceCustomerForItinerario()) {

			TaskInstance managedTaskInstance = jbpmContext.getTaskInstance(t.getId());
			
			Account account = (Account) managedTaskInstance.getVariable("customer");
			log.info("******* task customer " +account.getUsername());
			
			InfoFeedback infoFeedback = hashTableCustomer.get(account.getUsername());
			gestioneFeedback.assegnaFeedback(account.getUsername(), (Ordine) managedTaskInstance.getVariable("ordine"), (float) infoFeedback.getFeedback(), infoFeedback.getComment());
			log.info("******* task instance " +managedTaskInstance.toString());

			//setto il fatto che il contadino ha rilasciato il feedback al driver
			Boolean votatoDriverToCustomer = (Boolean) managedTaskInstance.getVariable("booleanResponsabileConsegnaToCustomer");
			votatoDriverToCustomer = new Boolean(true);
			managedTaskInstance.setVariable("booleanResponsabileConsegnaToCustomer", votatoDriverToCustomer);
			
			managedTaskInstance.end("fb_responsabile_to_customer");
			//settare il booleano
		}
		takeInHandForDriver.reset();
		return "fb_responsabile_to_customer";
	}
	
	
	
	public String closefbResponsabileConsegnaToContadino() {

	Hashtable<String, InfoFeedback> hashTableContadini = takeInHandForDriver.getHashTableContadini();
	log.info("******* task size contadini " +takeInHandForDriver.getTaskInstanceContadinoForItinerario().size());
	
	List<String> usernameContadini = new ArrayList<String>(); 
	usernameContadini.addAll(hashTableContadini.keySet());
	
	for(String uContadino : usernameContadini) {
		
		log.info("******* task contadino " +uContadino);
		InfoFeedback infoFeedback = hashTableContadini.get(uContadino);
		if(infoFeedback!=null)
			gestioneFeedback.assegnaFeedback(uContadino, null, (float) infoFeedback.getFeedback(), infoFeedback.getComment());
		else
		{	
			log.info("******* task contadino mannaggiaaaaaaaaaaaaa" +uContadino);
			infoFeedback = new InfoFeedback("",3);
			gestioneFeedback.assegnaFeedback(uContadino, null, (float) infoFeedback.getFeedback(), infoFeedback.getComment());
		}
	}
	//chiudiamo le task instance...
	for (TaskInstance t: takeInHandForDriver.getTaskInstanceContadinoForItinerario()) {
		TaskInstance managedTaskInstance = jbpmContext.getTaskInstance(t.getId());
		
		//setto il fatto che il contadino ha rilasciato il feedback al driver
		Boolean votatoDriverToContadino = (Boolean) managedTaskInstance.getVariable("booleanResponsabileConsegnaToContadino");
		votatoDriverToContadino = new Boolean(true);
		managedTaskInstance.setVariable("booleanResponsabileConsegnaToContadino", votatoDriverToContadino);
		
		log.info("******* task instance " +managedTaskInstance.toString());
		managedTaskInstance.end("fb_responsabile_consegna_to_contadino");
	}

	takeInHandForDriver.reset();
	return "fb_responsabile_consegna_to_contadino";
}
		
	//metodo invocato quando il contadino è il responsabile della consegna	
	public String closefbContadinoToCustomer() {
		
		Hashtable<Integer, InfoFeedback> hashTable = takeInHandForContadinoToCustomer.getHashTable();
		log.info("******* Number of order " +hashTable.size());
		
		for (TaskInstance t: takeInHandForContadinoToCustomer.getTaskInstanceForCurrentAccount()) {

			TaskInstance managedTaskInstance = jbpmContext.getTaskInstance(t.getId());
			
			Account account = (Account) managedTaskInstance.getVariable("customer");
			log.info("******* task customer " +account.getUsername());
			
			Ordine ordine = (Ordine) managedTaskInstance.getVariable("ordine");
			
			InfoFeedback infoFeedback = hashTable.get(ordine.getIdordine());
			if (infoFeedback!=null)
				gestioneFeedback.assegnaFeedback(account.getUsername(), ordine, (float) infoFeedback.getFeedback(), infoFeedback.getComment());
			log.info("******* task instance " +managedTaskInstance.toString());

			//setto il fatto che il contadino ha rilasciato il feedback al driver
			Boolean votatoContadinoToDriver = (Boolean) managedTaskInstance.getVariable("booleanResponsabileConsegnaToCustomer");
			votatoContadinoToDriver = new Boolean(true);
			managedTaskInstance.setVariable("booleanResponsabileConsegnaToCustomer", votatoContadinoToDriver);
			
			managedTaskInstance.end("fb_responsabile_to_customer");
			
		}
		takeInHandForContadinoToCustomer.reset();
		
		return "fb_responsabile_to_customer";
	}
		
		
		
	public String closefbContadinoToResponsabile() {
		
		Hashtable<Integer, InfoFeedback> hashTable = takeInHandForContadinoToDriver.getHashTable();
		log.info("******* num di feedback da assegnare "+hashTable.size());
		String usernameDriver = takeInHandForContadinoToDriver.getStringaResponsabileConsegna();
		log.info("******* Driver corrente "+usernameDriver);
		
		//salvo i feedback
		Enumeration<Integer> enumeration = hashTable.keys();
		Integer idItinerario;
		while (enumeration.hasMoreElements()) {
			idItinerario = enumeration.nextElement();
			InfoFeedback infoFeedback = hashTable.get(idItinerario);
			if(infoFeedback!=null)
				gestioneFeedback.assegnaFeedback(usernameDriver, null, (float) infoFeedback.getFeedback(), infoFeedback.getComment());
		}
		
		//chiudo le task instance
		for (TaskInstance t: takeInHandForContadinoToDriver.getTaskInstanceListForResponsabile()) {
			TaskInstance managedTaskInstance = jbpmContext.getTaskInstance(t.getId());
			log.info("******* task instance " +managedTaskInstance.toString());
			
			//tengo traccia che il contadino corrente ha votato il driver
			Hashtable<String,Boolean> booleanFeedbackContadiniToResponsabile = (Hashtable<String,Boolean>) t.getVariable("booleanFeedbackContadiniToResponsabile");
			booleanFeedbackContadiniToResponsabile.put(credentials.getUsername(), new Boolean(true));
			Boolean votato = booleanFeedbackContadiniToResponsabile.get(credentials.getUsername());
			votato = new Boolean(true);
			managedTaskInstance.setVariable("booleanFeedbackContadiniToResponsabile", booleanFeedbackContadiniToResponsabile);
			
			if (this.allHannoVotato(booleanFeedbackContadiniToResponsabile))
				managedTaskInstance.end("fb_contadino_to_responsabile_consegna");
		}

		takeInHandForContadinoToDriver.reset();
		return "fb_contadino_to_responsabile_consegna";
	}
	
	//verifica se tutti i contadini hanno votato il driver
	private boolean allHannoVotato(Hashtable<String,Boolean> booleanFeedbackContadiniToResponsabile) {
		Enumeration<Boolean> enumeration = (Enumeration<Boolean>) booleanFeedbackContadiniToResponsabile.elements();
		boolean all = true;
		while (enumeration.hasMoreElements()) {
			Boolean votato = enumeration.nextElement();
			if (!(votato.booleanValue())) {
				all=false;
				break;
			}
		}
		return all;
	}
		
		
		
}