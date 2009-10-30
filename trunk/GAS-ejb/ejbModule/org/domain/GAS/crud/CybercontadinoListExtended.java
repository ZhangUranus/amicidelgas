package org.domain.GAS.crud;

import org.domain.GAS.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("cybercontadinoListExtended")
public class CybercontadinoListExtended extends EntityQuery<Cybercontadino> {

	private static final String EJBQL = "select cybercontadino from Cybercontadino cybercontadino";

	private static final String[] RESTRICTIONS = {
			"lower(cybercontadino.account.username) like concat(lower(#{accountList.account.username}),'%')",//
			"lower(cybercontadino.nomePresidente) like concat(lower(#{cybercontadinoListExtended.cybercontadino.nomePresidente}),'%')",
			"lower(cybercontadino.indirizzo) like concat(lower(#{cybercontadinoListExtendedExtended.cybercontadino.indirizzo}),'%')",
			"lower(cybercontadino.cognomePresidente) like concat(lower(#{cybercontadinoListExtended.cybercontadino.cognomePresidente}),'%')",
			"lower(cybercontadino.nomeAzienda) like concat(lower(#{cybercontadinoListExtended.cybercontadino.nomeAzienda}),'%')",
			"lower(cybercontadino.pathAsl) like concat(lower(#{cybercontadinoListExtended.cybercontadino.pathAsl}),'%')",
			"lower(cybercontadino.descrizioneAzienda) like concat(lower(#{cybercontadinoListExtended.cybercontadino.descrizioneAzienda}),'%')",
			"lower(cybercontadino.email) like concat(lower(#{cybercontadinoListExtended.cybercontadino.email}),'%')",
			"lower(cybercontadino.coordinate) like concat(lower(#{cybercontadinoListExtended.cybercontadino.coordinate}),'%')", };

	private Cybercontadino cybercontadino = new Cybercontadino();

	public CybercontadinoListExtended() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Cybercontadino getCybercontadino() {
		return cybercontadino;
	}
}
