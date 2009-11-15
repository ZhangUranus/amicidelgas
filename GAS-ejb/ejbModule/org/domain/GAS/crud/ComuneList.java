package org.domain.GAS.crud;

import org.domain.GAS.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("comuneList")
public class ComuneList extends EntityQuery<Comune> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1393209792450289449L;

	private static final String EJBQL = "select comune from Comune comune";

	private static final String[] RESTRICTIONS = { "lower(comune.nome) like concat(lower(#{comuneList.comune.nome}),'%')", };

	private Comune comune = new Comune();

	public ComuneList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(10000);
	}

	public Comune getComune() {
		return comune;
	}
}
