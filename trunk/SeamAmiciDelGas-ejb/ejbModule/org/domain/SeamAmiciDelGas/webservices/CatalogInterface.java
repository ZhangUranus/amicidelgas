package org.domain.SeamAmiciDelGas.webservices;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.jws.WebService;

@WebService
public interface CatalogInterface extends java.rmi.Remote{

	public List<Item> getItems();
	
	public String getDescription();
	
	public List<String> getCategories();
	
	public List<Item> getItemsForCategory(String category);
	
	public String beginTransaction(Date deliveryDate);
	
	public long getAvailableQuantity(Item item);
	
	public int reserveItem(String transactionId, Item item, int minimalQuantity, int quantity);
	
	public long rollBackTransaction(String transactionId);
	
	public long commitTransaction(String transactionId);
}
