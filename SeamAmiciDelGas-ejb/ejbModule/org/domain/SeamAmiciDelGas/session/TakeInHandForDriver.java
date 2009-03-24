package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	
	private List<TaskInstance> tasksResponsabileToCustomer;
	
	private List<TaskInstance> tasksResponsabileToContadino;

	private Itinerario currentItinerario;
	
	private List<Itinerario> itinerari;

	private List<String> stringhe;
	
	public void reset() {

		hashTableContadini = new Hashtable<String, InfoFeedback>();
		hashTableCustomer = new Hashtable<String, InfoFeedback>();
		tasksResponsabileToCustomer = new ArrayList<TaskInstance>();
		tasksResponsabileToContadino  = new ArrayList<TaskInstance>();
		taskInstanceCustomerForItinerario = new ArrayList<TaskInstance>();
		taskInstanceContadinoForItinerario = new ArrayList<TaskInstance>();
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

	
	//carico i cybercontadini ai quali è associato almeno un ordine
	public List<Cybercontadino> listCybercontadini() {
		List<Cybercontadino> contadini = new ArrayList<Cybercontadino>();
		if(currentItinerario!=null && taskInstanceContadinoForItinerario.size()!=0) {
			Set<Cybercontadino> setContadini = currentItinerario.getCybercontadinos();
			Iterator<Cybercontadino> iterator = setContadini.iterator();
			Cybercontadino c;
			Hashtable<String,Boolean> hashTable;
			while (iterator.hasNext()) {
				c = iterator.next();
				for (TaskInstance t1: taskInstanceContadinoForItinerario) {
						hashTable = (Hashtable<String,Boolean>) t1.getVariable("booleanFeedbackContadiniToResponsabile");
						Boolean voto = hashTable.get(c.getAccount().getUsername());
						if (voto!=null) {
							contadini.add(c);
							break;
						}
				}
			}
		}
		return contadini;
	}
	
	public List<Account> listCustomer() {
		List<Account> customerList = new ArrayList<Account>();
		if(currentItinerario!=null) {
			for (TaskInstance t1: taskInstanceCustomerForItinerario) {
				Account currAccount = (Account) t1.getVariable("customer");
				if (!customerList.contains(currAccount))
					customerList.add(currAccount);
			}
		}
		return customerList;
	}

		public Hashtable<String, InfoFeedback> getHashTableContadini() {
			return hashTableContadini;
		}
		
		public Hashtable<String, InfoFeedback> getHashTableCustomer() {
			return hashTableCustomer;
		}

		public List<String> getStringhe() {
			tasksResponsabileToCustomer = filtraNotifica.getAllSingleTaskInstanceListByFilters("fbResponsabileConsegnaToCustomer");
			tasksResponsabileToContadino = filtraNotifica.getAllSingleTaskInstanceListByFilters("fbResponsabileConsegnaToContadino");
			
			itinerari = new ArrayList<Itinerario>();
			stringhe = new ArrayList<String>();

			addList(tasksResponsabileToCustomer);
			addList(tasksResponsabileToContadino);
			
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
			
			taskInstanceCustomerForItinerario = new ArrayList<TaskInstance>();
			taskInstanceContadinoForItinerario = new ArrayList<TaskInstance>();
			hashTableContadini = new Hashtable<String, InfoFeedback>();
			hashTableCustomer = new Hashtable<String, InfoFeedback>();
			
			for (TaskInstance t1: tasksResponsabileToCustomer) {
				Itinerario it = (Itinerario) t1.getVariable("itinerario");
				if (it.getIditinerario().equals(new Integer(idItinerario)))
				{
					taskInstanceCustomerForItinerario.add(t1);
					currentItinerario = it;
				}
			}
			for (TaskInstance t2: tasksResponsabileToContadino) {
				Itinerario it = (Itinerario) t2.getVariable("itinerario");
				if (it.getIditinerario().equals(new Integer(idItinerario)))
				{
					taskInstanceContadinoForItinerario.add(t2);
					currentItinerario = it;
				}
			}
			log.info("******* current itinerario = " +currentItinerario.getIditinerario());
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