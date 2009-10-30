package org.domain.GAS.session;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import javax.persistence.EntityManager;

import org.domain.GAS.crud.CybercontadinoList;
import org.domain.GAS.crud.ItinerarioHome;
import org.domain.GAS.crud.PuntiDiConsegnaList;
import org.domain.GAS.entity.Account;
import org.domain.GAS.entity.Cybercontadino;
import org.domain.GAS.entity.Itinerario;
import org.domain.GAS.entity.PuntiDiConsegna;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.ScopeType;

@Name("addItinerario")
@Scope(value=ScopeType.PAGE)
public class AddItinerarioBean implements AddItinerario
{

    @In StatusMessages statusMessages;
    

    @In(value="entityManager")
    private EntityManager em;
    
    @In(value="currentAccount", required=false)
    private Account account;
    @In(value="cybercontadinoList",create=true)
	private CybercontadinoList cybercontadinolist;
    @In(value="puntiDiConsegnaList",create=true)
	private PuntiDiConsegnaList puntiDiConsegnaList;
    
    @In(value="itinerarioHome", create=true)
    private ItinerarioHome itinerarioHome;
    
    //@In(value="creaItinerario",create=true)
	//private Itinerario itinerario;
    
    private String all_id;
    private Date dataPartenza;
    private String all_id_puntiConsegna;
    
    @Transactional public boolean aggiungiItinerario()
    {
    	Itinerario itinerario = new Itinerario();
        Set<Cybercontadino> contadini = new HashSet<Cybercontadino>(0);
        StringTokenizer st = new StringTokenizer(all_id);
        while (st.hasMoreTokens()) {
        	cybercontadinolist.setEjbql("select contadino from Cybercontadino contadino where contadino.codiceContadino= "+ st.nextToken().trim());
        	contadini.add(cybercontadinolist.getSingleResult());
        }
        itinerario.setCybercontadinos(contadini);
        
        Set<PuntiDiConsegna> puntiDiConsegnas = new HashSet<PuntiDiConsegna>(0);
        StringTokenizer st_consegna = new StringTokenizer(all_id_puntiConsegna);
        while (st_consegna.hasMoreTokens()) {
        	puntiDiConsegnaList.setEjbql("select punti from PuntiDiConsegna punti where punti.idpuntiConsegna= "+ st_consegna.nextToken().trim());
        	puntiDiConsegnas.add(puntiDiConsegnaList.getSingleResult());
        }
        itinerario.setPuntiDiConsegnas(puntiDiConsegnas);
        
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dataPartenza);
        gc.add(Calendar.HOUR_OF_DAY, -3);
        itinerario.setDataPartenza(gc.getTime());
        itinerario.setDataConsegna(dataPartenza);
        itinerario.setDataCreazione(new Date(System.currentTimeMillis()));
        itinerario.setAccount(account);
        
        itinerarioHome.setInstance(itinerario);
        itinerarioHome.persist();
    	return true;  
    	
    }

    public String addItinerario()
    {
    	Date dataCorrente = new Date(System.currentTimeMillis());
    	if(dataPartenza.after(dataCorrente))
    	{
    		if(this.aggiungiItinerario())
    			return "OutcomeItinerario";
    		else

    			return null;
    	}
        return null;
    }
    

	public String getAll_id() {
		return all_id;
	}

	public void setAll_id(String all_id) {
		this.all_id = all_id;
	}

	public Date getDataPartenza() {
		return dataPartenza;
	}

	public void setDataPartenza(Date date) {
		this.dataPartenza = date;
	}

	public String getAll_id_puntiConsegna() {
		return all_id_puntiConsegna;
	}

	public void setAll_id_puntiConsegna(String all_id_puntiConsegna) {
		this.all_id_puntiConsegna = all_id_puntiConsegna;
	}
}
