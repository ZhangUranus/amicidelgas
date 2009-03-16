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

	@Out(value="categories", required=false) private List<String> categories;
	@Out(value="items", required=false) private List<Item> items;
	@Out(value="itemsForCategory", required=false) private List<Item> itemsForCategory;
	private Cybercontadino contadino;
	private String category;
	public CatalogBrowser(){
		
	}
	
	public List<String> getCategories(){
		CatalogInterface catalog= CatalogImpl.getInstanceForContadino(contadino.getPartitaIva());
		if(catalog==null)
			return new ArrayList<String>();
		return catalog.getCategories();
		
		
	}

	public String getDescription() {
		CatalogInterface catalog= CatalogImpl.getInstanceForContadino(contadino.getPartitaIva());
		return catalog.getDescription();
	}

	public List<Item> getItems() {
		CatalogInterface catalog= CatalogImpl.getInstanceForContadino(contadino.getPartitaIva());
		return catalog.getItems();
	
	}

	public List<Item> getItemsForCategory(String category) {
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
		CatalogInterface catalog= CatalogImpl.getInstanceForContadino(contadino.getPartitaIva());
		return catalog.getAvailableQuantity(item);
	}

	
}
