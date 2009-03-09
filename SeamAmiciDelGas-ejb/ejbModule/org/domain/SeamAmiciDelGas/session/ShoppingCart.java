package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.domain.SeamAmiciDelGas.webservices.Item;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;


@Name("shoppingCart")
@Scope(ScopeType.SESSION)
public class ShoppingCart {

	@Logger
	private Log log;
	
	private String quantita="0";
	
	private boolean noSelect=false;
	
	List<ItemQuantita> itemInShoppingCart = new ArrayList<ItemQuantita>();

	public ShoppingCart(){}
	
	public String getQuantita() {
		return quantita;
	}

	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}

	public List<ItemQuantita> getItemInShoppingCart() {
		return itemInShoppingCart;
	}

	public void setItemInShoppingCart(List<ItemQuantita> itemInShoppingCart) {
		this.itemInShoppingCart = itemInShoppingCart;
	}
	
	public void addItemInShoppingCart(Item item, String quantita, String contadinoUsername)
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
		log.info("******** aggiunto nuovo item : "+item.getName() +" quantita = "+quantita +"nome" +contadinoUsername);
		itemInShoppingCart.add(new ItemQuantita(item,Integer.parseInt(quantita),contadinoUsername));
	}
	
	public void deleteItemInShoppingCart(Item item, String quantita)
	{
		if(quantita==null || quantita.equals("")) 
			return;
		
		for(int i=0; i<itemInShoppingCart.size(); i++)
		{
			ItemQuantita iq = itemInShoppingCart.get(i);
			if(iq.getItem().equals(item))
			{	
				itemInShoppingCart.remove(i);
				log.info("******** eliminato item : "+item.getName() +" quantita = "+quantita);
				return;	}
			
		}
		log.info("******** item non presente : "+item.getName() +" quantita = "+quantita);
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
	
	public void buyAllItem()
	{
		//procede all'acquisto dei prodotti nel carrello
		//svuota il carrello
		log.info("******** buyAllItem************");
		List<ItemQuantita> selectedItem = new ArrayList<ItemQuantita>();
		
		for(int index=0; index<itemInShoppingCart.size(); index++)//aggiungo i prodotti all'ordine da fare
		{	
			ItemQuantita iq=itemInShoppingCart.get(index);
			if(iq.isCheckedForOrdine())
			{	selectedItem.add(iq); log.info("******** Item SELEZIONATO "+iq.getItem().getName()+"************");	}
		}
		//invia ordine
		
		for(int index=0; index<itemInShoppingCart.size(); index++)//elimino i prodotti dalla lista
		{	
			ItemQuantita iq=itemInShoppingCart.get(index);
			if(iq.isCheckedForOrdine())
			{
				itemInShoppingCart.remove(index);
				index=0;
			}
		}
		if (selectedItem.size()==0)
			noSelect=  true;
		else
			noSelect=  false;
	}

	public boolean isNoSelect() {
		return noSelect;
	}

	public void setNoSelect(boolean noSelect) {
		this.noSelect = noSelect;
	}
}

	
