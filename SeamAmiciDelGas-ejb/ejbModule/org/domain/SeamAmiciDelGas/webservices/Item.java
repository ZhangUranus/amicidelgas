package org.domain.SeamAmiciDelGas.webservices;

public interface Item {

	public String getId();
	
	public String getName();
	
	public String getDescription();
	
	public String getCategory();
	
	public double getPrezzo();
	
	public boolean equals(Object o);
	
	public String getMinImageUrl();
	
	public String getMaxImageUrl();
	
	
}
