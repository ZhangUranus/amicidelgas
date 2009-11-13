package org.domain.GAS.session;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

import org.domain.GAS.catalog.Item;
import org.domain.GAS.entity.Cybercontadino;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

@Name(value="itemQuantita")
@Scope(ScopeType.SESSION)
public class ItemQuantita implements Serializable
	{
	
		/**
	 * 
	 */
	private static final long serialVersionUID = -8281187892213576184L;
		@Logger
		private Log log;
		private Item item;
		private int quantita;
		private Cybercontadino cybercontadino; 
		private boolean booleanIsQuantitaMinima= false;
		private int quantitaParziale;
		private boolean checkedForOrdine=false;
		private float prezzoTotale;
		
		public float getPrezzoTotale() {
			return prezzoTotale;
		}
		public void setPrezzoTotale(float prezzoTotale) {
			this.prezzoTotale = prezzoTotale;
		}
		public boolean isCheckedForOrdine() {
			return checkedForOrdine;
		}
		public void setCheckedForOrdine(boolean checkedForOrdine) {
			this.checkedForOrdine = checkedForOrdine;
		}
		public ItemQuantita(Item item, int q)
		{
			this.item=item;
			this.quantita=q;
		}
		public ItemQuantita(Item item, int q,Cybercontadino contadino)
		{
			this.item=item;
			this.quantita=q;
			this.cybercontadino=contadino;
		}
		
		public boolean isBooleanIsQuantitaMinima() {
			return booleanIsQuantitaMinima;
		}
		public void setBooleanIsQuantitaMinima(boolean booleanIsQuantitaMinima) {
			this.booleanIsQuantitaMinima = booleanIsQuantitaMinima;
		}
		public int getQuantitaParziale() {
			return quantitaParziale;
		}
		public void setQuantitaParziale(int quantitaParziale) {
			this.quantitaParziale = quantitaParziale;
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
		
		public Cybercontadino getCybercontadino() {
			return cybercontadino;
		}
		public void setCybercontadino(Cybercontadino cybercontadino) {
			this.cybercontadino = cybercontadino;
		}
	}