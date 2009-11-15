package org.domain.GAS.crud;

import org.domain.GAS.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("feedbackList") 
public class FeedbackList extends EntityQuery<Feedback> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2541349161312779386L;

	private static final String EJBQL = "select feedback from Feedback feedback";

	private static final String[] RESTRICTIONS = { "lower(feedback.descrizione) like concat(lower(#{feedbackList.feedback.descrizione}),'%')", };

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
