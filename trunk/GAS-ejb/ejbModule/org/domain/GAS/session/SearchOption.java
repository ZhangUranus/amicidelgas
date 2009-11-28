package org.domain.GAS.session;

import java.util.List;

public interface SearchOption {
	

	public String getLabel();
	
	public List search(String searchString);

}
