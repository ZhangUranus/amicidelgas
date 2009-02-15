package org.domain.SeamAmiciDelGas.crud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import org.domain.SeamAmiciDelGas.entity.Comune;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;



@Name("newComuneAutoCompleteBean")
public class ComuneAutoCompleteBean implements ValueChangeListener{
	
	
	@Logger
	private Log log;
	@In(value="comuneList",create=true)
	private ComuneList comuneList;
	private List<Comune> autoCompleteList;
	
	private String nomeProvincia;
	
	public String getNomeProvincia() {
		return nomeProvincia;
	}

	public void setNomeProvincia(String nomeProvincia) {
		this.nomeProvincia = nomeProvincia;
	}

	public ComuneAutoCompleteBean(){
		
	}
	
	public void updateProvincia(ValueChangeEvent event){
		
	}
	
	public List<myComuneBean> autocomplete(Object suggest) {
        if(autoCompleteList==null){
        	comuneList.setEjbql("select comune from Comune comune where comune.provinces.nome="+nomeProvincia);
        	autoCompleteList= comuneList.getResultList();
        }
		String pref = (String)suggest;
        
        ArrayList<myComuneBean> result = new ArrayList<myComuneBean>();

        Iterator<Comune> iterator = autoCompleteList.iterator();
        while (iterator.hasNext()) {
            Comune elem =  iterator.next();
            if ((elem.getNome() != null && elem.getNome().toLowerCase().indexOf(pref.toLowerCase()) == 0) || "".equals(pref))
            {
            	myComuneBean comune= new myComuneBean();
            	comune.setId(elem.getCap());
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

	public void processValueChange(ValueChangeEvent arg0)
			throws AbortProcessingException {
		nomeProvincia=(String) event.getNewValue();
		comuneList.setEjbql("select comune from Comune comune where comune.provinces.nome="+nomeProvincia);
    	autoCompleteList= comuneList.getResultList();
    	log.info("Updated provincia nome with value"+ nomeProvincia );
		
	}
	
}
