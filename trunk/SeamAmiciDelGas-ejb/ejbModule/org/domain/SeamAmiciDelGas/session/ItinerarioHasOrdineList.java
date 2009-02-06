package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("itinerarioHasOrdineList")
public class ItinerarioHasOrdineList extends EntityQuery<ItinerarioHasOrdine> {

	private static final String EJBQL = "select itinerarioHasOrdine from ItinerarioHasOrdine itinerarioHasOrdine";

	private static final String[] RESTRICTIONS = {};

	private ItinerarioHasOrdine itinerarioHasOrdine;

	public ItinerarioHasOrdineList() {
		itinerarioHasOrdine = new ItinerarioHasOrdine();
		itinerarioHasOrdine.setId(new ItinerarioHasOrdineId());
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public ItinerarioHasOrdine getItinerarioHasOrdine() {
		return itinerarioHasOrdine;
	}
}
