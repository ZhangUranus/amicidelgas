package org.domain.GAS.session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.domain.GAS.crud.AccountHome;
import org.domain.GAS.crud.FeedbackList;
import org.domain.GAS.crud.ItinerarioList;
import org.domain.GAS.entity.Account;
import org.domain.GAS.entity.Articolo;
import org.domain.GAS.entity.Cybercontadino;
import org.domain.GAS.entity.Feedback;
import org.domain.GAS.entity.Itinerario;
import org.domain.GAS.entity.PuntiDiConsegna;
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

	public List<Itinerario> getItinerariCorrenti() 
	{
		Date dataCorrente = new Date(System.currentTimeMillis());
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dataCorrente);
		String format = "yyyy-MM-dd-HH-mm";
		itinerarioList.setEjbql("select itinerario from Itinerario itinerario where itinerario.dataPartenza  >= '"+new SimpleDateFormat(format).format(gc.getTime())+"'");
		itinerariCorrenti = itinerarioList.getResultList();
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
