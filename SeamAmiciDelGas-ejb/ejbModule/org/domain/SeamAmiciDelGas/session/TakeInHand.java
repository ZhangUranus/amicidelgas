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

	
	public void azzeraHowMuch() {
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
		if(i==null)
			return "0";
		for(ItemQuantita iq : itemQuantita)
		{
			if(iq.getItem().equals(i))
				return String.valueOf(iq.getQuantita());
		}
		return null;
	}
	
	public void addHowMuch(Item i, String howMuch)
	{
		if(howMuch==null || howMuch.equals("0"))
			return;
		
		//associo la quantita all'item corrispondente
		for(ItemQuantita it : itemQuantita)
		{
			if(it.getItem().equals(i))
			{	it.setQuantita(Integer.parseInt(howMuch)); return;	}
		}
		itemQuantita.add(new ItemQuantita(i,Integer.parseInt(howMuch)));
	}
}