package org.domain.SeamAmiciDelGas.processes;

import java.util.ArrayList;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.bpm.StartTask;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.security.Credentials;

@Name("inviaNotificaRegistrazioni")
@Scope(ScopeType.SESSION)
public class InviaNotificaRegistrazioni {
	
	@In private Credentials credentials;
	
	@In(value="inviati",scope=ScopeType.BUSINESS_PROCESS, required= false)
	@Out(value="inviati",scope=ScopeType.BUSINESS_PROCESS, required= false)
	private ArrayList<String> usernameInviati;
	
	@In(value="postiOccupati" , scope= ScopeType.BUSINESS_PROCESS, required =false)
	@Out(value="postiOccupati", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Integer postiOccupati;
	
	@Out(value="decisione",scope=ScopeType.BUSINESS_PROCESS, required= false)
	private boolean decisione;
		
	@StartTask @EndTask(transition="partecipa")
	public void partecipa_alla_visita()
	{
		usernameInviati.add(credentials.getUsername());
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa"+usernameInviati.toString());
		postiOccupati++;
		System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBb "+postiOccupati);
	}
	
	@StartTask @EndTask(transition="decisioneAdmin")
	public void visualizzaQuestionari(boolean b)
	{
		decisione = b;
	}
	
	


}
