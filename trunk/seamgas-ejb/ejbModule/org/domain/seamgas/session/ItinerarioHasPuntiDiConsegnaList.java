package org.domain.seamgas.session;

import org.domain.seamgas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("itinerarioHasPuntiDiConsegnaList")
public class ItinerarioHasPuntiDiConsegnaList extends
		EntityQuery<ItinerarioHasPuntiDiConsegna> {

	private static final String EJBQL = "select itinerarioHasPuntiDiConsegna from ItinerarioHasPuntiDiConsegna itinerarioHasPuntiDiConsegna";

	private static final String[] RESTRICTIONS = {};

	private ItinerarioHasPuntiDiConsegna itinerarioHasPuntiDiConsegna;

	public ItinerarioHasPuntiDiConsegnaList() {
		itinerarioHasPuntiDiConsegna = new ItinerarioHasPuntiDiConsegna();
		itinerarioHasPuntiDiConsegna
				.setId(new ItinerarioHasPuntiDiConsegnaId());
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public ItinerarioHasPuntiDiConsegna getItinerarioHasPuntiDiConsegna() {
		return itinerarioHasPuntiDiConsegna;
	}
}
