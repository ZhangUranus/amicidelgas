package org.domain.SeamAmiciDeGas.customers;

import javax.ejb.Local;

import org.domain.SeamAmiciDelGas.entity.Utente;

@Local
public interface CustomersEdit {
	
	public void setUtente(Utente utente);
	
	public boolean updateContatti();
	public Utente getUtente();
	
	public void destroy();
}
