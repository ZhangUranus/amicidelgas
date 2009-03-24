package org.domain.SeamAmiciDelGas.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.domain.SeamAmiciDelGas.crud.ComuneList;
import org.domain.SeamAmiciDelGas.entity.PuntiDiConsegna;
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
		
		ComuneList comuneList = new ComuneList();
    	comuneList.setEjbql("select comune from Comune comune where comune.idcomune= "+ comuneProvinciaBean.getComune().getId());

    	puntiDiConsegna.setComune(comuneList.getResultList().get(0));
    	
    	em.persist(puntiDiConsegna);
    	return true;
	}

    // add additional action methods
}
