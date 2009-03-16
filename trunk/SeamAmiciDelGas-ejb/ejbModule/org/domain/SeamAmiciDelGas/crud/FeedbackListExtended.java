package org.domain.SeamAmiciDelGas.crud;

import java.util.Arrays;
import java.util.List;
import org.domain.SeamAmiciDelGas.entity.Feedback;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

@Name("newFeedbackListExtended")
public class FeedbackListExtended extends EntityQuery<Feedback>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3792575911239674625L;

	private static final String EJBQL = "select feedback from Feedback feedback ";

	private static final String[] RESTRICTIONS = { "feedback.accountByDestinatario.username= #{newFeedbackListExtended.feedback.accountByDestinatario.username}"};

	private Feedback feedback = new Feedback();

	public FeedbackListExtended() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		
	}
	
	
	public Feedback getFeedback() {
		return feedback;
	}

}
