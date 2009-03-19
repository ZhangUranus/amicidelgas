package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import org.domain.SeamAmiciDelGas.webservices.Item;


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
	
	private List<Articolo> articoli;
	
	private Ordine currentOrdine;
	
	private MyOrdine currentMyOrder;
	
	private TaskInstance currentTask;


	public Ordine getCurrentOrdine() {
		return currentOrdine;
	}

	public void setCurrentOrdine(Ordine currentOrdine) {
		log.info("\n\n******** CURRENT ORDINE == " +currentOrdine.getDriver() +" **********");
		this.currentOrdine = currentOrdine;
	}

	public String getTipoOrdine() {
		return tipoOrdine;
	}

	public void setTipoOrdine(String tipoOrdine) {
		this.tipoOrdine = tipoOrdine;
	}

	
	public List<TaskInstance> listOrdiniPendenti() {
		List<TaskInstance> ordiniPendenti = filtraNotifica.getAllPooledTaskInstanceListForSingleCustomer(credentials.getUsername(), "accetta_ordine");
		return ordiniPendenti;
	}

	public List<Ordine> listOrdiniEvasiOrConclusi()
	{
		List<Ordine> ordini = new ArrayList<Ordine>();
		if (tipoOrdine.equals("2")) {
			ordineList.setEjbql("select ordine from Ordine ordine where ordine.account.username='"+credentials.getUsername()+"' and ordine.concluso=false");
			ordini = ordineList.getResultList();
		}
		if (tipoOrdine.equals("3")) {
			ordineList.setEjbql("select ordine from Ordine ordine where ordine.account.username='"+credentials.getUsername()+"' and ordine.concluso=true");
			ordini = ordineList.getResultList();
		}
		return ordini;
	}

	public List<Ordine> ordiniPresiInCarico() {
		List<Ordine> ordini = new ArrayList<Ordine>();
		ordineList.setEjbql("select ordine from Ordine ordine where ordine.driver.username='"+credentials.getUsername()+"' and ordine.concluso=false");
		ordini = ordineList.getResultList();
		return ordini;
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

	public MyOrdine getCurrentMyOrder() {
		return currentMyOrder;
	}

	public void setCurrentMyOrder(MyOrdine currentMyOrder) {
		this.currentMyOrder = currentMyOrder;
	}
	
	public void deleteItemFromMyOrdine(ItemQuantita itemSelected)
	{
		List<ItemQuantita> itemQuantitaList = currentMyOrder.getItemQuantita();
		log.info("***** deleteItemFromMyOrdine(Item itemSelected) ********");
		for(int index=0; index<itemQuantitaList.size(); index++)
		{
			ItemQuantita iq = itemQuantitaList.get(index);
			log.info("***** itemQuantita"+iq.getItem().getName() +" ********" );
			if(iq.getItem().equals(itemSelected.getItem()))
			{
				log.info("\n\n*****\n\n itemQuantita rimosso..."+iq.getItem().getName() +" \n\n********\n\n" );
				itemQuantitaList.remove(index);
				return;
			}
		}
	}
	
	public List<Articolo> currentArticolosForOrdine() {
		List<Articolo> articoloList = new ArrayList<Articolo>();
		if(currentOrdine==null)
			return articoloList;
		Set<Articolo> articoloSet = currentOrdine.getArticolos();
		articoloList.addAll(articoloSet);
		return articoloList;
	}

	public List<Articolo> getArticoli() {
		articoli = new ArrayList<Articolo>();
		if (currentOrdine!=null)
			articoli.addAll(currentOrdine.getArticolos());
		return articoli;
	}

	public void setArticoli(List<Articolo> articoli) {
		this.articoli = articoli;
	}

}
