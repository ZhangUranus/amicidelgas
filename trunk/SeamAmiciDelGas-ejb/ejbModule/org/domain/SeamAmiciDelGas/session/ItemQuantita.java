package org.domain.SeamAmiciDelGas.session;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

import org.domain.SeamAmiciDelGas.webservices.Item;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

@Name(value="itemQuantita")
@Scope(ScopeType.SESSION)
public class ItemQuantita implements Serializable
	{
	
		@Logger
		private Log log;
		private Item item;
		private int quantita;
		private String username; 
		private boolean booleanIsQuantitaMinima= false;
		private int quantitaParziale;
		private Date dataMassimaAcquisto;
		private boolean checkedForOrdine=false;
		
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
		public ItemQuantita(Item item, int q,String contadinoUsername)
		{
			this.item=item;
			this.quantita=q;
			this.username=contadinoUsername;
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
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public Date getDataMassimaAcquisto() {
			return dataMassimaAcquisto;
		}
		public void setDataMassimaAcquisto(Date dataMassimaAcquisto) {
			this.dataMassimaAcquisto = dataMassimaAcquisto;
		}
	}