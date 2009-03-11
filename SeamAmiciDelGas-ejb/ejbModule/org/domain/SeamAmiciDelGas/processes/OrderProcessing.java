package org.domain.SeamAmiciDelGas.processes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import org.domain.SeamAmiciDelGas.session.ItemQuantita;
import org.domain.SeamAmiciDelGas.session.Message;
import org.domain.SeamAmiciDelGas.session.MyOrdine;
import org.domain.SeamAmiciDelGas.webservices.CatalogInterface;
import org.domain.SeamAmiciDelGas.webservices.CatalogNoServiceImpl;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.bpm.BeginTask;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.security.Credentials;


@Name("orderProcessing")
@Scope(ScopeType.SESSION)
public class OrderProcessing {
	@In(value="myOrdine", scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="myOrdine",scope=ScopeType.BUSINESS_PROCESS,required=false)
	private MyOrdine myOrdine;
	
	@Out(value="notificaDriverContadino",scope=ScopeType.BUSINESS_PROCESS,required=false)
	protected Message messageDriverContadino;
	
	@Out(value="notifyMessage",scope=ScopeType.BUSINESS_PROCESS,required=false)
	protected Message messageStatoOrdine;
	
	@In(value="customer", scope=ScopeType.BUSINESS_PROCESS, required=false)
	@Out(value="customer", scope=ScopeType.BUSINESS_PROCESS, required=false)
	private String customer;
	
	@Out(value="selectedItemShoppingCart",scope=ScopeType.BUSINESS_PROCESS,required=false)
	private List<ItemQuantita> selectedItem;
	
	@Out(value="dataMassimaShoppingCart",scope=ScopeType.BUSINESS_PROCESS,required=false)
	private Date dataMassima;
	
	@In private Credentials credentials;
	
	@CreateProcess(definition="myOrderProcessing")
	public String startOrder(List<ItemQuantita> itemQ, Date dm){
		
		selectedItem = itemQ;
		dataMassima = dm;
		
		customer = credentials.getUsername();
		myOrdine = new MyOrdine();
		myOrdine.setDataMassima(dataMassima);
		myOrdine.setItemQuantita(selectedItem);
		//setto lo stato dell'ordine
		myOrdine.setPendente(true);
		myOrdine.setEvaso(false);
		boolean isStessoContadino=true;
		messageDriverContadino = new Message();

		String usernameContadino = selectedItem.get(0).getUsername();
		for(ItemQuantita iq : selectedItem)//vedo se il contadino è sempre lo stesso
			if(!(iq.getUsername().equals(usernameContadino)))
			{	
				isStessoContadino=false; break;
			}
		
		if(isStessoContadino)
			messageDriverContadino.addRecipient(usernameContadino);

		String content = "Ordine fatto da " +credentials.getUsername()+"dim = "+selectedItem.size();
		
		messageDriverContadino.setContent(content);
		
		return "partito";
	}
	
	@BeginTask @EndTask(transition="ordine_preso_in_carico")
	public void verificaDisponibilita(){
		//setto lo stato dell'ordine
		//myOrdine.setPendente(false);
		CatalogInterface catalog = new CatalogNoServiceImpl();
		Hashtable<String,UUID> transactionIdList = new Hashtable<String,UUID>();
		boolean isAvailable=true;
		//verifico la disponibilità per ogni contandino
		for (ItemQuantita iq : myOrdine.getItemQuantita()) {
			String idContadino = iq.getUsername();
			UUID uuid = transactionIdList.get(idContadino);
			if(uuid==null)
			{	
				uuid = catalog.beginTransaction(idContadino);
				transactionIdList.put(idContadino, uuid);
			}
			isAvailable=catalog.reserveItem(idContadino,uuid, iq.getItem(),iq.getQuantitaParziale(), iq.getQuantita(),myOrdine.getDataMassima());
			if(!isAvailable)
				break;
		}
		messageStatoOrdine =new Message();
		messageStatoOrdine.addRecipient(customer);
		if(!isAvailable)
		{
			Enumeration<String> enumContadini = transactionIdList.keys();
			while(enumContadini.hasMoreElements())
			{
				String idContadino = enumContadini.nextElement();
				catalog.rollBackTransaction(idContadino, transactionIdList.get(idContadino));
			}
			messageStatoOrdine.setContent("Ordine non fattibile, e' stato rimesso in coda");
		}
		else
		{
			Enumeration<String> enumContadini = transactionIdList.keys();
			while(enumContadini.hasMoreElements())
			{
				String idContadino = enumContadini.nextElement();
				catalog.commitTransaction(idContadino, transactionIdList.get(idContadino));
			}
			messageStatoOrdine.setContent("Ordine effettuato");
			myOrdine.setPendente(false);
			myOrdine.setEvaso(true);
		}
	}
	
	/*
	 * Getter and settrer methods...
	 */

	public MyOrdine getMyOrdine() {
		return myOrdine;
	}

	public void setMyOrdine(MyOrdine myOrdine) {
		this.myOrdine = myOrdine;
	}

	public List<ItemQuantita> getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(List<ItemQuantita> selectedItem) {
		this.selectedItem = selectedItem;
	}

	public Date getDataMassima() {
		return dataMassima;
	}

	public void setDataMassima(Date dataMassima) {
		this.dataMassima = dataMassima;
	}
	
}
