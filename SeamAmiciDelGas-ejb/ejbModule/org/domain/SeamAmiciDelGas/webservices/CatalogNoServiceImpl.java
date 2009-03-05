package org.domain.SeamAmiciDelGas.webservices;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

@Name("catalogService")
@Scope(ScopeType.SESSION)
public class CatalogNoServiceImpl implements CatalogInterface{

	@Out(value="categories") private List<String> categories;
	@Out(value="items") private List<Item> items;
	@Out(value="itemsForCategory") private List<Item> itemsForCategory;
	private Cybercontadino contadino;
	private String currentCategory;
	
	@Logger
	private Log log;
	
	public CatalogNoServiceImpl(){
		categories= new ArrayList<String>();
		categories.add("cibo");
		categories.add("bevande");
		ItemImpl t1= new ItemImpl();
		t1.setId("01");
		t1.setCategory("cibo");
		t1.setDescription("Formaggio fresco");
		t1.setName("Sottilette");
		t1.setPrezzo(2.50);
		ItemImpl t2= new ItemImpl();
		t2.setId("02");
		t2.setCategory("bevande");
		t2.setDescription("Direttamente dalla Sicilia");
		t2.setName("Nero d'avola");
		t2.setPrezzo(3.50);
		items= new ArrayList<Item>();
		items.add(t1);
		items.add(t2);
		itemsForCategory= new ArrayList<Item>();
	}
	
	public String[] getCategories(String idContadino) {
		
		return (String[]) categories.toArray(new String[categories.size()]);
	}

	public String getDescription(String idContadino) {
		return "Un semplice catalogo di prova";
	}

	public Item[] getItems(String idContadino) {
		return (Item[])items.toArray(new Item[items.size()]);
	}

	public Item[] getItemsForCategory(String idContadino, String category) {
		log.info("************DENTROOOO*********");
		Iterator<Item> it= items.iterator();
		while(it.hasNext()){
			Item item=it.next();
			if(item.getCategory().equalsIgnoreCase(category))
				itemsForCategory.add(item);
		}
		return (Item[]) itemsForCategory.toArray(new Item[itemsForCategory.size()]);
	}

	public Cybercontadino getContadino() {
		return contadino;
	}

	public void setContadino(Cybercontadino contadino) {
		this.contadino = contadino;
	}

	public String getCurrentCategory() {
		return currentCategory;
	}

	public void setCurrentCategory(String currentCategory) {
		this.currentCategory = currentCategory;
	}

}
