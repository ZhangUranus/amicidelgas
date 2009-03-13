package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.domain.SeamAmiciDelGas.crud.AccountList;
import org.domain.SeamAmiciDelGas.crud.OrdineList;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Articolo;
import org.domain.SeamAmiciDelGas.entity.Ordine;
import org.domain.SeamAmiciDelGas.processes.OrderProcessing;


@Name("ordineBean")
@Scope(ScopeType.SESSION)
public class OrdineBean {

	@Logger
	private Log log;

	@In private Credentials credentials;
	
	@In(value="ordineList",create=true)
	private OrdineList ordineList;
	
	@In(value="filtraNotifica", create=true)
	private FiltraNotifica filtraNotifica;
	
	@In(value="pooledTaskInstanceList", create=true)
	private List<TaskInstance> pooledTaskInstanceList;
	
	@In(value="orderProcessing",create=true)
	private OrderProcessing orderProcessing;
	
	private String tipoOrdine="1";
	
	private List<MyOrdine> ordini;
	
	private Ordine currentOrdine;
	
	private TaskInstance currentTask;


	public Ordine getCurrentOrdine() {
		return currentOrdine;
	}

	public void setCurrentOrdine(Ordine currentOrdine) {
		this.currentOrdine = currentOrdine;
	}

	public String getTipoOrdine() {
		return tipoOrdine;
	}

	public void setTipoOrdine(String tipoOrdine) {
		log.info("****ho selezionato un ordine****");
		log.info("*******"+tipoOrdine+"**********");
		this.tipoOrdine = tipoOrdine;
	}

	
	public List<TaskInstance> listOrdiniPendenti() {
		List<TaskInstance> ordiniPendenti = filtraNotifica.getAllPooledTaskInstanceListForSingleCustomer(credentials.getUsername(), "accetta_ordine");
		log.info("****PENDENTE****");
		log.info("*******"+ordiniPendenti.size()+"**********");
		return ordiniPendenti;
	}

	public List<Ordine> listOrdiniEvasi() {
		List<Ordine> ordiniEvasi = new ArrayList<Ordine>();
		if (tipoOrdine.equals("2")) {
			ordineList.setEjbql("select ordine from Ordine ordine where ordine.account.username='"+credentials.getUsername()+"' and ordine.concluso=false");
			ordiniEvasi = ordineList.getResultList();
		}
		return ordiniEvasi;
	}
	
	public List<Ordine> listOrdiniConclusi() {
		List<Ordine> ordiniConclusi = new ArrayList<Ordine>();
		if (tipoOrdine.equals("3")) {
			ordineList.setEjbql("select ordine from Ordine ordine where ordine.account.username='"+credentials.getUsername()+"' and ordine.concluso=true");
			ordiniConclusi = ordineList.getResultList();
		}
		return ordiniConclusi;
	}
	
	public MyOrdine getOrdineFromTask(TaskInstance task) {
		return (MyOrdine) task.getVariable("myOrdine");
	}
	
	public void setOrdini(List<MyOrdine> ordini) {
		this.ordini = ordini;
	}

	public TaskInstance getCurrentTask() {
		return currentTask;
	}

	public void setCurrentTask(TaskInstance currentTask) {
		this.currentTask = currentTask;
	}
	
}
