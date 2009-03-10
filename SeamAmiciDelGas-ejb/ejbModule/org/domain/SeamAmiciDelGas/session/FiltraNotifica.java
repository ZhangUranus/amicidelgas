package org.domain.SeamAmiciDelGas.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.bpm.PooledTaskInstanceList;
import org.jboss.seam.bpm.TaskInstanceList;
import org.jboss.seam.security.Credentials;
import org.jbpm.taskmgmt.exe.TaskInstance;

@Name("filtraNotifica")
@Scope(ScopeType.SESSION)
public class FiltraNotifica {

	@In(value="pooledTaskInstanceList", create=true)
	private PooledTaskInstanceList pooledTaskInstanceList;
	
	@In(value="taskInstanceList", create=true)
	private TaskInstanceList taskInstanceList;
	
	@In private Credentials credentials;
	
	public int numberForSingleForCustomer() {
		int count = 0;
		for (TaskInstance ti: taskInstanceList.getTaskInstanceList()) {
			if (ti.getVariable("notifyMessage")!=null)
				count++;
		}
		return count;
	}
	
	public int numberForGroupForCustomer() {
		int count = 0;
		for (TaskInstance ti: pooledTaskInstanceList.getPooledTaskInstanceList()) {
			if (ti.getVariable("notifyMessage")!=null)
				count++;
		}
		return count;
	}
	
	/*
	public int numberForSingleForDriver() {
		
	}
	*/
	
	/*
	public int numberForGroupForDriver() {
		
	}
	*/
	
}
