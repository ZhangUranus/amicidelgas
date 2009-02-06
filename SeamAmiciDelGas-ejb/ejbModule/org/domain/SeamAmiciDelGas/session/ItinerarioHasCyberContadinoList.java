package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("itinerarioHasCyberContadinoList")
public class ItinerarioHasCyberContadinoList extends
		EntityQuery<ItinerarioHasCyberContadino> {

	private static final String EJBQL = "select itinerarioHasCyberContadino from ItinerarioHasCyberContadino itinerarioHasCyberContadino";

	private static final String[] RESTRICTIONS = {};

	private ItinerarioHasCyberContadino itinerarioHasCyberContadino;

	public ItinerarioHasCyberContadinoList() {
		itinerarioHasCyberContadino = new ItinerarioHasCyberContadino();
		itinerarioHasCyberContadino.setId(new ItinerarioHasCyberContadinoId());
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public ItinerarioHasCyberContadino getItinerarioHasCyberContadino() {
		return itinerarioHasCyberContadino;
	}
}
