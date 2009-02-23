package org.domain.SeamAmiciDelGas.webservices;

public class CatalogTester {

	public static void main(String[] args) {
		CatalogImpl c= new CatalogImpl();
		String[] categories = c.getCategories("prova");
		for(String category:categories)
			System.out.println(category);
	}
	
}
