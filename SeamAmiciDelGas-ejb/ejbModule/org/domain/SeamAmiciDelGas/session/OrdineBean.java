package org.domain.SeamAmiciDelGas.session;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.domain.SeamAmiciDelGas.crud.AccountList;
import org.domain.SeamAmiciDelGas.crud.OrdineList;
import org.domain.SeamAmiciDelGas.entity.Ordine;


@Name("ordineBean")
@Scope(ScopeType.SESSION)
public class OrdineBean {

	@Logger
	private Log log;
	
	private String quantita;
	
	@In private Credentials credentials;
	
	@In(value="ordineList",create=true)
	private OrdineList ordineList;
	
	private String tipoOrdine="1";
	
	private List<Ordine> ordini;
	
	private Ordine currentOrdine;

	public Ordine getCurrentOrdine() {
		return currentOrdine;
	}

	public void setCurrentOrdine(Ordine currentOrdine) {
		this.currentOrdine = currentOrdine;
	}

	public String getTipoOrdine() {
		return tipoOrdine;
	}

	public void setTipoOrdine(String tipoOrdine) {
		log.info("****ho selezionato un ordine****");
		log.info("*******"+tipoOrdine+"**********");
		this.tipoOrdine = tipoOrdine;
	}

	public List<Ordine> getOrdini() {
		log.info("*******"+credentials.getUsername()+"**********");
		log.info("*******"+tipoOrdine+"**********");
		if (tipoOrdine.equals("1"))
			ordineList.setEjbql("select ordine from Ordine ordine where ordine.account.username='"+credentials.getUsername()+"' and ordine.cancellato=false");
		//selezioni gli ordini pendenti
		if (tipoOrdine.equals("2"))
			ordineList.setEjbql("select ordine from Ordine ordine where ordine.account.username='"+credentials.getUsername()+"' and ordine.cancellato=false and ordine.pendente=true");
		//selezioni gli ordini conclusi
		if (tipoOrdine.equals("3"))
			ordineList.setEjbql("select ordine from Ordine ordine where ordine.account.username='"+credentials.getUsername()+"' and ordine.cancellato=false and ordine.pendente=false");
		ordini = ordineList.getResultList();
		return ordini;
	}

	public void setOrdini(List<Ordine> ordini) {
		this.ordini = ordini;
	}

	public String getQuantita() {
		return quantita;
	}

	public void setQuantita(String quantita) {
		log.info("************ quantita= "+quantita +"************");
		this.quantita = quantita;
	}
	
	public void salvaQuantita(String s)
	{
		log.info("************ s= "+s +"************");
		quantita = s;
	}
	
}
