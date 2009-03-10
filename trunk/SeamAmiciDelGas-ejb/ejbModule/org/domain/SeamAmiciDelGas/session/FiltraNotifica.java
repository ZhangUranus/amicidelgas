package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.List;

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
	private List<TaskInstance> pooledTaskInstanceList;
	
	@In(value="taskInstanceList", create=true)
	private List<TaskInstance> taskInstanceList;
	
	@In private Credentials credentials;
	
	public int numberForSingleForCustomer() {
		int count = 0;
		for (TaskInstance ti: taskInstanceList) {
			if (ti.getVariable("notifyMessage")!=null)
				count++;
		}
		return count;
	}
	
	public List<TaskInstance> taskInstanceSingleListForCustomer() {
		List<TaskInstance> tasks = new ArrayList<TaskInstance>();
		for (TaskInstance ti: taskInstanceList) {
			if (ti.getVariable("notifyMessage")!=null)
				tasks.add(ti);
		}
		return tasks;
	}
	
	public int numberForGroupForCustomer() {
		int count = 0;
		for (TaskInstance ti: pooledTaskInstanceList) {
			if (ti.getVariable("notifyMessage")!=null)
				count++;
		}
		return count;
	}
	
	public List<TaskInstance> taskInstanceGroupListForCustomer() {
		List<TaskInstance> tasks = new ArrayList<TaskInstance>();
		for (TaskInstance ti: pooledTaskInstanceList) {
			if (ti.getVariable("notifyMessage")!=null)
				tasks.add(ti);
		}
		return tasks;
	}
	
	
	public int numberForSingleForDriver() {
		int count = 0;
		for (TaskInstance ti: taskInstanceList) {
			if (ti.getVariable("notificaDriverContadino")!=null)
				count++;
		}
		return count;
	}
	
	public List<TaskInstance> taskInstanceSingleListForDriver() {
		List<TaskInstance> tasks = new ArrayList<TaskInstance>();
		for (TaskInstance ti: taskInstanceList) {
			if (ti.getVariable("notificaDriverContadino")!=null)
				tasks.add(ti);
		}
		return tasks;
	}
	
	public int numberForGroupForDriver() {
		int count = 0;
		for (TaskInstance ti: pooledTaskInstanceList) {
			if (ti.getVariable("notificaDriverContadino")!=null)
				count++;
		}
		return count;
	}
	
	public List<TaskInstance> taskInstanceGroupListForDriver() {
		List<TaskInstance> tasks = new ArrayList<TaskInstance>();
		for (TaskInstance ti: pooledTaskInstanceList) {
			if (ti.getVariable("notificaDriverContadino")!=null)
				tasks.add(ti);
		}
		return tasks;
	}
	
	
}
