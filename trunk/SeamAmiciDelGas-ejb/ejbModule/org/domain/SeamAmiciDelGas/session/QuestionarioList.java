package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("questionarioList")
public class QuestionarioList extends EntityQuery<Questionario> {

	private static final String EJBQL = "select questionario from Questionario questionario";

	private static final String[] RESTRICTIONS = { "lower(questionario.commenti) like concat(lower(#{questionarioList.questionario.commenti}),'%')", };

	private Questionario questionario = new Questionario();

	public QuestionarioList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Questionario getQuestionario() {
		return questionario;
	}
}
