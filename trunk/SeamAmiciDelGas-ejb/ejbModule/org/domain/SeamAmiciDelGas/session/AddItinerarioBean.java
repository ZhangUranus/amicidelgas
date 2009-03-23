package org.domain.SeamAmiciDelGas.session;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import javax.persistence.EntityManager;
import org.domain.SeamAmiciDelGas.crud.CybercontadinoList;
import org.domain.SeamAmiciDelGas.crud.ItinerarioHome;
import org.domain.SeamAmiciDelGas.crud.PuntiDiConsegnaList;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Itinerario;
import org.domain.SeamAmiciDelGas.entity.PuntiDiConsegna;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.ScopeType;

@Name("addItinerario")
@Scope(value=ScopeType.SESSION)
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

    public String addItinerario()
    {
    	System.out.println("DATA: "+dataPartenza);
    	System.out.println("String: "+all_id+" : "+all_id_puntiConsegna);
        statusMessages.add("itinerario salvato correttamente");
        Itinerario itinerario = new Itinerario();
        Set<Cybercontadino> contadini = new HashSet<Cybercontadino>(0);
        StringTokenizer st = new StringTokenizer(all_id);
        while (st.hasMoreTokens()) {
        	cybercontadinolist.setEjbql("select contadino from Cybercontadino contadino where contadino.codiceContadino= "+ st.nextToken().trim());
        	System.out.println("Contadini belli");
        	contadini.add(cybercontadinolist.getSingleResult());
        	System.out.println("Contadini belli 2");
        }
        itinerario.setCybercontadinos(contadini);
        System.out.println("size contadini: "+contadini.size());
        
        Set<PuntiDiConsegna> puntiDiConsegnas = new HashSet<PuntiDiConsegna>(0);
        StringTokenizer st_consegna = new StringTokenizer(all_id_puntiConsegna);
        while (st_consegna.hasMoreTokens()) {
        	System.out.println("punti belli");
        	puntiDiConsegnaList.setEjbql("select punti from PuntiDiConsegna punti where punti.idpuntiConsegna= "+ st_consegna.nextToken().trim());
        	puntiDiConsegnas.add(puntiDiConsegnaList.getSingleResult());
        }
        itinerario.setPuntiDiConsegnas(puntiDiConsegnas);
        System.out.println("size punti: "+puntiDiConsegnas.size());
        
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dataPartenza);
        gc.add(Calendar.HOUR_OF_DAY, -3);
        itinerario.setDataPartenza(gc.getTime());
        itinerario.setDataConsegna(dataPartenza);
        itinerario.setDataCreazione(new Date(System.currentTimeMillis()));
        itinerario.setAccount(account);
        
        itinerarioHome.setInstance(itinerario);
        itinerarioHome.persist();
        
        return "OutcomeItinerario";
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
