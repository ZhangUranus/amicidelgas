package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.domain.SeamAmiciDelGas.crud.AccountHome;
import org.domain.SeamAmiciDelGas.crud.FeedbackList;
import org.domain.SeamAmiciDelGas.crud.ItinerarioList;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Articolo;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Feedback;
import org.domain.SeamAmiciDelGas.entity.Itinerario;
import org.domain.SeamAmiciDelGas.entity.PuntiDiConsegna;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;


@Name(value="gestioneItinerario")
@Scope(ScopeType.SESSION)
public class GestioneItinerario 
{
	@In 
	private Credentials credentials;
	
	@In(value="itinerarioList",create=true)
	private ItinerarioList itinerarioList;
	
	private List<Itinerario> itinerariCorrenti;
	
	public GestioneItinerario() {
		
	}

	public List<Itinerario> getItinerariCorrenti() {
		Date dataCorrente = new Date(System.currentTimeMillis());
		//itinerarioList.setEjbql("select itinerario from Itinerario itinerario where itinerario.dataPartenza  >= '"+dataCorrente+"'");
		System.out.println("select itinerario from Itinerario itinerario where itinerario.dataPartenza  >= '"+dataCorrente+"'");
		itinerarioList.setEjbql("select itinerario from Itinerario itinerario ");
		itinerariCorrenti = itinerarioList.getResultList();
		System.out.println("ITINERARIADSAFDAS"+itinerariCorrenti.size());
		return itinerariCorrenti;
	}

	public void setItinerariCorrenti(List<Itinerario> itinerariCorrenti) {
		this.itinerariCorrenti = itinerariCorrenti;
	}
	
	public List<Cybercontadino> contadiniAssociati(Itinerario it) {
		List<Cybercontadino> contadinoList = new ArrayList<Cybercontadino>();
		Set<Cybercontadino> contadinoSet =it.getCybercontadinos();
		contadinoList.addAll(contadinoSet);
		return contadinoList;
	}
	public List<PuntiDiConsegna> puntiDiConsegnaAssociati(Itinerario it) {
		List<PuntiDiConsegna> puntiConsegnaList = new ArrayList<PuntiDiConsegna>();
		Set<PuntiDiConsegna> puntiConsegnaSet =it.getPuntiDiConsegnas();
		puntiConsegnaList.addAll(puntiConsegnaSet);
		return puntiConsegnaList;
	}

	

}
