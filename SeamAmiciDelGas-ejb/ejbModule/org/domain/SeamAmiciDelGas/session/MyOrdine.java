package org.domain.SeamAmiciDelGas.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.domain.SeamAmiciDelGas.webservices.Item;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;

public class MyOrdine implements Serializable{	

	private static final long serialVersionUID = 8347466730682726753L;

	@Logger
	Log log;
	
	private Date dataMassima;
	private Date dataRichiesta;
	private List<ItemQuantita> itemQuantita = new ArrayList<ItemQuantita>();
	private boolean pendente;
	private boolean evaso;
	
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
	public boolean isPendente() {
		return pendente;
	}
	public void setPendente(boolean pendente) {
		this.pendente = pendente;
	}
	
	public boolean isEvaso() {
		return evaso;
	}
	public void setEvaso(boolean evaso) {
		this.evaso = evaso;
	}
	public Date getDataRichiesta() {
		return dataRichiesta;
	}
	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	
}

