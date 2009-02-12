package org.domain.SeamAmiciDelGas.crud;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("puntiDiConsegnaList")
public class PuntiDiConsegnaList extends EntityQuery<PuntiDiConsegna> {

	private static final String EJBQL = "select puntiDiConsegna from PuntiDiConsegna puntiDiConsegna";

	private static final String[] RESTRICTIONS = {
			"lower(puntiDiConsegna.indirizzo) like concat(lower(#{puntiDiConsegnaList.puntiDiConsegna.indirizzo}),'%')",
			"lower(puntiDiConsegna.coordinate) like concat(lower(#{puntiDiConsegnaList.puntiDiConsegna.coordinate}),'%')", };

	private PuntiDiConsegna puntiDiConsegna = new PuntiDiConsegna();

	public PuntiDiConsegnaList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public PuntiDiConsegna getPuntiDiConsegna() {
		return puntiDiConsegna;
	}
}
