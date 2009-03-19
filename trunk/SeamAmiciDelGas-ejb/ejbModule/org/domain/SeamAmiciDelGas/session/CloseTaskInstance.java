package org.domain.SeamAmiciDelGas.session;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Ordine;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jbpm.JbpmContext;
import org.jbpm.taskmgmt.exe.TaskInstance;



@Name("closeTaskInstance")
@Scope(ScopeType.SESSION)
public class CloseTaskInstance {
	
	@In(value="gestioneFeedback", create=true)
	private GestioneFeedback gestioneFeedback;
	
	@In(value="takeInHandForDriver", create=true)
	@Out(value="takeInHandForDriver",scope=ScopeType.SESSION,required=false)
	private TakeInHandForDriver takeInHandForDriver;
	
	@Logger private Log log;
	
	@In protected JbpmContext jbpmContext;
	
	
	//chiudo i task di tipo fb_responsabile_to_customer
	public String closefbResponsabileConsegnaToCustomer() {
		
		Hashtable<String, InfoFeedback> hashTableCustomer = takeInHandForDriver.getHashTableCustomer();
		log.info("******* task size " +takeInHandForDriver.getTaskInstanceCustomerForItinerario().size());

		for (TaskInstance t: takeInHandForDriver.getTaskInstanceCustomerForItinerario()) {

			TaskInstance managedTaskInstance = jbpmContext.getTaskInstance(t.getId());
			
			Account account = (Account) managedTaskInstance.getVariable("customer");
			log.info("******* task customer " +account.getUsername());
			

			InfoFeedback infoFeedback = hashTableCustomer.get(account.getUsername());
			gestioneFeedback.assegnaFeedback(account.getUsername(), (Ordine) managedTaskInstance.getVariable("ordine"), (float) infoFeedback.getFeedback(), infoFeedback.getComment());
			log.info("******* task instance " +managedTaskInstance.toString());

			managedTaskInstance.end("fb_responsabile_to_customer");

			//settare il booleano
		}

		takeInHandForDriver.reset();
		return "fb_responsabile_to_customer";
	}
	
public String closefbResponsabileConsegnaToContadino() {
		
		Hashtable<String, InfoFeedback> hashTableContadini = takeInHandForDriver.getHashTableContadini();
		
		for (TaskInstance t: takeInHandForDriver.getTaskInstanceContadinoForItinerario()) {

			TaskInstance managedTaskInstance = jbpmContext.getTaskInstance(t.getId());
			
			Account account = (Account) managedTaskInstance.getVariable("customer");
			InfoFeedback infoFeedback = hashTableContadini.get(account.getUsername());
			gestioneFeedback.assegnaFeedback(account.getUsername(), (Ordine) managedTaskInstance.getVariable("ordine"), (float) infoFeedback.getFeedback(), infoFeedback.getComment());
			log.info("******* task instance " +managedTaskInstance.toString());

			managedTaskInstance.end("fb_contadino_to_responsabile_consegna");

			//settare il booleano
		}

		takeInHandForDriver.reset();
		return "fb_responsabile_to_customer";
	}
	
	
}