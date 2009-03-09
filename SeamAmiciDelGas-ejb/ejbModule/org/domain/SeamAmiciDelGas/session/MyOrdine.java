package org.domain.SeamAmiciDelGas.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyOrdine implements Serializable{

	private Date dataMassima;
	private List<ItemQuantita> itemQuantita = new ArrayList<ItemQuantita>();

	
	public Date getDataMassima() {
		return dataMassima;
	}
	public void setDataMassima(Date dataMassima) {
		this.dataMassima = dataMassima;
	}
	public List<ItemQuantita> getItemQuantita() {
		return itemQuantita;
	}
	public void setItemQuantita(List<ItemQuantita> itemQuantita) {
		this.itemQuantita = itemQuantita;
	}
	
}

