package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.List;

import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Credentials;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;

@Name("filtraNotificaAltaPriorita")
@Scope(ScopeType.SESSION)
public class FiltraNotificaAltaPriorita {

	@In(value="pooledTaskInstanceList", create=true)
	private List<TaskInstance> pooledTaskInstanceList;
	
	@In(value="taskInstanceList", create=true)
	private List<TaskInstance> taskInstanceList;
	/*
	 * ritorno il numero di notifiche per gruppi
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
	
	public int numberOfTask(String SingleOrGroupTask, String taskName)
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
			if(ti.getName().equals(taskName))
					count++;
		System.out.println("COUNTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTtt"+count);
		return count;
	}
	
	public boolean TaskInstanceListForAllUserButMeNot(String username, String taskName)
	{
		System.out.println("AIUTOOOOOOOOOOOOOOOOOO TASKKKKKKKKKKKKKKK");
		List<TaskInstance> tasks= taskInstanceSingleList(taskName);
		List<TaskInstance> tasksUser = new ArrayList<TaskInstance>();
		
		ArrayList<String> arraylist = null;
		
		for(TaskInstance temp : tasks)
		{
			arraylist = (ArrayList<String>) temp.getVariable("inviati");
			
			if(arraylist==null)
			{
				System.out.println("VUOTOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
				return true;
			} 
			else 
			{
				System.out.println("PIENOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
				if(!(arraylist.contains(username)))
					tasksUser.add(temp);
			}
			
		}
		if(tasksUser.isEmpty())
			return false;
		else
			return true;
	}
	
	public boolean PooledTaskInstanceListForAllUserButMeNot(String username, String taskName)
	{
		System.out.println("AIUTOOOOOOOOOOOOOOOOOO TASKKKKKKKKKKKKKKK");
		List<TaskInstance> tasks= getAllPooledTaskInstanceList(taskName);
		List<TaskInstance> tasksUser = new ArrayList<TaskInstance>();
		
		ArrayList<String> arraylist = null;
		
		for(TaskInstance temp : tasks)
		{
			arraylist = (ArrayList<String>) temp.getVariable("inviati");
			
			if(arraylist==null)
			{
				System.out.println("VUOTOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
				return true;
			} 
			else 
			{
				System.out.println("PIENOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
				if(!(arraylist.contains(username)))
					tasksUser.add(temp);
			}
			
		}
		if(tasksUser.isEmpty())
			return false;
		else
			return true;
	}
	
	public List<TaskInstance> getPooledTaskInstanceListForAllUserButMeNot(String username, String taskName)
	{
		System.out.println("AIUTOOOOOOOOOOOOOOOOOO TASKKKKKKKKKKKKKKK");
		List<TaskInstance> tasks= getAllPooledTaskInstanceList(taskName);
		List<TaskInstance> tasksUser = new ArrayList<TaskInstance>();
		
		ArrayList<String> arraylist = null;
		
		for(TaskInstance temp : tasks)
		{
			arraylist = (ArrayList<String>) temp.getVariable("inviati");
			
			if(arraylist==null)
			{
				System.out.println("VUOTOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
			} 
			else 
			{
				System.out.println("PIENOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
				if(!(arraylist.contains(username)))
					tasksUser.add(temp);
			}
			
		}
		return tasksUser;
	}
	
	
	public int numberOfHighestPriorityTaskForSingle()	{
		return numberOfHighPriorityTask("taskForSingle");
	}
	
	public int getNumerOfAllTaskInstanceListForContadino(String username, String taskName, String contadino)
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
		return tasksUser.size();
		
	}
	
	
	public int getSizeForAllContadinoForTaskInstanceType(String username, String taskName)
	{
		// Primo filtro che restiuisce il numero di contadini per un tipo di  task e assegnato ad un utente
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
				if((nomeContadino != null) && !(partiteIva.contains(nomeContadino.getPartitaIva()))){
					contadini.add(nomeContadino);
					partiteIva.add(nomeContadino.getPartitaIva());
				}
			}
		}
		return contadini.size();
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
			account = (Account) temp.getVariable("currentAccount");
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

	private List<TaskInstance> taskInstanceSingleList(String taskFilter) {
		List<TaskInstance> tasks = new ArrayList<TaskInstance>();
		for (TaskInstance ti: taskInstanceList) {
			if (ti.getName().equals(taskFilter))
				tasks.add(ti);
		}
		return tasks;
	}

	public List<TaskInstance> getAllPooledTaskInstanceList(String... filters)
	{
		List<TaskInstance> tasks= new ArrayList<TaskInstance>();
		for(String taskFilter : filters)
			tasks.addAll(taskInstanceGroupList(taskFilter));
		return tasks;
	}
	private List<TaskInstance> taskInstanceGroupList(String taskFilter) {
		List<TaskInstance> tasks = new ArrayList<TaskInstance>();
		for (TaskInstance ti: pooledTaskInstanceList) {
			if (ti.getName().equals(taskFilter))
				tasks.add(ti);
		}
		return tasks;
	}
}
