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
	
	/*
	 * ritorno il numero di notifiche per singolo utente
	 */
	
	public int numberForSingleForCustomer() {
		return numberOfTaskForSingle("ReceiveMessage");
	}

	public int numberForSingleForDriver() {
		return numberOfTaskForSingle("accetta_ordine");
	}

	public int numberOfTaskForSingle(String taskType)	{
		int count = 0;
		for (TaskInstance ti: taskInstanceList)
			if (ti.getName().equals(taskType))
				count++;
		return count;
	}

	/*
	 * ritorno il numero di notifiche per gruppi
	 */
	
	public int numberForGroupForCustomer() 	{
		return numberOfTaskForGroup("ReceiveMessage");
	}

	public int numberForGroupForDriver() {
		return numberOfTaskForGroup("accetta_ordine");
	}
	
	public int numberOfTaskForGroup(String taskType)	{
		int count = 0;
		for (TaskInstance ti: pooledTaskInstanceList)
			if (ti.getName().equals(taskType))
				count++;
		return count;
	}
	
	/*
	 * Ritorno task instance per singolo utente
	 */
	
	public List<TaskInstance> taskInstanceSingleListForCustomer() {
		return taskInstanceSingleList("ReceiveMessage");
	}
	
	public List<TaskInstance> taskInstanceSingleListForDriver() {
		return taskInstanceSingleList("accetta_ordine");
	}
	
	public List<TaskInstance> taskInstanceSingleList(String taskType) {
		List<TaskInstance> tasks = new ArrayList<TaskInstance>();
		for (TaskInstance ti: taskInstanceList) {
			if (ti.getName().equals(taskType))
				tasks.add(ti);
		}
		return tasks;
	}
	
	
	/*
	 * ritorno task instance per gruppi
	 */
	
	public List<TaskInstance> taskInstanceGroupListForCustomer() {
		return taskInstanceGroupList("ReceiveMessage");
	}
	
	public List<TaskInstance> taskInstanceGroupListForDriver() {
		return taskInstanceGroupList("accetta_ordine");
	}
	
	public List<TaskInstance> taskInstanceGroupList(String taskType) {
		List<TaskInstance> tasks = new ArrayList<TaskInstance>();
		for (TaskInstance ti: pooledTaskInstanceList) {
			if (ti.getName().equals(taskType))
				tasks.add(ti);
		}
		return tasks;
	}
	
	/*
	 * Gestione contadino con filtro sul nome
	 */

	public List<TaskInstance> taskInstanceSingleListForContadino() {
		List<TaskInstance> tasks = new ArrayList<TaskInstance>();
		Message notifica;
		for (TaskInstance ti: pooledTaskInstanceList) {
			notifica = (Message) ti.getVariable("accetta_ordine");
			if (notifica!=null && notifica.getRecipients().contains(credentials.getUsername()))
				tasks.add(ti);
		}
		return tasks;
	}
	
	
	public int numberForSingleForContadino() {
		int count = 0;
		Message notifica;
		for (TaskInstance ti: pooledTaskInstanceList) {
			notifica = (Message) ti.getVariable("accetta_ordine");
			if (notifica!=null && notifica.getRecipients().contains(credentials.getUsername()))
				count++;
		}
		return count;
	}
	

}
