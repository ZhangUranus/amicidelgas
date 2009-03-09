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
	@Out(value="listaProdotti") private List<Item> listaProdotti;
	//@Out(value="itemsForCategory") private List<Item> itemsForCategory;
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
		
		ItemImpl t3= new ItemImpl();
		t3.setId("03");
		t3.setCategory("bevande");
		t3.setDescription("Sempre sicilia");
		t3.setName("Zibibbo");
		t3.setPrezzo(4.00);
		
		ItemImpl t4= new ItemImpl();
		t4.setId("04");
		t4.setCategory("bevande");
		t4.setDescription("Vino Sardu");
		t4.setName("Nuragus");
		t4.setPrezzo(2.25);
		
		ItemImpl t5= new ItemImpl();
		t5.setId("05");
		t5.setCategory("cibo");
		t5.setDescription("Panino con la frittataa");
		t5.setName("Panino");
		t5.setPrezzo(3.75);
		
		listaProdotti= new ArrayList<Item>();
		listaProdotti.add(t1);
		listaProdotti.add(t2);
		listaProdotti.add(t3);
		listaProdotti.add(t4);
		listaProdotti.add(t5);
		//itemsForCategory= new ArrayList<Item>();
	}
	
	public String[] getCategories(String idContadino) {
		return (String[]) categories.toArray(new String[categories.size()]);
	}

	public String getDescription(String idContadino) {
		return "Un semplice catalogo di prova";
	}

	public Item[] getItems(String idContadino) {
		return (Item[])listaProdotti.toArray(new Item[listaProdotti.size()]);
	}

	public Item[] getItemsForCategory(String idContadino, String category) {
		List<Item> newItem = new ArrayList<Item>();
		for(Item it : listaProdotti)
			if(it.getCategory().equalsIgnoreCase(category))
				newItem.add(it);
		
		return (Item[]) newItem.toArray(new Item[newItem.size()]);
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
