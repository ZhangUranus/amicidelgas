package org.domain.seamgas.session;

import org.domain.seamgas.entity.*;
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
			"lower(cybercontadino.pathQuestionario) like concat(lower(#{cybercontadinoList.cybercontadino.pathQuestionario}),'%')",
			"lower(cybercontadino.urlWisdl) like concat(lower(#{cybercontadinoList.cybercontadino.urlWisdl}),'%')",
			"lower(cybercontadino.descrizioneAzienda) like concat(lower(#{cybercontadinoList.cybercontadino.descrizioneAzienda}),'%')", };

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
