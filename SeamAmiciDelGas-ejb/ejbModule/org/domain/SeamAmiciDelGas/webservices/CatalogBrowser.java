package org.domain.SeamAmiciDelGas.webservices;

import java.util.ArrayList;
import java.util.List;

import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
@Name("catalogBrowser")
@Scope(ScopeType.SESSION)
public class CatalogBrowser{
	
	@Out(value="contadinoSelezionato",scope=ScopeType.SESSION, required=false)
	private Cybercontadino contadino;
	private String category;
	public CatalogBrowser(){
		
	}
	
	public List<String> getCategories(){
		if(contadino==null)
			return null;
		CatalogInterface catalog= CatalogImpl.getInstanceForContadino(contadino.getPartitaIva());
		if(catalog==null)
			return new ArrayList<String>();
		return catalog.getCategories();
		
		
	}

	public String getDescription() {
		if(contadino==null)
			return null;
		
		CatalogInterface catalog= CatalogImpl.getInstanceForContadino(contadino.getPartitaIva());
		return catalog.getDescription();
	}

	public List<Item> getItems() {
		if(contadino==null)
			return null;
		
		CatalogInterface catalog= CatalogImpl.getInstanceForContadino(contadino.getPartitaIva());
		if(catalog==null)
			return new ArrayList<Item>();
		return catalog.getItems();
	
	}

	public List<Item> getItemsForCategory(String category) {
		if(contadino==null)
			return null;
		if(category.length()==0)
			return null;
		
		CatalogInterface catalog= CatalogImpl.getInstanceForContadino(contadino.getPartitaIva());
		return catalog.getItemsForCategory(category);
	}

	public Cybercontadino getContadino() {
		return contadino;
	}

	public void setContadino(Cybercontadino contadino) {
		this.contadino = contadino;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getAvailableQuantity(Item item) {
		if(contadino==null)
			return 0;
		
		CatalogInterface catalog= CatalogImpl.getInstanceForContadino(contadino.getPartitaIva());
		return catalog.getAvailableQuantity(item);
	}

	
}
