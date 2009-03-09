package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.webservices.Item;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

@Name(value="itemQuantita")
@Scope(ScopeType.SESSION)
public class ItemQuantita
	{
	
		@Logger
		private Log log;
		private Item item;
		private int quantita;
		private boolean evasioneParziale = false;
		private int quantitaParziale;
		
		public boolean isEvasioneParziale() {
			return evasioneParziale;
		}
		public void setEvasioneParziale(boolean evasioneParziale) {
			this.evasioneParziale = evasioneParziale;
		}
		public int getQuantitaParziale() {
			return quantitaParziale;
		}
		public void setQuantitaParziale(int quantitaParziale) {
			this.quantitaParziale = quantitaParziale;
		}
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