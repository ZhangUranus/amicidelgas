package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.webservices.Item;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import org.jboss.seam.ScopeType;

@Name(value="takeInHand") 
@Scope(ScopeType.SESSION)
public class TakeInHand {

	public class Quantita {

		private int quantity;
		public Quantita(int quantity) {
			this.quantity=quantity;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

	}

	public TakeInHand(){}
	
	@In(value="contadinoSelezionato",required=false)
	private Cybercontadino contadinoSelezionato;
	private Hashtable<ItemContadino, Quantita> quantitaItem= new Hashtable<ItemContadino, Quantita>();
	

	
	public void resetQuantita(Item i){
		Quantita quantita= getQuantita(i);
		quantita.setQuantity(0);
	}
	
	public void reset(){
		quantitaItem= new Hashtable<ItemContadino, Quantita>();	
	}
	
	
	public Quantita getQuantita(Item i){
		if(i==null || contadinoSelezionato==null){
			ItemContadino itContadino= new ItemContadino(i.getId(), contadinoSelezionato.getPartitaIva());
			Quantita quantita = new Quantita(0);
			quantitaItem.put(itContadino, quantita);
			return quantita;
		}
		ItemContadino itContadino= new ItemContadino(i.getId(), contadinoSelezionato.getPartitaIva());
		Quantita q= quantitaItem.get(itContadino);
		if(q==null){
			q= new Quantita(0);
			quantitaItem.put(itContadino, q);
		}
		return q;
	}
	
	/*
	public String getHowMuchForItem(Item i)
	{	//torno la quantita per l'item corrispondente
		
		if(i==null || contadinoSelezionato==null)
			return "0";
		ItemContadino itContadino= new ItemContadino(i.getId(), contadinoSelezionato.getPartitaIva());
		Integer quantita= quantitaItem.get(itContadino);
		if(quantita == null)
			return "0";
		return quantita.toString();
	}
	
	public String incrementHowMuchForItem(Item i)
	{
		String howMuch=this.getHowMuchForItem(i);
		System.out.println("Eureka!!");
		ItemContadino itContadino= new ItemContadino(i.getId(), contadinoSelezionato.getPartitaIva());
		quantitaItem.put(itContadino, new Integer(Integer.parseInt(howMuch)+1));
		return "updated";
	}*/
	
	private class ItemContadino{
		public ItemContadino(String id, String partitaIva) {
			this.partitaIva=partitaIva;// TODO Auto-generated constructor stub
			this.codiceItem=id;
		}
		private String partitaIva;
		private String codiceItem;
		
		public boolean equals(Object o){
			ItemContadino other= (ItemContadino) o;
			if(partitaIva.equalsIgnoreCase(other.getPartitaIva()) && codiceItem.equalsIgnoreCase(other.getCodiceItem()) )
				return true;
			return false;
		}
		
		public int hashCode(){
			return partitaIva.hashCode()+ codiceItem.hashCode();
		}
		public String getPartitaIva() {
			return partitaIva;
		}

		public String getCodiceItem() {
			return  codiceItem;
		}
	}	
	
}

