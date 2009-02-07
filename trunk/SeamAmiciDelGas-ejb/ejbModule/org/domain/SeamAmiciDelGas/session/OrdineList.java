package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("ordineList")
public class OrdineList extends EntityQuery<Ordine> {

	private static final String EJBQL = "select ordine from Ordine ordine";

	private static final String[] RESTRICTIONS = { "lower(ordine.username) like concat(lower(#{ordineList.ordine.username}),'%')", };

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
