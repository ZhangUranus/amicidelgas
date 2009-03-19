package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Articolo;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Itinerario;
import org.domain.SeamAmiciDelGas.entity.Ordine;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

import org.jboss.seam.ScopeType;
import org.jbpm.taskmgmt.exe.TaskInstance;

@Name(value="takeInHandForDriver") 
@Scope(ScopeType.SESSION)
public class TakeInHandForDriver {

	public TakeInHandForDriver(){}
	
	private String stringa;
	
	@Logger
	private Log log;
	
	@In(value="filtraNotifica", create=true)
	private FiltraNotifica filtraNotifica;
	
	//hashtable per i contadini
	private Hashtable<String, InfoFeedback> hashTableContadini = new Hashtable<String, InfoFeedback>();
	
	//hashtable per i customer
	private Hashtable<String, InfoFeedback> hashTableCustomer = new Hashtable<String, InfoFeedback>();
	
	private List<TaskInstance> taskInstanceCustomerForItinerario;
	
	private List<TaskInstance> taskInstanceContadinoForItinerario;
	
	private TaskInstance currentTaskResponsabileToCustomer;
	
	private TaskInstance currentTaskResponsabileToContadino;
	
	private List<TaskInstance> tasksResponsabileToCustomer;
	
	private List<TaskInstance> tasksResponsabileToContadino;

	private Itinerario currentItinerario;
	
	private List<Itinerario> itinerari;

	private List<String> stringhe;
	
	public void reset() {

		hashTableContadini = new Hashtable<String, InfoFeedback>();
		hashTableCustomer = new Hashtable<String, InfoFeedback>();
		currentTaskResponsabileToCustomer=null;
		currentTaskResponsabileToContadino=null;
		tasksResponsabileToCustomer = new ArrayList<TaskInstance>();
		tasksResponsabileToContadino  = new ArrayList<TaskInstance>();
		currentItinerario=null;
		itinerari = new ArrayList<Itinerario>();
		stringa = null;
		stringhe = new ArrayList<String>();
		log.info("\n\n******** RESET ***********\n\n");

	}
	
	public InfoFeedback getInfoFeedbackContadini(String username){
		if(username==null)
			return null;
		if(hashTableContadini.get(username)==null)
			hashTableContadini.put(username, new InfoFeedback("",3));
		return hashTableContadini.get(username);
	}
	
	public InfoFeedback getInfoFeedbackCustomer(String username){
		if(username==null)
			return null;
		if(hashTableCustomer.get(username)==null)
			hashTableCustomer.put(username, new InfoFeedback("",3));
		return hashTableCustomer.get(username);
	}

	
	
	public List<Cybercontadino> listCybercontadini() {
		List<Cybercontadino> contadini = new ArrayList<Cybercontadino>();
		if(currentItinerario!=null) {
			log.info("***** SIZEEEE"+currentItinerario.getCybercontadinos().size());
			contadini.addAll(currentItinerario.getCybercontadinos());
		}
		return contadini;
	}
	
	public List<Account> listCustomer() {
		List<Account> customerList = new ArrayList<Account>();
		if(currentItinerario!=null) {
			for (TaskInstance t1: tasksResponsabileToCustomer) {
				Itinerario it = (Itinerario) t1.getVariable("itinerario");
				Account currAccount = (Account) t1.getVariable("customer");
				if (currentItinerario.equals(it) && !customerList.contains(currAccount))
					customerList.add(currAccount);
			}
		}
		return customerList;
	}

	
	public Account responsabileConsegna() {
		if (currentTaskResponsabileToContadino!=null)
			return (Account) currentTaskResponsabileToContadino.getVariable("responsabileConsegna");
		return null;
	}

		public Hashtable<String, InfoFeedback> getHashTableContadini() {
			return hashTableContadini;
		}
		
		public Hashtable<String, InfoFeedback> getHashTableCustomer() {
			return hashTableCustomer;
		}

		public List<String> getStringhe() {
			tasksResponsabileToCustomer = filtraNotifica.getAllSingleTaskInstanceList("fbResponsabileConsegnaToCustomer");
			tasksResponsabileToContadino = filtraNotifica.getAllSingleTaskInstanceList("fbResponsabileConsegnaToContadino");
			
			itinerari = new ArrayList<Itinerario>();
			stringhe = new ArrayList<String>();

			addList(tasksResponsabileToCustomer);
			addList(tasksResponsabileToContadino);
			
			log.info("****TASKS1**"+tasksResponsabileToCustomer.size());
			log.info("****TASKS2**"+tasksResponsabileToContadino.size());
			
			Collections.sort(stringhe);
			return stringhe;
		}
		
