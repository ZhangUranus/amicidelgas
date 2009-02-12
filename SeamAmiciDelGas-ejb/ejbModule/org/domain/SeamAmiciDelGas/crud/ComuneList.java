package org.domain.SeamAmiciDelGas.crud;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("comuneList")
public class ComuneList extends EntityQuery<Comune> {

	private static final String EJBQL = "select comune from Comune comune";

	private static final String[] RESTRICTIONS = { "lower(comune.nome) like concat(lower(#{comuneList.comune.nome}),'%')", };

	private Comune comune = new Comune();

	public ComuneList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Comune getComune() {
		return comune;
	}
}
