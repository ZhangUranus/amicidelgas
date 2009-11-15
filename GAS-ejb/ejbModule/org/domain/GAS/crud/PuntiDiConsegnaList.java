package org.domain.GAS.crud;

import org.domain.GAS.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("puntiDiConsegnaList")
public class PuntiDiConsegnaList extends EntityQuery<PuntiDiConsegna> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3716900403516974277L;

	private static final String EJBQL = "select puntiDiConsegna from PuntiDiConsegna puntiDiConsegna";

	private static final String[] RESTRICTIONS = {
			"lower(puntiDiConsegna.indirizzo) like concat(lower(#{puntiDiConsegnaList.puntiDiConsegna.indirizzo}),'%')", };

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
