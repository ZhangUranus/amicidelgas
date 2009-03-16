package org.domain.SeamAmiciDelGas.crud;

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

	private static final String EJBQL = "select feedback from Feedback feedback where feedback.accountByDestinatario.username=";

	//private static final String[] RESTRICTIONS = { "lower(feedback.descrizione) like concat(lower(#{feedbackList.feedback.descrizione}),'%')", };

	//private Feedback feedback = new Feedback();

	public FeedbackListExtended() {
		/*setEjbql(EJBQL);
		//setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);*/
	}
	
	public List<Feedback> getPunteggioFeedback(String username)
	{
		setEjbql(EJBQL.concat("'"+username+"'"));
		return getResultList();
		
	}

}
