package org.domain.GAS.session;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.domain.GAS.crud.ItinerarioList;
import org.domain.GAS.entity.Account;
import org.domain.GAS.entity.Cybercontadino;
import org.domain.GAS.entity.Itinerario;
import org.domain.GAS.entity.PuntiDiConsegna;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jbpm.taskmgmt.exe.TaskInstance;

@Name("matchItinerarioOrdine")
@Scope(ScopeType.SESSION)
public class MatchItinerarioOrdine implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3656873046607888259L;

	@Logger
	private Log log;
	
	@In(value="currentAccount", scope=ScopeType.SESSION, required=false)
	private Account currentAccount;
	
	@In(value="itinerarioList", create=true)
	private ItinerarioList itinerarioList;
	
	private Itinerario currentItinerario;

	private List<Cybercontadino> currentListCybercontadino;
	
	private List<PuntiDiConsegna> currentListPuntiDiConsegna;
	
	@In(value="filtraNotifica",create=true)
	private FiltraNotifica filtraNotifica;
	
	@In private Credentials credentials;
	
	private List<Itinerario> itinerariValidi;
	
	public List<Itinerario> loadItinerari() {
		Date dataCorrente = new Date(System.currentTimeMillis());
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dataCorrente);
		String format = "yyyy-MM-dd-HH-mm";
		//query per tornare gli itinerari del driver
		itinerarioList.setEjbql("select itinerario from Itinerario itinerario " +
									"where itinerario.account.username='"+credentials.getUsername()+
									"' and itinerario.dataConsegna  >= '"+new SimpleDateFormat(format).format(gc.getTime())+"'");
		List<Itinerario> tempItinerario = itinerarioList.getResultList();
		return tempItinerario;
	}	
	
	
	/**
	 * vedo quali ordini nei taskInstance("accetta_ordine") possono essere
	 * soddisfatti dall'itinerario scelto.
	 * @param itinerario un itinerario caricato dal database
	 * @return List<TaskInstance>
	 */
	public List<TaskInstance> loadTaskInstanceForItinerario() {
		if(currentItinerario==null)
			return new ArrayList<TaskInstance>();
		List<TaskInstance> tasks = filtraNotifica.taskInstanceGroupListForDriver();

		List<TaskInstance> taskValidi = new ArrayList<TaskInstance>() ;
		MyOrdine myOrdine;
		for(TaskInstance ti : tasks)
		{
			myOrdine= (MyOrdine) ti.getVariable("myOrdine");
			if(myOrdine.getDataMassima().after(currentItinerario.getDataConsegna()))
				if(compareOrdineWithItinerario(myOrdine,currentItinerario))
					taskValidi.add(ti);
		}
		return taskValidi;
	}
	
	/**
	 * 
	 * @param myOrdine 
	 * @param itinerario
	 * @return true se l'ordine ha tutti contadini che si trovano anche nell'itinerario
	 */
	private boolean compareOrdineWithItinerario(MyOrdine myOrdine, Itinerario itinerario) {
		List<ItemQuantita> listItemQuantita = myOrdine.getItemQuantita();
		Set<Cybercontadino> contadinoList = itinerario.getCybercontadinos();
		for(ItemQuantita currentItemQuantita : listItemQuantita)
		{
			boolean isPresent=false;
			String usernameContadinoOrdine = currentItemQuantita.getCybercontadino().getAccount().getUsername();
			for(Cybercontadino currentContadino : contadinoList)
			{
				if(currentContadino.getAccount().getUsername().equals(usernameContadinoOrdine))
				{
					isPresent=true;
					break;
				}
			}
			if(!isPresent)
				return false;
		}
		return true;
	}

	public List<Itinerario> getItinerariValidi() {
		return itinerariValidi;
	}

	public void setItinerariValidi(List<Itinerario> itinerariValidi) {
		this.itinerariValidi = itinerariValidi;
	}
	public Itinerario getCurrentItinerario() {
		return currentItinerario;
	}
	public void setCurrentItinerario(Itinerario currentItinerario) {
		this.currentItinerario = currentItinerario;
		currentListCybercontadino = new ArrayList<Cybercontadino>();
		currentListCybercontadino.addAll(currentItinerario.getCybercontadinos());
		currentListPuntiDiConsegna = new ArrayList<PuntiDiConsegna>();
		currentListPuntiDiConsegna.addAll(currentItinerario.getPuntiDiConsegnas());
		
	}
	public List<Cybercontadino> getCurrentListCybercontadino() {
		return currentListCybercontadino;
	}
	public void setCurrentListCybercontadino(List<Cybercontadino> currentListCybercontadino) {
		this.currentListCybercontadino = currentListCybercontadino;
	}




	public List<PuntiDiConsegna> getCurrentListPuntiDiConsegna() {
		return currentListPuntiDiConsegna;
	}

	public void setCurrentListPuntiDiConsegna(
			List<PuntiDiConsegna> currentListPuntiDiConsegna) {
		this.currentListPuntiDiConsegna = currentListPuntiDiConsegna;
	}


	
}

