package org.domain.SeamAmiciDelGas.processes;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import org.domain.SeamAmiciDelGas.crud.AccountHome;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.webservices.ServicePublisher;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
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
	
	@In(value="accountHome", create=true)
	private AccountHome accountHome;
	
	@In(value="postiOccupati" , scope= ScopeType.BUSINESS_PROCESS, required =false)
	@Out(value="postiOccupati", scope= ScopeType.BUSINESS_PROCESS, required =false)
	private int postiOccupati;
	
	@Out(value="decisione",scope=ScopeType.BUSINESS_PROCESS, required= false)
	private int decisione;
	
	@In(value="contadinoCreato" , scope= ScopeType.BUSINESS_PROCESS, required =false)
	private Cybercontadino contadino;
		
	@StartTask @EndTask(transition="partecipa")
	public void partecipa_alla_visita()
	{
		usernameInviati.add(credentials.getUsername());
		postiOccupati++;
		System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBb "+postiOccupati);
	}
	
	@StartTask @EndTask(beforeRedirect=true , transition="decisioneAdmin")
	public String visualizzaQuestionari(int b)
	{
		decisione = b;
		if(b == 1)
		{
			if(!(this.accettaContadino())){
				return null;
			}				
			else {
		    	String urlWsdl=contadino.getUrlWsdl();
		    	ServicePublisher sp= new ServicePublisher(); 
		    	sp.publishService(contadino.getPartitaIva(), "CatalogService", urlWsdl, contadino.getNameSpaceWsdl());
			}
		}
		else
		{
			if(!(this.rifiutaContadino()))
					return null;
		}
		return "OutComeRiepilogoRegistrazione";
	}
	
	@Transactional public boolean accettaContadino( )
    {
		Date dataCorrente = new Date(System.currentTimeMillis());
		Account accountContadino = contadino.getAccount();
		String username = accountContadino.getUsername();
		accountHome.setAccountUsername(username);
		accountContadino = accountHome.find();	
		accountContadino.setAttivato(true);
		accountContadino.setBloccato(false);
		accountContadino.setElimato(false);
		accountContadino.setDataAccettazione(dataCorrente);
		accountHome.update();
		return true; 
		
    }
	
	@Transactional public boolean rifiutaContadino( )
    {
		Date dataCorrente = new Date(System.currentTimeMillis());
		Account accountContadino = contadino.getAccount();
		String username = accountContadino.getUsername();
		accountHome.setAccountUsername(username);
		accountContadino = accountHome.find();	
		accountContadino.setAttivato(false);
		accountContadino.setBloccato(false);
		accountContadino.setElimato(true);
		accountContadino.setDataAccettazione(dataCorrente);
		accountHome.update();
		return true; 
		
    }
	
	


}
