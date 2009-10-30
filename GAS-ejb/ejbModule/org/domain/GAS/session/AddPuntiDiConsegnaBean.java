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
    @In(value="newComuneProvinciaBean", create=true)
    private ComuneProvinciaBean comuneProvinciaBean;

    public void addPuntiDiConsegna()
    {
        // implement your business logic here
        statusMessages.add("newPuntiDiConsegna");
    }
    
	public boolean persistExtend(){
		
		System.out.println("NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
    	System.out.println(comuneProvinciaBean.getComune().getId());
		ComuneList comuneList = new ComuneList();
    	comuneList.setEjbql("select comune from Comune comune where comune.idcomune= "+ comuneProvinciaBean.getComune().getId());
    	Comune comune = comuneList.getResultList().get(0);
    	if(comune != null)
    	{
    		System.out.println("NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        	
    		puntiDiConsegna.setComune(comuneList.getResultList().get(0));
    		em.persist(puntiDiConsegna);
    	}
    	else
    		System.out.println("NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
    	
    	
    	
    	return true;
	}

    // add additional action methods
}
