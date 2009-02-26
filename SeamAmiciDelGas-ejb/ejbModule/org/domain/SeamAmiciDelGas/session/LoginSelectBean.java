package org.domain.SeamAmiciDelGas.session;

import java.util.Iterator;

import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Role;
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
	
	public String calculateMaxRole()
	{
		Iterator<Role> iter = account.getRoles().iterator();
		while(iter.hasNext())
		{
			Role r = iter.next();
			if(r.getName().equals("admin"))
				return "admin";
		}
		iter = account.getRoles().iterator();
		while(iter.hasNext())
		{
			Role r = iter.next();
			if(r.getName().equals("mediatore"))
				return "mediatore";
		}
		iter = account.getRoles().iterator();
		while(iter.hasNext())
		{
			Role r = iter.next();
			if(r.getName().equals("utenteGas"))
				return "utenteGas";
		}
		return "null";
	}

	public String getMaxRole() {
		return maxRole;
	}

	public void setMaxRole(String maxRole) {
		this.maxRole = maxRole;
	}
	
	
}