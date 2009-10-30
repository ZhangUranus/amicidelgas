package org.domain.GAS.processes;

import org.domain.GAS.entity.Account;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.annotations.bpm.StartTask;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.faces.FacesMessages;

@Name("registrationConfirmation")
public class RegistrationConfirmation 
{
	@In protected FacesMessages facesMessages;

	@In(required = false, scope = ScopeType.BUSINESS_PROCESS)
	@Out(required = false, scope = ScopeType.BUSINESS_PROCESS)
	protected String confirmationCode;
	
	@Out(value="currentAccountUtente", scope = ScopeType.BUSINESS_PROCESS , required=false)
	private Account currentAccountUtente;
	
	@RequestParameter("code") private String confirmationCodeVerify;
	
	@CreateProcess(definition = "ProcessoEmailConferma")
	@Observer("registrationRequest")
	public void initiateConfirmation(Account a) {
		confirmationCode = "hole-in-one";
		currentAccountUtente = a;
	}
	
	@StartTask @EndTask(beforeRedirect=true , transition = "confirmed")
	public String confirm() {
		if (confirmationCodeVerify != null &&
			confirmationCodeVerify.equals(confirmationCode)) {
			facesMessages.add("Registrazione confermata ora fai parte degli AMICI DEL GAS!");
			return "confirmed";
		}
		else {
			facesMessages.add("Invalid confirmation code.");
			return null;
		}
	}
}
