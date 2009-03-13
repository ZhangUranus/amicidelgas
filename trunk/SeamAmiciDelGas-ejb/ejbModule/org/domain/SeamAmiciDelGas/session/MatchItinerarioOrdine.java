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
	
	public List<TaskInstance> loadTaskInstanceForItinerario(Itinerario itinerario) {
		List<TaskInstance> tasks = filtraNotifica.getAllPooledTaskInstanceList("accetta_ordine");
		List<TaskInstance> taskValidi = new ArrayList<TaskInstance>() ;
		MyOrdine myOrdine;
		for(TaskInstance ti : tasks)
		{
			myOrdine= (MyOrdine) ti.getVariable("myOrdine");
			if(compareOrdineItinerario(myOrdine,itinerario))
				taskValidi.add(ti);
		}
		return taskValidi;
	}
	
	private boolean compareOrdineItinerario(MyOrdine myOrdine, Itinerario itinerario) {
		List<ItemQuantita> listItemQuantita = myOrdine.getItemQuantita();
		Set<Cybercontadino> cyber = itinerario.getCybercontadinos();
		boolean matching;
		for(ItemQuantita iq : listItemQuantita)
		{
			String usernameContadinoOrdine = iq.getCybercontadino().getAccount().getUsername();
			matching=false;
			for(Cybercontadino tempCyber : cyber)
			{
				if(tempCyber.getAccount().getUsername().equals(usernameContadinoOrdine))
				{
					matching=true;
					break;
				}
			}
			if(!matching)
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

