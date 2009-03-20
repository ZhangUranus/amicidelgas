package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;


import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Ordine;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;

import org.jboss.seam.ScopeType;
import org.jbpm.taskmgmt.exe.TaskInstance;

@Name(value="takeInHandForContadinoToCustomer") 
@Scope(ScopeType.SESSION)
public class TakeInHandForContadinoToCustomer {

	public TakeInHandForContadinoToCustomer(){}
	
	private String stringaCustomer;
	
	@Logger
	private Log log;
	
	@In(value="filtraNotifica", create=true)
	private FiltraNotifica filtraNotifica;
	
	private Hashtable<Integer, InfoFeedback> hashTable = new Hashtable<Integer, InfoFeedback>();

	private List<TaskInstance> taskInstanceForCurrentAccount;
	
	private List<TaskInstance> tasksContadinoToCustomer;
	
	private List<Account> listCustomer;
	
	@In private Credentials credentials;
	
	private List<Ordine> ordiniForCustomer;
	
	private Account currentAccount;
	
	private InfoFeedback infoFeedbackCustomer;
	
	private List<String> stringhe;
	
	public void reset() {
		
		tasksContadinoToCustomer = new ArrayList<TaskInstance>();
		stringaCustomer = null;
		stringhe = new ArrayList<String>();
		log.info("\n\n******** RESET ***********\n\n");
	}
	
	public InfoFeedback getInfoFeedbackForOrdine(Integer idOrdine){
		if(idOrdine==null)
			return null;
		if(hashTable.get(idOrdine)==null)
			hashTable.put(idOrdine, new InfoFeedback("",3));
		return hashTable.get(idOrdine);
	}


		
		public List<String> getStringhe() {
			//prendo tutti i task in cui il contadino è responsabile della consegna
			hashTable = new Hashtable<Integer,InfoFeedback>();
			tasksContadinoToCustomer = filtraNotifica.getAllSingleTaskInstanceListByFilters("fbResponsabileConsegnaToCustomer");
			
			//lista di customer a cui assegnare i feedback
			listCustomer = new ArrayList<Account>();
			stringhe = new ArrayList<String>();

			addList(tasksContadinoToCustomer);
			
			log.info("****TASKS1**"+tasksContadinoToCustomer.size());
			
			Collections.sort(stringhe);
			return stringhe;
		}
		
		private void addList(List<TaskInstance> taskInstanceList) {
			//filtro le istanze
			for (TaskInstance t2: taskInstanceList) {
			Account account = (Account) t2.getVariable("customer");
			if (!listCustomer.contains(account)) {
				stringhe.add(account.getUsername());
			}
		}
	}

		public void setStringhe(List<String> stringhe) {
			this.stringhe = stringhe;
		}


		public void setStringaCustomer(String usernameCustomer) {
			
			this.stringaCustomer = usernameCustomer;
			currentAccount = null;
			taskInstanceForCurrentAccount = new ArrayList<TaskInstance>();
			ordiniForCustomer = new ArrayList<Ordine>();
			for (TaskInstance t1: tasksContadinoToCustomer) {
				Account account = (Account) t1.getVariable("customer");
				if (account.getUsername().equals(stringaCustomer))
				{
					currentAccount = account;
					taskInstanceForCurrentAccount.add(t1);
					ordiniForCustomer.add((Ordine) t1.getVariable("ordine"));
				}

			}
			
			log.info("******* current account = " +currentAccount.getUsername());
		}
	

		
		public List<Account> getListCustomer() {
			return listCustomer;
		}

		public void setListCustomer(List<Account> listCustomer) {
			this.listCustomer = listCustomer;
		}

		public Account getCurrentAccount() {
			return currentAccount;
		}

		public void setCurrentAccount(Account currentAccount) {
			this.currentAccount = currentAccount;
		}

		public List<TaskInstance> getTaskInstanceForCurrentAccount() {
			return taskInstanceForCurrentAccount;
		}

		public void setTaskInstanceForCurrentAccount(
				List<TaskInstance> taskInstanceForCurrentAccount) {
			this.taskInstanceForCurrentAccount = taskInstanceForCurrentAccount;
		}

		public InfoFeedback getInfoFeedbackCustomer() {
			if(infoFeedbackCustomer==null)
				infoFeedbackCustomer= new InfoFeedback("",3);
			return infoFeedbackCustomer;
		}

		public void setInfoFeedbackCustomer(InfoFeedback infoFeedbackCustomer) {
			this.infoFeedbackCustomer = infoFeedbackCustomer;
		}

		public String getStringaCustomer() {
			return stringaCustomer;
		}

		public List<Ordine> getOrdiniForCustomer() {
			return ordiniForCustomer;
		}

		public void setOrdiniForCustomer(List<Ordine> ordiniForCustomer) {
			this.ordiniForCustomer = ordiniForCustomer;
		}

		public Hashtable<Integer, InfoFeedback> getHashTable() {
			return hashTable;
		}

		public void setHashTable(Hashtable<Integer, InfoFeedback> hashTable) {
			this.hashTable = hashTable;
		}

}