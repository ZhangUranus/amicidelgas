package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.processes.NotifyBean;
import org.domain.SeamAmiciDelGas.processes.OrderProcessing;
import org.domain.SeamAmiciDelGas.webservices.Item;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Log;


@Name("shoppingCart")
@Scope(ScopeType.SESSION)
public class ShoppingCart {

	@Logger
	private Log log;
	
	@In(value="orderProcessing",create=true)
	private OrderProcessing orderProcessing;
	
	private String quantita="0";
	
	private boolean noSelect=false;
	
	List<ItemQuantita> itemInShoppingCart = new ArrayList<ItemQuantita>();

	private Date dataMassima;

	private List<ItemQuantita> selectedItem;

	private boolean dataMassimaBeforeToday=false;
	
	public Date getDataMassima() {
		return dataMassima;
	}

	public void setDataMassima(Date dataMassima) {
		this.dataMassima = dataMassima;
	}

	public ShoppingCart(){}
	
	public String getQuantita() {
		return quantita;
	}

	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}

	public boolean isEmpyShoppingCart()
	{
		if(itemInShoppingCart.size()==0)
			return true;
		return false;
	}

	public List<ItemQuantita> getItemInShoppingCart() {
		return itemInShoppingCart;
	}

	public void setItemInShoppingCart(List<ItemQuantita> itemInShoppingCart) {
		this.itemInShoppingCart = itemInShoppingCart;
	}
	
	public void addItemInShoppingCart(Item item, String quantita, Cybercontadino contadino)
	{
		if(quantita==null || quantita.equals("0") || quantita.equals("")) 
			return;
		for(ItemQuantita iq : itemInShoppingCart)
		{
			if(iq.getItem().equals(item))
			{	iq.addQuantita(Integer.parseInt(quantita)); 
				log.info("******** aggiunto item gia esistente: "+item.getName() +" quantita = "+quantita);
				return;	}
		}
		log.info("******** aggiunto nuovo item : "+item.getName() +" quantita = "+quantita +"nome" +contadino.getCognomePresidente());
		itemInShoppingCart.add(new ItemQuantita(item,Integer.parseInt(quantita),contadino));
	}
	
	public void deleteItemInShoppingCart(Item item)
	{
		for(int i=0; i<itemInShoppingCart.size(); i++)
		{
			ItemQuantita iq = itemInShoppingCart.get(i);
			if(iq.getItem().equals(item))
			{	
				itemInShoppingCart.remove(i);
				return;	}
		}
	}
	
	public double calcolaTotale()
	{
		double totale=0;
		for(ItemQuantita iq : itemInShoppingCart)
		{
			totale += ( iq.getItem().getPrezzo() * iq.getQuantita() );
		}
		return totale;
	}
	
	public void buySelectedItem()
	{
		log.info("******** buyAllItem************");
		
		if(dataMassima==null)
		{
			log.info("*******data massima = nuuuuuulllllllllllllllllllll*********");
			return;
		}
		if(dataMassimaBeforeCurrentDate())
			return;

		selectedItem = new ArrayList<ItemQuantita>();
		for(int index=0; index<itemInShoppingCart.size(); index++)//aggiungo i prodotti all'ordine da fare
		{	
			ItemQuantita iq=itemInShoppingCart.get(index);
			if(iq.isCheckedForOrdine())
			{	selectedItem.add(iq); 
				itemInShoppingCart.remove(index--);
				log.info("******** Item SELEZIONATO "+iq.getItem().getName()+"************");	
			}
		}
		noSelect = (selectedItem.size()==0) ? true : false;
		
		if(noSelect)
		{
			log.info("*******nessun item selezionato, ritorno*********");
			return;
		}

		dataMassimaBeforeToday=false;
		log.info("*******data massima = " +dataMassima.toString() +" ************");
		String logInfo = orderProcessing.startOrder(selectedItem,dataMassima);
		log.info("*********** "+logInfo);
		//se l'ordine nn va a buon fine devo fare il rollback e riaggiungere
		//gli item nel carrello...
	}
	
	public void update() {
		
	}

	public boolean isNoSelect() {
		return noSelect;
	}

	public void setNoSelect(boolean noSelect) {
		this.noSelect = noSelect;
	}

	private boolean dataMassimaBeforeCurrentDate()
	{
		GregorianCalendar gc = new GregorianCalendar();
		Date currentDate = gc.getTime();
		if(dataMassima.before(currentDate))
		{
			log.info("*******data massima = " +dataMassima.toString() +" ************");
			log.info("******* TROPPO PRESTOOOOOOOOOOOOOOOOO ************");
			dataMassimaBeforeToday=true;
			return true;
		}
		return false;
	}

	public boolean isDataMassimaBeforeToday() {
		return dataMassimaBeforeToday;
	}

	public void setDataMassimaBeforeToday(boolean dataMassimaBeforeToday) {
		this.dataMassimaBeforeToday = dataMassimaBeforeToday;
	}
}

	
