package org.domain.GAS.session;

import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.domain.GAS.entity.Account;
import org.domain.GAS.entity.Cybercontadino;
import org.domain.GAS.entity.Role;
import org.domain.GAS.entity.Utente;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.bpm.Actor;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

@Name("authenticator")
public class Authenticator
{
    @Logger private Log log;

    @In private Identity identity;
    @In private Credentials credentials;
    @In private Actor actor;
    @Out(value="currentUser", required = false)
    private Utente utente;
    @Out(value="currentAccount", required = false)
    private Account account;
    @Out(value="currentContadino", required = false)
    private Cybercontadino contadino;
    @In(value="passwordManager",create=true)
    private PasswordManager passwordManager;
    @In
    private EntityManager entityManager;
    @Transactional public boolean authenticate()
    {
        try{
        account=(Account) entityManager.createQuery(
        		"select account from Account account where account.username = :username").setParameter("username", credentials.getUsername()).getSingleResult();
        if(!validatePassword(credentials.getPassword(), account))
        	return false;
        if((!account.getAttivato()) || (account.getBloccato() || (account.getCancellato()) || (account.getEliminato()))){
        	return false;
        }
        actor.setId(credentials.getUsername());
        if (account.getRoles() != null) {
        	for (Role role : account.getRoles()) {
        	identity.addRole(role.getName());
        	actor.getGroupActorIds().add(role.getName());
        	}
        	
        }
        
        Iterator<Role> iter = account.getRoles().iterator();
		while(iter.hasNext())
		{
			Role r = iter.next();
			if(r.getName().equals("utenteContadino"))
			{
				contadino=(Cybercontadino) entityManager.createQuery(
	    		"select contadino from Cybercontadino contadino where contadino.account.username = :username").setParameter("username", credentials.getUsername()).getSingleResult();
	        	break;
			}
			else
	        {
	        	utente = account.getUtente();
	        	break;
	        }
		}
        
    	//log.info("UTENTE "+utente.getNome()+" "+utente.getEmail());
        
        return true;
        }
        catch(NoResultException nre){
        	return false;
        }
        
       /* 
        //write your authentication logic here,
        //return true if the authentication was
        //successful, false otherwise
        if ("admin".equals(credentials.getUsername()))
        {
        	identity.addRole("admin");
            return true;
        }
        return false;
        */
    }
    
    public boolean validatePassword(String password, Account a) {
    	return passwordManager.hash(password).equals(a.getPasswordHash());
    }

}
