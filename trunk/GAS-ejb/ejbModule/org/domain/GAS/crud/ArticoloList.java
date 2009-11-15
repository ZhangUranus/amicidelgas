package org.domain.GAS.crud;

import org.domain.GAS.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("articoloList")
public class ArticoloList extends EntityQuery<Articolo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7759394893271549593L;

	private static final String EJBQL = "select articolo from Articolo articolo";

	private static final String[] RESTRICTIONS = {
			"lower(articolo.codiceEsterno) like concat(lower(#{articoloList.articolo.codiceEsterno}),'%')",
			"lower(articolo.descrizione) like concat(lower(#{articoloList.articolo.descrizione}),'%')", };

	private Articolo articolo = new Articolo();

	public ArticoloList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Articolo getArticolo() {
		return articolo;
	}
}
