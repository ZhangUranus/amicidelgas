package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
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
	
	private String quantita;
	
	List<ItemQuantita> itemInShoppingCart = new ArrayList<ItemQuantita>();

	public ShoppingCart(){}
	
	public void aggiungiAlCarrello(Item item, String quantita)
	{	
		if(quantita==null || quantita.equals("0")) 
			return;
			//itemInShoppingCart.add(new ItemQuantita(item,0));
		itemInShoppingCart.add(new ItemQuantita(item,Integer.parseInt(quantita)));
	}
	
	
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
	
	public void addItemInShoppingCart(Item item, String quantita)
	{
		//gestire se il prodotto esiste gia...
		for(ItemQuantita iq : itemInShoppingCart)
		{
			if(quantita==null || quantita.equals("0")) 
				return;
			if(iq.getItem().equals(item))
			{	iq.addQuantita(Integer.parseInt(quantita)); 
				log.info("******** aggiunto item gia esistente: "+item.getName() +" quantita = "+quantita);
				return;	}
		}
		log.info("******** aggiunto nuovo item : "+item.getName() +" quantita = "+quantita);
		itemInShoppingCart.add(new ItemQuantita(item,Integer.parseInt(quantita)));
	}
	
	

	public class ItemQuantita
	{
		private Item item;
		private int quantita;
		public ItemQuantita(Item item, int q)
		{
			this.item=item;
			this.quantita=q;
		}
		public Item getItem() {
			return item;
		}
		public void setItem(Item item) {
			this.item = item;
		}
		public int getQuantita() {
			return quantita;
		}
		public void setQuantita(int quantita) {
			this.quantita = quantita;
		}
		public void addQuantita(int quantita)
		{
			this.quantita += quantita;
		}
		public boolean equals(Object o)
		{
			return item.equals((Item)o);
		}
	}

	
}

	
