package org.domain.GAS.session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.domain.GAS.crud.ComuneList;
import org.domain.GAS.entity.Comune;
import org.domain.GAS.entity.Provinces;
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

	public String getControlloComuneNascita() {
		return controlloComuneNascita;
	}

	public void setControlloComuneNascita(String controlloComuneNascita) {
		this.controlloComuneNascita = controlloComuneNascita;
	}

	private List<Comune> autoCompleteList;
	private myComuneBean comune;
	private Provinces provincia;
	private String controlloComuneNascita;
	
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
	
	public List<myComuneBean> autocompleteForBenevento(Object suggest) {
		
        comuneList.setEjbql("select comune from Comune comune where comune.provinces.nome='Benevento'");
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
		if(comune != null)
			this.comune = comune;
		//controlloComuneNascita = controllaComune(comune.nome);
		
	}

	public boolean isSelectedProvincia() {
		return provincia!=null;
	}

	public void setSelectedProvincia(boolean selectedProvincia) {
		this.selectedProvincia = selectedProvincia;
	}

	
}
