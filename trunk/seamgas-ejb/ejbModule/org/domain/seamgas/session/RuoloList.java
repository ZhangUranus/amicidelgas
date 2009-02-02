package org.domain.seamgas.session;

import org.domain.seamgas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("ruoloList")
public class RuoloList extends EntityQuery<Ruolo> {

	private static final String EJBQL = "select ruolo from Ruolo ruolo";

	private static final String[] RESTRICTIONS = { "lower(ruolo.tipo) like concat(lower(#{ruoloList.ruolo.tipo}),'%')", };

	private Ruolo ruolo = new Ruolo();

	public RuoloList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Ruolo getRuolo() {
		return ruolo;
	}
}
