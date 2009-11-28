package org.domain.GAS.session;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.domain.GAS.entity.Cybercontadino;
import org.domain.GAS.entity.Utente;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.jboss.seam.Component;

public class SearchContadino implements SearchOption{
	
	public String getLabel() {
		return "Contadino";
	}

	public List search(String searchString) {
		Query luceneQuery = null;
		FullTextEntityManager fem = (FullTextEntityManager) Component.getInstance("entityManager");
		SearchFactory sf=fem.getSearchFactory();
		Analyzer analyzer = sf.getAnalyzer(Cybercontadino.class);
		try {
			/*luceneQuery = new QueryParser("nomeCompleto",
					new StandardAnalyzer()).parse(searchString);
			*/
			//snowball
			luceneQuery= new QueryParser("descrizioneAzienda", analyzer).parse(searchString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		FullTextQuery query = fem
				.createFullTextQuery(luceneQuery, Cybercontadino.class);
		List<Cybercontadino> searchResults = (List<Cybercontadino>) query.getResultList();
		ArrayList<Cybercontadino> contadinoSearchResults = new ArrayList<Cybercontadino>();

		for (Cybercontadino u : searchResults) {
			if (u != null)
				contadinoSearchResults.add(u);
		}
		return contadinoSearchResults;
	}
	
	public boolean equals(Object o){
		return true;
	}



}
