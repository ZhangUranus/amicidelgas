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
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jbpm.taskmgmt.exe.TaskInstance;

@Name("matchItinerarioOrdine")
@Scope(ScopeType.SESSION)
public class MatchItinerarioOrdine implements Serializable{

	
	@In(value="currentAccount", scope=ScopeType.SESSION, required=false)
	private Account currentAccount;
	
	@In(value="itinerarioList", create=true)
	private ItinerarioList itinerarioList;
	
	private FiltraNotifica filtraNotifica;
	
	private List<Itinerario> itinerariValidi;
	
	public List<Itinerario> loadItinerari() {
		//query per tornare gli itinerari del driver
		return null;
	}
	/**
	 * vedo quali ordini nei taskInstance("accetta_ordine") possono essere
	 * soddisfatti dall'itinerario scelto.
	 * @param itinerario un itinerario caricato dal database
	 * @return List<TaskInstance>
	 */
	public List<TaskInstance> loadTaskInstanceForItinerario(Itinerario itinerario) {
		List<TaskInstance> tasks = filtraNotifica.getAllPooledTaskInstanceList("accetta_ordine");
		List<TaskInstance> taskValidi = new ArrayList<TaskInstance>() ;
		MyOrdine myOrdine;
		for(TaskInstance ti : tasks)
		{
			myOrdine= (MyOrdine) ti.getVariable("myOrdine");
			if(compareOrdineWithItinerario(myOrdine,itinerario))
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
			String usernameContadinoOrdine = currentItemQuantita.getCybercontadino().getAccount().getUsername();
			for(Cybercontadino currentContadino : contadinoList)
				if(!currentContadino.getAccount().getUsername().equals(usernameContadinoOrdine))
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
	

	
}

