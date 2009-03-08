package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.List;

import org.domain.SeamAmiciDelGas.webservices.Item;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import org.jboss.seam.ScopeType;

@Name(value="takeInHand") 
@Scope(ScopeType.SESSION)
public class TakeInHand {

	public TakeInHand(){}
	
	private String howMuch="0";
	private List<ItemQuantita> itemQuantita = new ArrayList<ItemQuantita>();

	
	public void azzeraHowMuch()
	{
		howMuch="0";
	}
	
	public String getHowMuch() {
		return howMuch;
	}

	public void setHowMuch(String howMuch) {
		this.howMuch = howMuch;
	}
	
	public String getHowMuchForItem(Item i)
	{	//torno la quantita per l'item corrispondente
		for(ItemQuantita iq : itemQuantita)
		{
			if(iq.getItem().equals(i))
				return String.valueOf(iq.getQuantita());
		}
		return null;
	}
	
	public void addHowMuch(Item i, String howMuch)
	{
		//associo la quantita all'item corrispondente
		for(ItemQuantita it : itemQuantita)
		{
			if(it.getItem().equals(i))
			{
				if(howMuch==null || howMuch.equals("0"))
					return;
				it.setQuantita(Integer.parseInt(howMuch)); return;
			}
		}
		itemQuantita.add(new ItemQuantita(i,Integer.parseInt(howMuch)));
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