		private void addList(List<TaskInstance> taskInstanceList) {
				for (TaskInstance t2: taskInstanceList) {
				Itinerario it = (Itinerario) t2.getVariable("itinerario");
				if (!itinerari.contains(it)) {
					itinerari.add(it);
					stringhe.add(""+it.getIditinerario());
				}
			}
		}
		

		public void setStringhe(List<String> stringhe) {
			this.stringhe = stringhe;
		}

		public String getStringa() {
			return stringa;
		}

		public void setStringa(String idItinerario) {
			this.stringa = idItinerario;
			log.info("Codice Itinerario "+idItinerario);
			
			currentTaskResponsabileToCustomer = null;
			currentTaskResponsabileToContadino = null;
			taskInstanceCustomerForItinerario = new ArrayList<TaskInstance>();
			taskInstanceContadinoForItinerario = new ArrayList<TaskInstance>();

			for (TaskInstance t1: tasksResponsabileToCustomer) {
				Itinerario it = (Itinerario) t1.getVariable("itinerario");
				if (it.getIditinerario().equals(new Integer(idItinerario)))
				{
					taskInstanceCustomerForItinerario.add(t1);
					currentTaskResponsabileToCustomer = t1;
					currentItinerario = it;
				}
			}
			for (TaskInstance t2: tasksResponsabileToContadino) {
				Itinerario it = (Itinerario) t2.getVariable("itinerario");
				if (it.getIditinerario().equals(new Integer(idItinerario)))
				{
					taskInstanceContadinoForItinerario.add(t2);
					currentTaskResponsabileToContadino = t2;
					currentItinerario = it;
				}
			}
			log.info("******* current itinerario = " +currentItinerario.getIditinerario());
		}

		public TaskInstance getCurrentTaskResponsabileToCustomer() {
			return currentTaskResponsabileToCustomer;
		}

		public void setCurrentTaskResponsabileToCustomer(
				TaskInstance currentTaskResponsabileToCustomer) {
			this.currentTaskResponsabileToCustomer = currentTaskResponsabileToCustomer;
		}

		public TaskInstance getCurrentTaskResponsabileToContadino() {
			return currentTaskResponsabileToContadino;
		}

		public void setCurrentTaskResponsabileToContadino(
				TaskInstance currentTaskResponsabileToContadino) {
			this.currentTaskResponsabileToContadino = currentTaskResponsabileToContadino;
		}

		public List<TaskInstance> getTasksResponsabileToCustomer() {
			return tasksResponsabileToCustomer;
		}

		public void setTasksResponsabileToCustomer(
				List<TaskInstance> tasksResponsabileToCustomer) {
			this.tasksResponsabileToCustomer = tasksResponsabileToCustomer;
		}

		public List<TaskInstance> getTasksResponsabileToContadino() {
			return tasksResponsabileToContadino;
		}

		public void setTasksResponsabileToContadino(
				List<TaskInstance> tasksResponsabileToContadino) {
			this.tasksResponsabileToContadino = tasksResponsabileToContadino;
		}

		public Itinerario getCurrentItinerario() {
			return currentItinerario;
		}

		public void setCurrentItinerario(Itinerario currentItinerario) {
			this.currentItinerario = currentItinerario;
		}

		public List<TaskInstance> getTaskInstanceCustomerForItinerario() {
			return taskInstanceCustomerForItinerario;
		}

		public void setTaskInstanceCustomerForItinerario(
				List<TaskInstance> taskInstanceCustomerForItinerario) {
			this.taskInstanceCustomerForItinerario = taskInstanceCustomerForItinerario;
		}

		public List<TaskInstance> getTaskInstanceContadinoForItinerario() {
			return taskInstanceContadinoForItinerario;
		}

		public void setTaskInstanceContadinoForItinerario(
				List<TaskInstance> taskInstanceContadinoForItinerario) {
			this.taskInstanceContadinoForItinerario = taskInstanceContadinoForItinerario;
		}
	
}