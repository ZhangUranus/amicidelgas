package org.domain.seamgas.session;

import org.domain.seamgas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("ordineList")
public class OrdineList extends EntityQuery<Ordine> {

	private static final String EJBQL = "select ordine from Ordine ordine";

	private static final String[] RESTRICTIONS = {};

	private Ordine ordine = new Ordine();

	public OrdineList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Ordine getOrdine() {
		return ordine;
	}
}
