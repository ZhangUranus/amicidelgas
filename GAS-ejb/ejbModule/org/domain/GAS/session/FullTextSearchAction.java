package org.domain.GAS.session;

import java.util.ArrayList;
import java.util.List;

import org.domain.GAS.entity.Articolo;
import org.domain.GAS.entity.Cybercontadino;
import org.domain.GAS.entity.Utente;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

@Name("fullTextSearch")
@Scope(ScopeType.SESSION)
public class FullTextSearchAction {
	
	@Out(scope = ScopeType.SESSION, required = false)
	private List<Utente> utenteSearchResults;
	@Out(scope = ScopeType.SESSION, required = false)
	private List<Cybercontadino> contadinoSearchResults;
	@Out(scope = ScopeType.SESSION, required = false)
	private List<Articolo> articoloSearchResults;
	private String searchString;

	private List<SearchOption> selectableEntities;
	
	
	public FullTextSearchAction(){
		selectableEntities= new ArrayList<SearchOption>();
		selectableEntities.add(new SearchUtente());
		selectableEntities.add(new SearchContadino());
		selectableEntities.add(new SearchArticolo());
	}
	
	
	public List<SearchOption> getSelectedEntities() {
		return selectedEntities;
	}

	public void setSelectedEntities(List<SearchOption> selectedEntities) {
		this.selectedEntities = selectedEntities;
	}

	private List<SearchOption> selectedEntities;
	
	
	public List<SearchOption> getSelectableEntities() {
		return selectableEntities;
	}

	public void setSelectableEntities(List<SearchOption> selectableEntities) {
		this.selectableEntities = selectableEntities;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public void doSearch() {
		List<SearchOption> targetList;
		if (selectedEntities.size()==0)
			targetList=selectableEntities;
		else
			targetList=selectedEntities;
		for (SearchOption option : targetList) {
			List results=option.search(searchString);
			//Remove to complete object-orientation
			if (option.getLabel().equals("Utente"))
				utenteSearchResults=results;
			else if (option.getLabel().equals("Contadino"))
				contadinoSearchResults=results;
			else if (option.getLabel().equals("Articolo"))
				articoloSearchResults=results;
			
				
		}
	}




}
