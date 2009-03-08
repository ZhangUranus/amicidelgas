package org.domain.SeamAmiciDelGas.webservices;

import java.io.Serializable;

import org.domain.SeamAmiciDelGas.session.ShoppingCart.ItemQuantita;

public class ItemImpl implements Item, Serializable{

	private String category;
	private String description;
	private String id;
	private String name;
	private double prezzo;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	
	public boolean equals(Object o)
	{
		Item obj = (Item) o; 
		if(this.getId().equals(obj.getId())) //da completare
			return true;
		else
			return false;
	}
	
	

}
