package org.domain.GAS.session;

import java.util.List;

import org.domain.GAS.crud.ArticoloList;
import org.domain.GAS.crud.CybercontadinoList;
import org.domain.GAS.crud.UtenteList;
import org.domain.GAS.entity.Articolo;
import org.domain.GAS.entity.Cybercontadino;
import org.domain.GAS.entity.Utente;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;

@Name("makeIndex")
@Scope(ScopeType.APPLICATION)
@Startup
public class MakeIndex {
	
	@Create
	public void index()
	{
		FullTextEntityManager fem = (FullTextEntityManager) Component.getInstance("entityManager");
		UtenteList list = new UtenteList();
		List<Utente> lista = list.getResultList();
		for (Utente u : lista)
			fem.index(u);
		ArticoloList listArticolo = new ArticoloList();
		List<Articolo> listaArticolo = listArticolo.getResultList();
		for (Articolo u : listaArticolo)
			fem.index(u);
		CybercontadinoList listContadino = new CybercontadinoList();
		List<Cybercontadino> listaContadino = listContadino.getResultList();
		for (Cybercontadino u : listaContadino)
			fem.index(u);
	}

}
