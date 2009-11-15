package org.domain.GAS.catalog;

import java.io.Serializable;

public class Item implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4058107148252910325L;
	private String category;
	private String description;
	private String id;
	private String name;
	private double prezzo;
	private String smallImageUri;
	public Item() {
		
	}
	


	public String getCategory() {
		return category;
	}



	public String getDescription() {
		return description;
	}



	public String getId() {
		return id;
	}



	public String getName() {
		return name;
	}



	public double getPrezzo() {
		return prezzo;
	}



	public void setCategory(String category) {
		this.category = category;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}



	public String getSmallImageUri() {
		return smallImageUri;
	}



	public void setSmallImageUri(String smallImageUri) {
		this.smallImageUri = smallImageUri;
	}
	

}
