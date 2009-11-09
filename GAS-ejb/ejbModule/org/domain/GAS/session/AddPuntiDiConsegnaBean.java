package org.domain.GAS.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.domain.GAS.crud.ComuneList;
import org.domain.GAS.entity.PuntiDiConsegna;
import org.domain.GAS.entity.Comune;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.international.StatusMessages;

@Stateless
@Name("addPuntiDiConsegna")
@Scope(value=ScopeType.PAGE)
public class AddPuntiDiConsegnaBean implements AddPuntiDiConsegna
{
    @Logger private Log log;

    @PersistenceContext
    private EntityManager em;
    @In StatusMessages statusMessages;

    @In(value="newPuntiDiConsegna", create=true)
    private PuntiDiConsegna puntiDiConsegna;

    @In(value="newComunePuntoConsegna", create=true)
    private ComunePuntoConsegna comuneProvinciaBean;

    public void addPuntiDiConsegna()
    {
        // implement your business logic here
        statusMessages.add("newPuntiDiConsegna");
    }
    
	public boolean persistExtend(){
		
		if(comuneProvinciaBean.getComune() == null)
			return false;
		ComuneList comuneList = new ComuneList();
    	comuneList.setEjbql("select comune from Comune comune where comune.idcomune= "+ comuneProvinciaBean.getComune().getId());
    	Comune comune = comuneList.getResultList().get(0);
    	if(comune != null)
    	{
    		puntiDiConsegna.setComune(comuneList.getResultList().get(0));
    		String indirizzo = puntiDiConsegna.getIndirizzo();
    		indirizzo = indirizzo.toLowerCase();
    		if(indirizzo.startsWith("via ") || indirizzo.startsWith("corso ") || indirizzo.startsWith("viale ") || indirizzo.startsWith("piazza ") || indirizzo.startsWith("contrada ") || indirizzo.startsWith("frazione ") || indirizzo.startsWith("strada ") || indirizzo.startsWith("piazzale ") || indirizzo.startsWith("rotonda ") || indirizzo.startsWith("rotonde "))
        		puntiDiConsegna.setIndirizzo(indirizzo);
        	else
        		puntiDiConsegna.setIndirizzo("via " + indirizzo);
    		em.persist(puntiDiConsegna);
    		return true;
    	}
    	else
    		return false;
	}

    // add additional action methods
}
