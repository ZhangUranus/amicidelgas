package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import org.domain.SeamAmiciDelGas.crud.ComuneList;
import org.domain.SeamAmiciDelGas.entity.Comune;
import org.domain.SeamAmiciDelGas.entity.Provinces;
import org.hibernate.validator.Length;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Role;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;



@Name("newComuneProvinciaBean")
@Scope(ScopeType.SESSION)
@Role(name="newComuneProvinciaResidenzaBean",scope=ScopeType.SESSION)
public class ComuneProvinciaBean{
	
	
	@Logger
	private Log log;
	@In(value="comuneList",create=true)
	private ComuneList comuneList;
	private List<Comune> autoCompleteList;
	private myComuneBean comune;
	private Provinces provincia;
	private boolean selectedProvincia=false;
	public Provinces getProvincia() {
		return this.provincia;
	}

	public void setProvincia(Provinces provincia) {
		this.provincia = provincia;
	}

	public ComuneProvinciaBean(){
		
	}
	
	public List<myComuneBean> autocomplete(Object suggest) {
		if (provincia==null)
			return null;
		log.info("Inizia l'autocomplete -la provincia selezionata � "+ provincia.getNome());
        comuneList.setEjbql("select comune from Comune comune where comune.provinces.nome='"+provincia.getNome()+"'");
        autoCompleteList= comuneList.getResultList();
        String pref = (String)suggest;
        
        ArrayList<myComuneBean> result = new ArrayList<myComuneBean>();

        Iterator<Comune> iterator = autoCompleteList.iterator();
        while (iterator.hasNext()) {
            Comune elem =  iterator.next();
            if ((elem.getNome() != null && elem.getNome().toLowerCase().indexOf(pref.toLowerCase()) == 0) || "".equals(pref))
            {
            	myComuneBean comune= new myComuneBean();
            	comune.setId(elem.getIdcomune());
            	comune.setNome(elem.getNome());
                result.add(comune);
            }
        }
        return result;
    }
	
	public class myComuneBean{
		private Integer id;
		private String nome;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		
	}

	public myComuneBean getComune() {
		return comune;
	}

	public void setComune(myComuneBean comune) {
		log.info("E' stato selezionato un comune - il comune attuale � " +comune.getNome());
		this.comune = comune;
	}

	public boolean isSelectedProvincia() {
		return provincia!=null;
	}

	public void setSelectedProvincia(boolean selectedProvincia) {
		log.info("E' stata selezionata una provincia - la provincia attuale �: "+ provincia.getNome());
		this.selectedProvincia = selectedProvincia;
	}

	
}
