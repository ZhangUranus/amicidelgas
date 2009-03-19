package org.domain.SeamAmiciDelGas.session;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Ordine;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jbpm.JbpmContext;
import org.jbpm.taskmgmt.exe.TaskInstance;



@Name("closeTaskInstance")
@Scope(ScopeType.SESSION)
public class CloseTaskInstance {
	
	@In(value="gestioneFeedback", create=true)
	private GestioneFeedback gestioneFeedback;
	
	//chiudo i task di tipo fb_responsabile_to_customer
	public String closefbResponsabileConsegnaToCustomer(TakeInHandForDriver takeInHandForDriver) {
		List<TaskInstance> tasks = takeInHandForDriver.getTaskInstanceCustomerForItinerario();
		Hashtable<String, InfoFeedback> hashTableCustomer = takeInHandForDriver.getHashTableCustomer();
		JbpmContext jbpmContext;
		TaskInstance managedTaskInstance = null;
		for (TaskInstance t: tasks) {
			jbpmContext = org.jbpm.JbpmConfiguration.getInstance().createJbpmContext();
			managedTaskInstance = jbpmContext.getTaskInstance(t.getId());
			Account account = (Account) managedTaskInstance.getVariable("customer");
			String username = account.getUsername();
			InfoFeedback infoFeedback = hashTableCustomer.get(username);
			gestioneFeedback.assegnaFeedback(username, (Ordine) managedTaskInstance.getVariable("ordine"), (float) infoFeedback.getFeedback(), infoFeedback.getComment());
			managedTaskInstance.end("fb_responsabile_to_customer");
			
			//settare il booleano
		}
			
		
			
		takeInHandForDriver.reset();
		return "fb_responsabile_to_customer";
	}
	
	
}