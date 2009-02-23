package org.domain.SeamAmiciDelGas.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;


@Name("richiestaDriver")
public class RichiestaDriver {
	
	@Logger
	private Log log;
	@In private Credentials credentials;
	
	@In private FacesMessages facesMessages;
	
	@Out(value="nomeRichiedente",scope=ScopeType.BUSINESS_PROCESS)
	private String nomeRichiedente;
	
	@CreateProcess(definition="becomeDriver")
	public void richiestaDriver(){
		log.info("E' arrivata la richiesta driver");
		nomeRichiedente= credentials.getUsername();
		facesMessages.add("La richiesta è stata inoltrata");
	}
	
	
}
