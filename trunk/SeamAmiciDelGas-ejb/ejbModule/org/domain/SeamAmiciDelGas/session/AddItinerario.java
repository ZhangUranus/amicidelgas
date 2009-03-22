package org.domain.SeamAmiciDelGas.session;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.international.StatusMessages;

@Stateless
@Name("addItinerario")
@Scope(value=ScopeType.PAGE)
public class AddItinerario
{
    @Logger private Log log;

    @PersistenceContext
    private EntityManager em;
    @In StatusMessages statusMessages;

    private String all_id;
    private Date date;

    public String addItinerario()
    {
    	System.out.println("DATA: "+date);
        statusMessages.add("newPuntiDiConsegna");
        
        return "OutcomeItinerario";
    }
    

	public String getAll_id() {
		return all_id;
	}

	public void setAll_id(String all_id) {
		this.all_id = all_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

    // add additional action methods
}
