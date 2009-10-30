package org.domain.GAS.catalog;


import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;




public class CatalogImpl {

	public static Hashtable instances= new Hashtable();
	
	public static CatalogInterface getInstanceForContadino(String idContadino){
		if(idContadino == null)
			return null;
		return CatalogImpl.staticCatalog();
	}
	
	private static CatalogInterface staticCatalog(){
		return new CatalogNoServiceImpl();
	}
	
	
	
}
