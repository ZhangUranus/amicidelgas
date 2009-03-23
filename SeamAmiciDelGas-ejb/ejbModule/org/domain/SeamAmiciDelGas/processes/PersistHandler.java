package org.domain.SeamAmiciDelGas.processes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.domain.SeamAmiciDelGas.crud.OrdineHome;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Itinerario;
import org.domain.SeamAmiciDelGas.entity.Ordine;
import org.domain.SeamAmiciDelGas.session.FiltraNotifica;
import org.domain.SeamAmiciDelGas.session.GestioneFeedback;
import org.domain.SeamAmiciDelGas.session.ItemQuantita;
import org.domain.SeamAmiciDelGas.session.MyOrdine;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.contexts.Context;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.log.Log;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.exe.TaskInstance;


public class PersistHandler implements ActionHandler{

	private GestioneFeedback gestioneFeedback;

	//private FiltraNotifica filtraNotifica;
	
	//private Itinerario itinerario;
	
	//private Account driverAccount;
	
	//@In(value="ordineHome",create=true)
	//private OrdineHome ordineHome;
	
	//private Ordine ordine;
	
	/**
	 * 
	 */
	String taskNameOrdine;
	String usernameFrom;
	String usernameTo;
	String idCase;
	
	private static final long serialVersionUID = 7802074922249313203L;

	public void execute(ExecutionContext executionContext) throws Exception 
	{
		Context businessContext = Contexts.getBusinessProcessContext();
		gestioneFeedback = (GestioneFeedback) businessContext.get("gestioneFeedback");
		Collection<TaskInstance> col  = executionContext.getTaskMgmtInstance().getTaskInstances();
		List<TaskInstance> taskInstanceList = new ArrayList<TaskInstance>();
		taskInstanceList.addAll(col);
		int idCaseInt = Integer.parseInt(idCase);
		switch(idCaseInt) {
			case 1 : //customer to contadino
				for (TaskInstance taskInstance : taskInstanceList) {
					closeTaskInstanceCustomerToContadino(taskInstance);
				}
				break;
			case 2 : //customer to responsabile consegna
				for (TaskInstance taskInstance : taskInstanceList) {
					closeTaskInstance(taskInstance);
				}
				break;
			case 3 : //responsabile consegna to customer
				for (TaskInstance taskInstance : taskInstanceList) {
					closeTaskInstance(taskInstance);
				}
				break;
			case 4 : //contadino to driver
				for (TaskInstance taskInstance : taskInstanceList) {
					closeTaskInstanceContadinoToResponsabile(taskInstance);
				}
				break;
			case 5 : //driver to contadino
				for (TaskInstance taskInstance : taskInstanceList) {
					closeTaskInstanceResponsabileToContadino(taskInstance);
				}
				break;
	
		}
	}
	
	private void closeTaskInstance(TaskInstance taskInstance) {
		Context businessContext = Contexts.getBusinessProcessContext();
		Account accountFrom = (Account) businessContext.get(usernameFrom);
		Account accountTo = (Account) businessContext.get(usernameTo);
		if(taskInstance.getName().equals(taskNameOrdine) && taskInstance.isOpen())
		{
			Ordine ordine = (Ordine) taskInstance.getVariable("ordine");

			gestioneFeedback.assegnaFeedbackFromToDefault(accountFrom.getUsername(), accountTo.getUsername(), ordine);
			taskInstance.setSignalling(false);
			taskInstance.cancel();
		}
	}
	
	private void closeTaskInstanceCustomerToContadino(TaskInstance taskInstance) {
		Context businessContext = Contexts.getBusinessProcessContext();
		Account accountFrom = (Account) businessContext.get(usernameFrom);
		if(taskInstance.getName().equals(taskNameOrdine) && taskInstance.isOpen())
		{
			Ordine ordine = (Ordine) taskInstance.getVariable("ordine");
			Itinerario itinerario = (Itinerario) taskInstance.getVariable("itinerario");
			List<Cybercontadino> contadinoList = new ArrayList<Cybercontadino>();
			contadinoList.addAll(itinerario.getCybercontadinos());
			for (Cybercontadino cybercontadino : contadinoList) {
				gestioneFeedback.assegnaFeedbackFromToDefault(accountFrom.getUsername(), cybercontadino.getAccount().getUsername(), ordine);
			}
			taskInstance.setSignalling(false);
			taskInstance.cancel();
		}
	}
	
	private void closeTaskInstanceContadinoToResponsabile(TaskInstance taskInstance) {
		Context businessContext = Contexts.getBusinessProcessContext();
		Account accountTo = (Account) businessContext.get(usernameTo);
		if(taskInstance.getName().equals(taskNameOrdine) && taskInstance.isOpen())
		{
			Ordine ordine = (Ordine) taskInstance.getVariable("ordine");
			Itinerario itinerario = (Itinerario) taskInstance.getVariable("itinerario");
			List<Cybercontadino> contadinoList = new ArrayList<Cybercontadino>();
			contadinoList.addAll(itinerario.getCybercontadinos());
			for (Cybercontadino cybercontadino : contadinoList) {
				gestioneFeedback.assegnaFeedbackFromToDefault(cybercontadino.getAccount().getUsername(), accountTo.getUsername(), ordine);
			}
			taskInstance.setSignalling(false);
			taskInstance.cancel();
		}
	}
	
	private void closeTaskInstanceResponsabileToContadino(TaskInstance taskInstance) {
		Context businessContext = Contexts.getBusinessProcessContext();
		Account accountFrom = (Account) businessContext.get(usernameFrom);
		if(taskInstance.getName().equals(taskNameOrdine) && taskInstance.isOpen())
		{
			Ordine ordine = (Ordine) taskInstance.getVariable("ordine");
			Itinerario itinerario = (Itinerario) taskInstance.getVariable("itinerario");
			List<Cybercontadino> contadinoList = new ArrayList<Cybercontadino>();
			contadinoList.addAll(itinerario.getCybercontadinos());
			for (Cybercontadino cybercontadino : contadinoList) {
				gestioneFeedback.assegnaFeedbackFromToDefault(accountFrom.getUsername(), cybercontadino.getAccount().getUsername(), ordine);
			}
			taskInstance.setSignalling(false);
			taskInstance.cancel();
		}
	}
	
	
	public String getTaskNameOrdine() {
		return taskNameOrdine;
	}

	public void setTaskNameOrdine(String taskNameOrdine) {
		this.taskNameOrdine = taskNameOrdine;
	}

	public String getUsernameFrom() {
		return usernameFrom;
	}

	public void setUsernameFrom(String usernameFrom) {
		this.usernameFrom = usernameFrom;
	}

	public String getUsernameTo() {
		return usernameTo;
	}

	public void setUsernameTo(String usernameTo) {
		this.usernameTo = usernameTo;
	}

	public String getIdCase() {
		return idCase;
	}

	public void setIdCase(String idCase) {
		this.idCase = idCase;
	}
}
