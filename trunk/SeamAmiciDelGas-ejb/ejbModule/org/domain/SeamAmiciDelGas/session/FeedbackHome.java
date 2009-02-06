package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("feedbackHome")
public class FeedbackHome extends EntityHome<Feedback> {

	public void setFeedbackIdfeedback(Integer id) {
		setId(id);
	}

	public Integer getFeedbackIdfeedback() {
		return (Integer) getId();
	}

	@Override
	protected Feedback createInstance() {
		Feedback feedback = new Feedback();
		return feedback;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Feedback getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
