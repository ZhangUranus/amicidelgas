package org.domain.SeamAmiciDelGas.webservices;

import java.util.Date;
import java.util.UUID;


public interface CatalogInterface {

	public Item[] getItems(String idContadino);
	
	public String getDescription(String idContadino);
	
	public String[] getCategories(String idContadino);
	
	public Item[] getItemsForCategory(String idContadino, String category);
	
	public UUID beginTransaction(String idContadino, Date deliveryDate);
	
	public long getAvailableQuantity(String idContadino,Item item);
	
	public boolean reserveItem(String idContadino,UUID transactionId, Item item, int minimalQuantity, int quantity);
	
	public long rollBackTransaction(String idContadino,UUID transactionId);
	public long commitTransaction(String idContadino, UUID transactionId);
}
