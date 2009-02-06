package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("feedbackList")
public class FeedbackList extends EntityQuery<Feedback> {

	private static final String EJBQL = "select feedback from Feedback feedback";

	private static final String[] RESTRICTIONS = {
			"lower(feedback.validatore) like concat(lower(#{feedbackList.feedback.validatore}),'%')",
			"lower(feedback.segnalatore) like concat(lower(#{feedbackList.feedback.segnalatore}),'%')",
			"lower(feedback.destinatario) like concat(lower(#{feedbackList.feedback.destinatario}),'%')",
			"lower(feedback.descrizione) like concat(lower(#{feedbackList.feedback.descrizione}),'%')", };

	private Feedback feedback = new Feedback();

	public FeedbackList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Feedback getFeedback() {
		return feedback;
	}
}
