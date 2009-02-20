package org.domain.SeamAmiciDelGas.session;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Role;
import org.domain.SeamAmiciDelGas.entity.Utente;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

@Name("authenticator")
public class Authenticator
{
    @Logger private Log log;

    @In Identity identity;
    @In Credentials credentials;
    @Out(value="currentUser", required = false)
    private Utente utente;
    @Out(value="currentAccount", required = false)
    private Account account;
    @In(value="passwordManager",create=true)
    private PasswordManager passwordManager;
    @In
    private EntityManager entityManager;
    @Transactional public boolean authenticate()
    {
    	
        log.info("authenticating {0}", credentials.getUsername());
        try{
        account=(Account) entityManager.createQuery(
        		"select account from Account account where account.username = :username").setParameter("username", credentials.getUsername()).getSingleResult();
        if(!validatePassword(credentials.getPassword(), account))
        	return false;
        if (account.getRoles() != null) {
        	for (Role role : account.getRoles()) {
        	identity.addRole(role.getName());
        	
        	}
        	
        }
        
        utente = account.getUtente();
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