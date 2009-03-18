package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Articolo;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
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
		if(username==null){
			InfoFeedback feed= new InfoFeedback();
			feed.setComment("");
			feed.setFeedback(3);
			hashTable.put(username, feed);
			return feed;
		}
		InfoFeedback feed = hashTable.get(username);
		if(feed==null){
			feed= new InfoFeedback();
			feed.setComment("");
			feed.setFeedback(3);
			hashTable.put(username, feed);
		}
		return feed;
	}
	
	public InfoFeedback getInfoFeedbackResponsabile() {
		if(infoFeedbackResponsabile==null){
			infoFeedbackResponsabile= new InfoFeedback();
			infoFeedbackResponsabile.setComment("");
			infoFeedbackResponsabile.setFeedback(3);
		}
		return infoFeedbackResponsabile;
	}
	
	
	public List<Cybercontadino> listCybercontadiniEffettivi() {
		List<Cybercontadino> contadiniEffettivi = new ArrayList<Cybercontadino>();
		if(currentTaskCustomerToContadino!=null)
		{
			MyOrdine myOrdine = (MyOrdine) currentTaskCustomerToContadino.getVariable("myOrdine");
				for (ItemQuantita iq: myOrdine.getItemQuantita()) {
					Cybercontadino contadino = iq.getCybercontadino();
					boolean isPresent = false;
					for(Cybercontadino effettivo : contadiniEffettivi) {
						if(effettivo.getPartitaIva().equals(contadino.getPartitaIva()))
						{
							isPresent = true;
							break;
						}
					}
					if(!isPresent)
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
			tasksCustomerToContadino = filtraNotifica.getAllSingleTaskInstanceList("fbCustomerToContadino");
			tasksCustomerToResponsabileConsegna = filtraNotifica.getAllSingleTaskInstanceList("fbCustomerToResponsabileConsegna");
			ordini = new ArrayList<Ordine>();
			stringhe = new ArrayList<String>();
			for (TaskInstance t1: tasksCustomerToContadino) {
				Ordine ordine = (Ordine) t1.getVariable("ordine");
				if (!ordini.contains(ordine)) {
					ordini.add(ordine);
					stringhe.add(""+ordine.getIdordine());
				}
			}
			for (TaskInstance t2: tasksCustomerToResponsabileConsegna) {
				Ordine ordine = (Ordine) t2.getVariable("ordine");
				if (!ordini.contains(ordine)) {
					ordini.add(ordine);
					stringhe.add(""+ordine.getIdordine());
				}
			}
			log.info("****TASKS1**"+tasksCustomerToContadino.size());
			log.info("****TASKS2**"+tasksCustomerToResponsabileConsegna.size());
			return stringhe;
		}

		public void setStringhe(List<String> stringhe) {
			this.stringhe = stringhe;
		}

		public String getStringa() {
			return stringa;
		}

		public void setStringa(String stringa) {
			this.stringa = stringa;
			log.info("Codice Ordine"+stringa);
			Ordine o;
			currentOrdine = null;
			currentTaskCustomerToContadino = null;
			currentTaskCustomerToResponsabileConsegna = null;
			int idOrdine = Integer.parseInt(stringa);
			for (TaskInstance t1: tasksCustomerToContadino) {
				o = (Ordine) t1.getVariable("ordine");
				if (o.getIdordine()==idOrdine)
				{
					currentTaskCustomerToContadino = t1;
					currentOrdine = o;
				}
				
			}
			for (TaskInstance t2: tasksCustomerToResponsabileConsegna) {
				o = (Ordine) t2.getVariable("ordine");
				if (o.getIdordine()==idOrdine)
				{
					currentTaskCustomerToResponsabileConsegna = t2;
					currentOrdine = o;
				}
			}
			log.info("******* current ordine = " +currentOrdine.getDataConclusione().toString());
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