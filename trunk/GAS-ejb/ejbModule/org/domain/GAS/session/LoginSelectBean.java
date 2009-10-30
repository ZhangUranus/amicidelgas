package org.domain.GAS.session;

import java.util.Iterator;

import org.domain.GAS.entity.Account;
import org.domain.GAS.entity.Role;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;



@Name("loginSelectBean") 
@Scope(ScopeType.SESSION)
public class LoginSelectBean{

	public LoginSelectBean(){}
	
	
	private String maxRole;
	@In(value="currentAccount", create=true)
	private Account account;
	@Logger
	private Log log;
	
	private boolean isCustomer;
	
	public void setCustomer(boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	public String calculateMaxRole()
	{
		for(Role r : account.getRoles())
			if(r.getName().equals("admin"))
				return "admin";
		for(Role r : account.getRoles())
			if(r.getName().equals("mediatore"))
				return "mediatore";
		for(Role r : account.getRoles())
			if(r.getName().equals("driver"))
				return "driver";
		for(Role r : account.getRoles())
			if(r.getName().equals("utenteContadino"))
				return "utenteContadino";
		for(Role r : account.getRoles())
			if(r.getName().equals("utenteGas"))
				return "utenteGas";
		return "null";
	}
	
	public boolean getIsCustomer() {
		for(Role r : account.getRoles())
			if(r.getName().equals("utenteGas") || r.getName().equals("admin") || r.getName().equals("mediatore"))
				return true;
		return false;
	}
	
	public boolean isDriver() {
		for(Role r : account.getRoles())
			if(r.getName().equals("driver"))
				return true;
		return false;

	}
	
	public String getMaxRole() {
		return maxRole;
	}

	public void setMaxRole(String maxRole) {
		this.maxRole = maxRole;
	}
	
	
}