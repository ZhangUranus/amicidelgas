package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("cyberContadinoList")
public class CyberContadinoList extends EntityQuery<CyberContadino> {

	private static final String EJBQL = "select cyberContadino from CyberContadino cyberContadino";

	private static final String[] RESTRICTIONS = {
			"lower(cyberContadino.username) like concat(lower(#{cyberContadinoList.cyberContadino.username}),'%')",
			"lower(cyberContadino.nomePresidente) like concat(lower(#{cyberContadinoList.cyberContadino.nomePresidente}),'%')",
			"lower(cyberContadino.indirizzo) like concat(lower(#{cyberContadinoList.cyberContadino.indirizzo}),'%')",
			"lower(cyberContadino.cognomePresidente) like concat(lower(#{cyberContadinoList.cyberContadino.cognomePresidente}),'%')",
			"lower(cyberContadino.nomeAzienda) like concat(lower(#{cyberContadinoList.cyberContadino.nomeAzienda}),'%')",
			"lower(cyberContadino.pathAsl) like concat(lower(#{cyberContadinoList.cyberContadino.pathAsl}),'%')",
			"lower(cyberContadino.urlWsdl) like concat(lower(#{cyberContadinoList.cyberContadino.urlWsdl}),'%')",
			"lower(cyberContadino.descrizioneAzienda) like concat(lower(#{cyberContadinoList.cyberContadino.descrizioneAzienda}),'%')",
			"lower(cyberContadino.email) like concat(lower(#{cyberContadinoList.cyberContadino.email}),'%')", };

	private CyberContadino cyberContadino = new CyberContadino();

	public CyberContadinoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public CyberContadino getCyberContadino() {
		return cyberContadino;
	}
}
