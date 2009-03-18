package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Ordine;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

import org.jboss.seam.ScopeType;
import org.jbpm.taskmgmt.exe.TaskInstance;

@Name(value="takeInHandContadino") 
@Scope(ScopeType.SESSION)
public class TakeInHandContadino {

	public TakeInHandContadino(){}
	
	private String stringa;
	
	@Logger
	private Log log;
	
	@In(value="filtraNotifica", create=true)
	private FiltraNotifica filtraNotifica;
	
	private Hashtable<String, InfoFeedback> hashTable = new Hashtable<String, InfoFeedback>();
	
	private TaskInstance currentTask1;
	
	private TaskInstance currentTask2;
	
	private List<TaskInstance> tasks1;
	
	private List<TaskInstance> tasks2;
	
	private List<Ordine> ordini;
	
	private long id;
	
	private List<String> stringhe;
	
	public void reset() {
		hashTable = new Hashtable<String, InfoFeedback>();
	}
	
	public InfoFeedback getInfoFeedback(String username){
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
	
	
	
	public List<Cybercontadino> listCybercontadiniEffettivi1() {
		List<Cybercontadino> contadiniEffettivi = new ArrayList<Cybercontadino>();
		if(currentTask1!=null)
		{
			MyOrdine myOrdine = (MyOrdine) currentTask1.getVariable("myOrdine");
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
	
	
	public List<Cybercontadino> listCybercontadiniEffettivi2() {
		List<Cybercontadino> contadiniEffettivi = new ArrayList<Cybercontadino>();
		if(currentTask2!=null)
		{
			MyOrdine myOrdine = (MyOrdine) currentTask2.getVariable("myOrdine");
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
	
		public class InfoFeedback {
		
		public InfoFeedback() {}
		
		public InfoFeedback(String c, int f) {
			comment = c; feedback = f;
		}

		private String comment;
		private int feedback;
		
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public int getFeedback() {
			return feedback;
		}
		public void setFeedback(int feedback) {
			this.feedback = feedback;
		}
	}

		public Hashtable<String, InfoFeedback> getHashTable() {
			return hashTable;
		}

		public List<String> getStringhe() {
			tasks1 = filtraNotifica.getAllSingleTaskInstanceList("fbCustomerToContadino");
			tasks2 = filtraNotifica.getAllSingleTaskInstanceList("fbCustomerToResponsabileConsegna");
			ordini = new ArrayList<Ordine>();
			stringhe = new ArrayList<String>();
			for (TaskInstance t1: tasks1) {
				Ordine ordine = (Ordine) t1.getVariable("ordine");
				if (!ordini.contains(ordine)) {
					ordini.add(ordine);
					stringhe.add(""+ordine.getIdordine());
				}
			}
			for (TaskInstance t2: tasks2) {
				Ordine ordine = (Ordine) t2.getVariable("ordine");
				if (!ordini.contains(ordine)) {
					ordini.add(ordine);
					stringhe.add(""+ordine.getIdordine());
				}
			}
			log.info("****TASKS1**"+tasks1.size());
			log.info("****TASKS2**"+tasks2.size());
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
			int idOrdine = Integer.parseInt(stringa);
			for (TaskInstance t1: tasks1) {
				Ordine o = (Ordine) t1.getVariable("ordine");
				if (o.getIdordine()==idOrdine)
					currentTask1 = t1;
			}
			for (TaskInstance t2: tasks2) {
				Ordine o = (Ordine) t2.getVariable("ordine");
				if (o.getIdordine()==idOrdine)
					currentTask2 = t2;
			}
		}

		public TaskInstance getCurrentTask1() {
			return currentTask1;
		}

		public void setCurrentTask1(TaskInstance currentTask1) {
			this.currentTask1 = currentTask1;
		}

		public TaskInstance getCurrentTask2() {
			return currentTask2;
		}

		public void setCurrentTask2(TaskInstance currentTask2) {
			this.currentTask2 = currentTask2;
		}

}