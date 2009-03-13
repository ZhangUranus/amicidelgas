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

	public int numberOfTaskForSingle(String... taskFilter)	{
		return numberOfTask("taskForSingle", taskFilter);
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
	
	public int numberOfTaskForGroup(String... taskFilter)	{
		return numberOfTask("taskForGroup", taskFilter);
	}
	
	/**
	 * numberOfTask(String SingleOrGroupTask, String... filters)
	 * Torna il numero di task per single o per gruppi con 
	 * gli opportuni filtri sul nome del filtro
	 * @return un int che rappresenta il numero di task con gli opportuni filtri
	 * @param SingleOrGroupTask può essere: "taskForSingle" oppure "taskForGroup" 
	 * @param filters una o più stringhe separate da una virgola che rappresentano i task
	 * 			a cui associare il filtro.
	 */
	public int numberOfTask(String SingleOrGroupTask, String... filters)
	{
		List<TaskInstance> tempTaskInstance;
		if(SingleOrGroupTask.equals("taskForSingle"))
			tempTaskInstance = taskInstanceList;
		else if(SingleOrGroupTask.equals("taskForGroup"))
			tempTaskInstance = pooledTaskInstanceList;
		else
			return 9999; //tempTaskInstance = null; //per verifica
		int count = 0;
		for (TaskInstance ti: tempTaskInstance)
			for(String singleFilter : filters)
				if (ti.getName().equals(singleFilter))
					count++;
		return count;
	}
	
	/*
	 * Ritorno la lista di task instance per singolo utente
	 */
	
	public List<TaskInstance> taskInstanceSingleListForCustomer() {
		return getAllSingleTaskInstanceList("ReceiveMessage",
												 "ReceiveOrderFailed","ReceiveOrderAccepted");
	}
	
	/**
	 * public List<TaskInstance> taskInstanceSingleListForCustomerDinamic(String... filters)
	 * {
	 * List<TaskInstance> tasks= new ArrayList<TaskInstance>();
	 *	for(String taskFilter : filters)
	 *		tasks.addAll(taskInstanceSingleList(taskFilter));
	 *	return tasks;
	 * }
	 * 
	 * @param filters Una o più stringhe che rappresentano i filtri
	 * @return ritorna una lista di TaskInstance 
	 */
	public List<TaskInstance> getAllSingleTaskInstanceList(String... filters)
	{
		List<TaskInstance> tasks= new ArrayList<TaskInstance>();
		for(String taskFilter : filters)
			tasks.addAll(taskInstanceSingleList(taskFilter));
		return tasks;
	}
	
	private List<TaskInstance> taskInstanceSingleList(String taskFilter) {
		List<TaskInstance> tasks = new ArrayList<TaskInstance>();
		for (TaskInstance ti: taskInstanceList) {
			if (ti.getName().equals(taskFilter))
				tasks.add(ti);
		}
		return tasks;
	}
	
	public List<TaskInstance> taskInstanceSingleListForDriver() {
		return taskInstanceSingleList("accetta_ordine");
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
	
	public List<TaskInstance> getAllPooledTaskInstanceList(String... filters)
	{
		List<TaskInstance> tasks= new ArrayList<TaskInstance>();
		for(String taskFilter : filters)
			tasks.addAll(taskInstanceGroupList(taskFilter));
		return tasks;
	}
	
	public List<TaskInstance> getAllPooledTaskInstanceListForSingleCustomer(String username, String... filters)
	{
		List<TaskInstance> tasks= getAllPooledTaskInstanceList(filters);
		List<TaskInstance> tasksUser = new ArrayList<TaskInstance>();
		for(TaskInstance temp : tasks)
		{
			String s = (String) temp.getVariable("customer");
			if(s.equals(username))
				tasksUser.add(temp);
		}
		return tasksUser;
		
	}
	
	private List<TaskInstance> taskInstanceGroupList(String taskFilter) {
		List<TaskInstance> tasks = new ArrayList<TaskInstance>();
		for (TaskInstance ti: pooledTaskInstanceList) {
			if (ti.getName().equals(taskFilter))
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
