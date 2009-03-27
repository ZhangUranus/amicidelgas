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
		
		for (TaskInstance t: takeInHandForDriver.getTaskInstanceCustomerForItinerario()) {
			TaskInstance managedTaskInstance = jbpmContext.getTaskInstance(t.getId());
			if(!managedTaskInstance.isOpen())
				return "fb_responsabile_to_customer";
			Account account = (Account) managedTaskInstance.getVariable("customer");
			InfoFeedback infoFeedback = hashTableCustomer.get(account.getUsername());
			gestioneFeedback.assegnaFeedback(account.getUsername(), (Ordine) managedTaskInstance.getVariable("ordine"), (float) infoFeedback.getFeedback(), infoFeedback.getComment());
			
			//setto il booleano associato a true
			managedTaskInstance.setVariable("booleanResponsabileConsegnaToCustomer", new Boolean(true));

			//chiudo l'instanza del task
			managedTaskInstance.end("fb_responsabile_to_customer");
			
			
		}
		takeInHandForDriver.reset();
		return "fb_responsabile_to_customer";
	}
	
	
	
	public String closefbResponsabileConsegnaToContadino() {

	Hashtable<String, InfoFeedback> hashTableContadini = takeInHandForDriver.getHashTableContadini();
	
	List<String> usernameContadini = new ArrayList<String>(); 
	usernameContadini.addAll(hashTableContadini.keySet());
	
	for(String uContadino : usernameContadini) {
		InfoFeedback infoFeedback = hashTableContadini.get(uContadino);
		gestioneFeedback.assegnaFeedback(uContadino, null, (float) infoFeedback.getFeedback(), infoFeedback.getComment());
	}

	for (TaskInstance t: takeInHandForDriver.getTaskInstanceContadinoForItinerario()) {
		//if(!t.isOpen())
			//return "fb_responsabile_consegna_to_contadino";
		TaskInstance managedTaskInstance = jbpmContext.getTaskInstance(t.getId());
		if(!managedTaskInstance.isOpen())
			return "fb_responsabile_consegna_to_contadino";
		//setto il booleano associato a true
		managedTaskInstance.setVariable("booleanResponsabileConsegnaToContadino", new Boolean(true));
		//chiudo l'instanza del task
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
			//if(!t.isOpen())
				//return "fb_responsabile_to_customer";
			TaskInstance managedTaskInstance = jbpmContext.getTaskInstance(t.getId());
			if(!managedTaskInstance.isOpen())
				return "fb_responsabile_to_customer";
			Account account = (Account) managedTaskInstance.getVariable("customer");
			
			Ordine ordine = (Ordine) managedTaskInstance.getVariable("ordine");
			
			InfoFeedback infoFeedback = hashTable.get(ordine.getIdordine());
			if (infoFeedback!=null)
				gestioneFeedback.assegnaFeedback(account.getUsername(), ordine, (float) infoFeedback.getFeedback(), infoFeedback.getComment());

			//setto il fatto che il contadino ha rilasciato il feedback al customer
			managedTaskInstance.setVariable("booleanResponsabileConsegnaToCustomer", new Boolean(true));
			
			managedTaskInstance.end("fb_responsabile_to_customer");
			
		}
		takeInHandForContadinoToCustomer.reset();
		
		return "fb_responsabile_to_customer";
	}
		
		
		
	public String closefbContadinoToResponsabile() {
		
		Hashtable<Integer, InfoFeedback> hashTable = takeInHandForContadinoToDriver.getHashTable();
		String usernameDriver = takeInHandForContadinoToDriver.getStringaResponsabileConsegna();
		
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
			if(!managedTaskInstance.isOpen())
				return "fb_contadino_to_responsabile_consegna";
			
			//tengo traccia che il contadino corrente ha votato il driver
			Hashtable<String,Boolean> booleanFeedbackContadiniToResponsabile = (Hashtable<String,Boolean>) t.getVariable("booleanFeedbackContadiniToResponsabile");
			booleanFeedbackContadiniToResponsabile.put(credentials.getUsername(), new Boolean(true));
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