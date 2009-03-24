package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

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

@Name(value="takeInHandForCustomer") 
@Scope(ScopeType.SESSION)
public class TakeInHandForCustomer {

	public TakeInHandForCustomer(){}
	
	private String stringa;
	
	@Logger
	private Log log;
	
	@In(value="filtraNotifica", create=true)
	private FiltraNotifica filtraNotifica;
	
	private Hashtable<String, InfoFeedback> hashTable = new Hashtable<String, InfoFeedback>();
	
	private TaskInstance currentTaskCustomerToContadino;
	
	private TaskInstance currentTaskCustomerToResponsabileConsegna;
	
	private List<TaskInstance> tasksCustomerToContadino;
	
	private List<TaskInstance> tasksCustomerToResponsabileConsegna;
	
	private InfoFeedback infoFeedbackResponsabile;
	
	private Ordine currentOrdine;
	
	private List<Ordine> ordini;
	
	private long id;
	
	private List<String> stringhe;
	
	public void reset() {
		
		hashTable = new Hashtable<String, InfoFeedback>();
		currentTaskCustomerToContadino=null;
		currentTaskCustomerToResponsabileConsegna=null;
		tasksCustomerToContadino = new ArrayList<TaskInstance>();
		tasksCustomerToResponsabileConsegna  = new ArrayList<TaskInstance>();
		currentOrdine=null;
		infoFeedbackResponsabile=null;
		ordini = new ArrayList<Ordine>();
		stringa = null;
		stringhe = new ArrayList<String>();
		log.info("\n\n******** RESET ***********\n\n");
	}
	
	public InfoFeedback getInfoFeedbackContadini(String username){
		if(username==null)
			return null;
		if(hashTable.get(username)==null)
			hashTable.put(username, new InfoFeedback("",3));
		return hashTable.get(username);
	}
	
	public InfoFeedback getInfoFeedbackResponsabile() {
		if(infoFeedbackResponsabile==null)
			infoFeedbackResponsabile= new InfoFeedback("",3);
		return infoFeedbackResponsabile;
	}
	
	
	public List<Cybercontadino> listCybercontadiniEffettivi() {
		List<Cybercontadino> contadiniEffettivi = new ArrayList<Cybercontadino>();
		if(currentTaskCustomerToContadino!=null)
		{
			MyOrdine myOrdine = (MyOrdine) currentTaskCustomerToContadino.getVariable("myOrdine");
				for (ItemQuantita iq: myOrdine.getItemQuantita()) {
					Cybercontadino contadino = iq.getCybercontadino();
					if(!contadiniEffettivi.contains(contadino))
						contadiniEffettivi.add(contadino);
				}
		}
		return contadiniEffettivi;
	}

	
	public Account responsabileConsegna() {
		if (currentTaskCustomerToResponsabileConsegna!=null)
			return (Account) currentTaskCustomerToResponsabileConsegna.getVariable("responsabileConsegna");
		return null;
	}

		public Hashtable<String, InfoFeedback> getHashTable() {
			return hashTable;
		}

		public List<String> getStringhe() {
			tasksCustomerToContadino = filtraNotifica.getAllSingleTaskInstanceListByFilters("fbCustomerToContadino");
			tasksCustomerToResponsabileConsegna = filtraNotifica.getAllSingleTaskInstanceListByFilters("fbCustomerToResponsabileConsegna");
			
			hashTable = new Hashtable<String, InfoFeedback>();
			
			ordini = new ArrayList<Ordine>();
			stringhe = new ArrayList<String>();

			addList(tasksCustomerToContadino);
			addList(tasksCustomerToResponsabileConsegna);
			
			Collections.sort(stringhe);
			return stringhe;
		}
		
		private void addList(List<TaskInstance> taskInstanceList) {
			for (TaskInstance t2: taskInstanceList) {
			Ordine ord = (Ordine) t2.getVariable("ordine");
			if (!ordini.contains(ord)) {
				ordini.add(ord);
				stringhe.add(""+ord.getIdordine());
			}
		}
	}

		public void setStringhe(List<String> stringhe) {
			this.stringhe = stringhe;
		}

		public String getStringa() {
			return stringa;
		}

		public void setStringa(String idOrdine) {
			
			this.stringa = idOrdine;
			currentOrdine = null;
			currentTaskCustomerToContadino = null;
			currentTaskCustomerToResponsabileConsegna = null;
			infoFeedbackResponsabile = null;
			
			for (TaskInstance t1: tasksCustomerToContadino) {
				Ordine ordine = (Ordine) t1.getVariable("ordine");
				if (ordine.getIdordine().equals(new Integer(idOrdine)))
				{
					currentTaskCustomerToContadino = t1;
					currentOrdine = ordine;
				}
				
			}
			for (TaskInstance t2: tasksCustomerToResponsabileConsegna) {
				Ordine ordine = (Ordine) t2.getVariable("ordine");
				if (ordine.getIdordine().equals(new Integer(idOrdine)))
				{
					currentTaskCustomerToResponsabileConsegna = t2;
					currentOrdine = ordine;
				}
			}
		}

		public List<Articolo> getArticoloForCurrentOrdine() {
			List<Articolo> articolos = new ArrayList<Articolo>();
			if(currentOrdine!=null)
				articolos.addAll(currentOrdine.getArticolos());
			return articolos;
		}
		

		public Ordine getCurrentOrdine() {
			return currentOrdine;
		}

		public void setCurrentOrdine(Ordine currentOrdine) {
			this.currentOrdine = currentOrdine;
		}

		public TaskInstance getCurrentTaskCustomerToContadino() {
			return currentTaskCustomerToContadino;
		}

		public void setCurrentTaskCustomerToContadino(
				TaskInstance currentTaskCustomerToContadino) {
			this.currentTaskCustomerToContadino = currentTaskCustomerToContadino;
		}

		public TaskInstance getCurrentTaskCustomerToResponsabileConsegna() {
			return currentTaskCustomerToResponsabileConsegna;
		}

		public void setCurrentTaskCustomerToResponsabileConsegna(
				TaskInstance currentTaskCustomerToResponsabileConsegna) {
			this.currentTaskCustomerToResponsabileConsegna = currentTaskCustomerToResponsabileConsegna;
		}

}