package org.domain.GAS.crud;

import org.domain.GAS.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("patenteList")
public class PatenteList extends EntityQuery<Patente> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7790064233311692258L;

	private static final String EJBQL = "select patente from Patente patente";

	private static final String[] RESTRICTIONS = { "lower(patente.tipo) like concat(lower(#{patenteList.patente.tipo}),'%')", };

	private Patente patente = new Patente();

	public PatenteList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Patente getPatente() {
		return patente;
	}
}
