package org.domain.SeamAmiciDelGas.webservices;


public interface CatalogInterface {

	public Item[] getItems(String idContadino);
	
	public String getDescription(String idContadino);
	
	public String[] getCategories(String idContadino);
	
	public Item[] getItemsForCategory(String idContadino, String category);
	
	
}
