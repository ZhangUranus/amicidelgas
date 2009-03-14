package org.domain.SeamAmiciDelGas.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.domain.SeamAmiciDelGas.crud.ItinerarioList;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Itinerario;
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

	@Logger
	private Log log;
	
	@In(value="currentAccount", scope=ScopeType.SESSION, required=false)
	private Account currentAccount;
	
	@In(value="itinerarioList", create=true)
	private ItinerarioList itinerarioList;
	
	private Itinerario currentItinerario;
	
	private List<Cybercontadino> currentListCybercontadino;
	
	@In(value="filtraNotifica",create=true)
	private FiltraNotifica filtraNotifica;
	
	@In private Credentials credentials;
	
	private List<Itinerario> itinerariValidi;
	
	public List<Itinerario> loadItinerari() {
		//query per tornare gli itinerari del driver
		itinerarioList.setEjbql("select itinerario from Itinerario itinerario " +
									"where itinerario.account.username='"+credentials.getUsername()+"'");
		List<Itinerario> tempItinerario = itinerarioList.getResultList();
		log.info("\n\n******** ITINERARIO dim = " +tempItinerario.size() +" **************");
		
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

		log.info("***********task size = " +tasks.size());
		List<TaskInstance> taskValidi = new ArrayList<TaskInstance>() ;
		MyOrdine myOrdine;
		for(TaskInstance ti : tasks)
		{
			myOrdine= (MyOrdine) ti.getVariable("myOrdine");
			if(myOrdine.getDataMassima().after(currentItinerario.getDataConsegna()))
				if(compareOrdineWithItinerario(myOrdine,currentItinerario))
					taskValidi.add(ti);
		}
		log.info("***********taskValidi size = " +taskValidi.size());
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
	}
	public List<Cybercontadino> getCurrentListCybercontadino() {
		return currentListCybercontadino;
	}
	public void setCurrentListCybercontadino(List<Cybercontadino> currentListCybercontadino) {
		this.currentListCybercontadino = currentListCybercontadino;
	}
	

	
}

