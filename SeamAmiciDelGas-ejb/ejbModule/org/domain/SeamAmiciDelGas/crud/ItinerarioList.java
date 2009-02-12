package org.domain.SeamAmiciDelGas.crud;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("itinerarioList")
public class ItinerarioList extends EntityQuery<Itinerario> {

	private static final String EJBQL = "select itinerario from Itinerario itinerario";

	private static final String[] RESTRICTIONS = { "lower(itinerario.documentazionePath) like concat(lower(#{itinerarioList.itinerario.documentazionePath}),'%')", };

	private Itinerario itinerario = new Itinerario();

	public ItinerarioList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Itinerario getItinerario() {
		return itinerario;
	}
}
