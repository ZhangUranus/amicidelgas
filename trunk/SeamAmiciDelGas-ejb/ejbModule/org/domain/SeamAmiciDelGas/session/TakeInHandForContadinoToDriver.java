package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Itinerario;
import org.domain.SeamAmiciDelGas.entity.Ordine;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jbpm.taskmgmt.exe.TaskInstance;

@Name(value="takeInHandForContadinoToDriver") 
@Scope(ScopeType.SESSION)
public class TakeInHandForContadinoToDriver {

	public TakeInHandForContadinoToDriver(){}
	
	private String stringaResponsabileConsegna;
	
	@Logger
	private Log log;
	
	@In(value="filtraNotifica", create=true)
	private FiltraNotifica filtraNotifica;
	
	private InfoFeedback infoFeedbackResponsabile;
	
	//list di taskInstance da terminare per il responsabile corrente
	private List<TaskInstance> taskInstanceListForResponsabile;
	
	//list di task Instance in cui il contadino non � il responsabile di consegna
	private List<TaskInstance> tasksContadinoToResponsabileConsegna;
	
	private List<Account> responsabili;

	private Account currentResponsabile;
	
	private List<String> stringheResponsabiliConsegna;
	
	public void reset() {

		infoFeedbackResponsabile=null;
		tasksContadinoToResponsabileConsegna = new ArrayList<TaskInstance>();
		responsabili = new ArrayList<Account>();
		stringaResponsabileConsegna = null;
		stringheResponsabiliConsegna = new ArrayList<String>();
		currentResponsabile = null;
		log.info("\n\n******** RESET ***********\n\n");

	}
		
	public InfoFeedback getInfoFeedbackResponsabile() {
		if(infoFeedbackResponsabile==null)
			infoFeedbackResponsabile= new InfoFeedback("",3);
		return infoFeedbackResponsabile;
	}

		public List<String> getStringheResponsabiliConsegna() {
			//task assegnati al contadino che � il responsabile della consegna
			//tasksContadinoToCustomer = filtraNotifica.getAllSingleTaskInstanceList("fbResponsabileConsegnaToCustomer");
			//task assegnati dal contadino in cui non � il responsabile di consegna
			tasksContadinoToResponsabileConsegna = filtraNotifica.taskInstanceForContadino("fbContadinoToResponsabile");
			
			responsabili = new ArrayList<Account>();
			stringheResponsabiliConsegna = new ArrayList<String>();

			//addList(tasksContadinoToCustomer);
			
			//setto i responsabili
			addList(tasksContadinoToResponsabileConsegna);
			
			Collections.sort(stringheResponsabiliConsegna);
			return stringheResponsabiliConsegna;
		}
		
		private void addList(List<TaskInstance> taskInstanceList) {
			for (TaskInstance t2: taskInstanceList) {
				Account responsabile = (Account) t2.getVariable("responsabileConsegna");
				if (responsabile!=null) {
					if (!responsabili.contains(responsabile)) {
						responsabili.add(responsabile);
						stringheResponsabiliConsegna.add(responsabile.getUsername());
					}
				}
			}
		}
		

		public void setStringaResponsabileConsegna(String usernameResponsabile) {
			this.stringaResponsabileConsegna = usernameResponsabile;
			log.info("Responsabile "+stringaResponsabileConsegna);

			taskInstanceListForResponsabile = new ArrayList<TaskInstance>();
			
			for (TaskInstance t1: tasksContadinoToResponsabileConsegna) {
				Account responsabile = (Account) t1.getVariable("responsabileConsegna");
				if (responsabile.getUsername().equals(stringaResponsabileConsegna))
				{
					currentResponsabile = responsabile;
					taskInstanceListForResponsabile.add(t1);
				}
			}
			log.info("******** CONTADINO "+taskInstanceListForResponsabile.size());
		}

		

		public List<TaskInstance> getTasksContadinoToResponsabileConsegna() {
			return tasksContadinoToResponsabileConsegna;
		}

		public void setTasksContadinoToResponsabileConsegna(
				List<TaskInstance> tasksContadinoToResponsabileConsegna) {
			this.tasksContadinoToResponsabileConsegna = tasksContadinoToResponsabileConsegna;
		}


		public List<Account> getResponsabili() {
			return responsabili;
		}

		public void setResponsabili(List<Account> responsabili) {
			this.responsabili = responsabili;
		}

		public void setStringheResponsabiliConsegna(
				List<String> stringheResponsabiliConsegna) {
			this.stringheResponsabiliConsegna = stringheResponsabiliConsegna;
		}

		public String getStringaResponsabileConsegna() {
			return stringaResponsabileConsegna;
		}

		public List<TaskInstance> getTaskInstanceListForResponsabile() {
			return taskInstanceListForResponsabile;
		}

		public void setTaskInstanceListForResponsabile(
				List<TaskInstance> taskInstanceListForResponsabile) {
			this.taskInstanceListForResponsabile = taskInstanceListForResponsabile;
		}

		public void setInfoFeedbackResponsabile(InfoFeedback infoFeedbackResponsabile) {
			this.infoFeedbackResponsabile = infoFeedbackResponsabile;
		}

		public Account getCurrentResponsabile() {
			return currentResponsabile;
		}

		public void setCurrentResponsabile(Account currentResponsabile) {
			this.currentResponsabile = currentResponsabile;
		}

}