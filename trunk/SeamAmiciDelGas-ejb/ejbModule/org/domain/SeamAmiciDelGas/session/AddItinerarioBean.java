package org.domain.SeamAmiciDelGas.session;

import java.util.Date;
import java.util.StringTokenizer;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.ScopeType;

@Name("addItinerario")
@Scope(value=ScopeType.PAGE)
public class AddItinerarioBean implements AddItinerario
{

    @In StatusMessages statusMessages;

    private String all_id;
    private Date dataPartenza;
    private String all_id_puntiConsegna;

    public String addItinerario()
    {
    	System.out.println("DATA: "+dataPartenza);
    	System.out.println("String: "+all_id+" : "+all_id_puntiConsegna);
        statusMessages.add("itinerario salvato correttamente");
        
        StringTokenizer st = new StringTokenizer(all_id, "|");
        while (st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }

        
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
