package org.domain.SeamAmiciDelGas.crud;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("cybercontadinoList")
public class CybercontadinoList extends EntityQuery<Cybercontadino> {

	private static final String EJBQL = "select cybercontadino from Cybercontadino cybercontadino";

	private static final String[] RESTRICTIONS = {
			"lower(cybercontadino.nomePresidente) like concat(lower(#{cybercontadinoList.cybercontadino.nomePresidente}),'%')",
			"lower(cybercontadino.indirizzo) like concat(lower(#{cybercontadinoList.cybercontadino.indirizzo}),'%')",
			"lower(cybercontadino.cognomePresidente) like concat(lower(#{cybercontadinoList.cybercontadino.cognomePresidente}),'%')",
			"lower(cybercontadino.nomeAzienda) like concat(lower(#{cybercontadinoList.cybercontadino.nomeAzienda}),'%')",
			"lower(cybercontadino.pathAsl) like concat(lower(#{cybercontadinoList.cybercontadino.pathAsl}),'%')",
			"lower(cybercontadino.urlWsdl) like concat(lower(#{cybercontadinoList.cybercontadino.urlWsdl}),'%')",
			"lower(cybercontadino.descrizioneAzienda) like concat(lower(#{cybercontadinoList.cybercontadino.descrizioneAzienda}),'%')",
			"lower(cybercontadino.email) like concat(lower(#{cybercontadinoList.cybercontadino.email}),'%')",
			"lower(cybercontadino.coordinate) like concat(lower(#{cybercontadinoList.cybercontadino.coordinate}),'%')", };

	private Cybercontadino cybercontadino = new Cybercontadino();

	public CybercontadinoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Cybercontadino getCybercontadino() {
		return cybercontadino;
	}
}
