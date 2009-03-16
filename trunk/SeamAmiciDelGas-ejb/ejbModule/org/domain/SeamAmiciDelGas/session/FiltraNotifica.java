package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.List;

import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.bpm.PooledTaskInstanceList;
import org.jboss.seam.bpm.TaskInstanceList;
import org.jboss.seam.security.Credentials;
import org.jbpm.taskmgmt.def.Task;
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
	
	public int numberOfHighestPriorityTaskForSingle()	{
		return numberOfHighPriorityTask("taskForSingle");
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
	 * @param SingleOrGroupTask pu� essere: "taskForSingle" oppure "taskForGroup" 
	 * @param filters una o pi� stringhe separate da una virgola che rappresentano i task
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
	
	/**
	 * numberOfTask(String SingleOrGroupTask, String... filters)
	 * Torna il numero di task con priorit� alta per single o per gruppi con 
	 * gli opportuni filtri sul nome del filtro
	 * @return un int che rappresenta il numero di task a priorit� alta con gli opportuni filtri
	 * @param SingleOrGroupTask pu� essere: "taskForSingle" oppure "taskForGroup" 
	 * @param filters una o pi� stringhe separate da una virgola che rappresentano i task
	 * 			a cui associare il filtro.
	 */
	public int numberOfHighPriorityTask(String SingleOrGroupTask)
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
			if ((ti.getPriority() == Task.PRIORITY_HIGHEST))
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
	 * @param filters Una o pi� stringhe che rappresentano i filtri
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
		Account account = null;
		for(TaskInstance temp : tasks)
		{
			account = (Account) temp.getVariable("customer");
			if(account.getUsername().equals(username))
				tasksUser.add(temp);
		}
		return tasksUser;
		
	}
	
	
	public List<Cybercontadino> getAllContadinoForTaskInstanceType(String username, String taskName)
	{
		// Primo filtro che restiuisce la lista dei contadini per un tipo di  task e assegnato ad un utente
		List<TaskInstance> tasks= taskInstanceSingleList(taskName);
		List<Cybercontadino> contadini = new ArrayList<Cybercontadino>();
		List<String> partiteIva = new ArrayList<String>();
		String nome;
		Cybercontadino nomeContadino;
		for(TaskInstance temp : tasks)
		{
			nome = temp.getActorId();
			nomeContadino = (Cybercontadino) temp.getVariable("contadino");
			if((nome.equals(username))){
				if(nomeContadino != null && !(partiteIva.contains(nomeContadino.getPartitaIva()))){						
					contadini.add(nomeContadino);
					partiteIva.add(nomeContadino.getPartitaIva());
				}
			}
				
		}
		return contadini;
	}
	
	public int getSizeForAllContadinoForTaskInstanceType(String username, String taskName)
	{
		// Primo filtro che restiuisce il numero di contadini per un tipo di  task e assegnato ad un utente
		//System.out.println("PARCOSEMPIONE00000");
		List<TaskInstance> tasks= taskInstanceSingleList(taskName);
		//System.out.println("Stampa numero di task: " +tasks.size());
		List<Cybercontadino> contadini = new ArrayList<Cybercontadino>();
		List<String> partiteIva = new ArrayList<String>();
		String nome;
		Cybercontadino nomeContadino;
		for(TaskInstance temp : tasks)
		{
			nome = temp.getActorId();
			nomeContadino = (Cybercontadino) temp.getVariable("contadino");
			if((nome.equals(username))){
				//System.out.println("sono admin: "+username+" taskname: "+taskName);
				if((nomeContadino != null) && !(partiteIva.contains(nomeContadino.getPartitaIva()))){
					contadini.add(nomeContadino);
					partiteIva.add(nomeContadino.getPartitaIva());
					//System.out.println("NOME CONTADINO: "+nomeContadino.getDescrizioneAzienda());
				}
			}
			//System.out.println("PARCOSEMPIONE");
		}
		return contadini.size();
	}
	
	public List<TaskInstance> getAllTaskInstanceListForContadino(String username, String taskName, String contadino)
	{
		// Seoncod filtro che restituisce i task per un contadino e assegnato a un utente
		
		List<TaskInstance> tasks= taskInstanceSingleList(taskName);
		
		List<TaskInstance> tasksUser = new ArrayList<TaskInstance>();
		Cybercontadino nomeContadino ;
		String nome;
		for(TaskInstance temp : tasks)
		{
			nomeContadino = (Cybercontadino) temp.getVariable("contadino");
			nome = temp.getActorId();
			if((nome.equals(username)))
				if(nomeContadino.getPartitaIva().equals(contadino))
					tasksUser.add(temp);
		}
		return tasksUser;
		
	}
	
	public List<TaskInstance> getAllHighestPriorityTaskInstanceListForSingleCustomer(String username ,String  taskName)
	{
		List<TaskInstance> tasks = taskInstanceSingleList(taskName);
		List<TaskInstance> tasksUser = new ArrayList<TaskInstance>();
		//Account account = null;
		String nome;
		for(TaskInstance temp : tasks)
		{
			//account = (Account) temp.getVariable("currentAccount");
			nome = temp.getActorId();
			if(nome != null)
			{
				if((nome.equals(username)) && (temp.getPriority() == Task.PRIORITY_HIGHEST))
					tasksUser.add(temp);
			}
		}
		return tasksUser;
		
	}
	
	public List<TaskInstance> getAllPooledHighestPriorityTaskInstanceListForSingleCustomer(String username)
	{
		List<TaskInstance> tasks= pooledTaskInstanceList;
		List<TaskInstance> tasksUser = new ArrayList<TaskInstance>();
		Account account = null;
		for(TaskInstance temp : tasks)
		{
			account = (Account) temp.getVariable("currentAccoun");
			if((account.getUsername().equals(username))&& (temp.getPriority() == Task.PRIORITY_HIGHEST))
				tasksUser.add(temp);
		}
		return tasksUser;
		
	}
	
	public List<TaskInstance> getAllPooledTaskInstanceListForAllUserButMeNot(String username, String... filters)
	{
		List<TaskInstance> tasks= getAllPooledTaskInstanceList(filters);
		List<TaskInstance> tasksUser = new ArrayList<TaskInstance>();
		
		ArrayList<String> arraylist = null;
		
		for(TaskInstance temp : tasks)
		{
			arraylist = (ArrayList<String>) temp.getVariable("inviati");
			
			if(arraylist==null){
				tasksUser.add(temp);
				System.out.println("vuoto");
			} else {
				System.out.println("pieno");
				if(!(arraylist.contains(username)))
					tasksUser.add(temp);
			}
			
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
