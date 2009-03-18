package org.domain.SeamAmiciDelGas.session;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jbpm.taskmgmt.exe.TaskInstance;



@Name("closeTaskInstance")
@Scope(ScopeType.SESSION)
public class CloseTaskInstance {
	
	
	public String closefbResponsabileConsegnaToCustomer(TakeInHandForDriver takeInHandForDriver) {
		List<TaskInstance> tasks = takeInHandForDriver.getTaskInstanceCustomerForItinerario();
		for (TaskInstance t: tasks)
			t.start();
		return "complete";
	}
	
	
}