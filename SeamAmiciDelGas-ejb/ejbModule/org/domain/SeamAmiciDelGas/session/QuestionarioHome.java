package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("questionarioHome")
public class QuestionarioHome extends EntityHome<Questionario> {

	@In(create = true)
	AccountHome accountHome;
	@In(create = true)
	CyberContadinoHome cyberContadinoHome;

	public void setQuestionarioIdquestionario(Integer id) {
		setId(id);
	}

	public Integer getQuestionarioIdquestionario() {
		return (Integer) getId();
	}

	@Override
	protected Questionario createInstance() {
		Questionario questionario = new Questionario();
		return questionario;
	}

	public void wire() {
		getInstance();
		Account account = accountHome.getDefinedInstance();
		if (account != null) {
			getInstance().setAccount(account);
		}
		CyberContadino cyberContadino = cyberContadinoHome.getDefinedInstance();
		if (cyberContadino != null) {
			getInstance().setCyberContadino(cyberContadino);
		}
	}

	public boolean isWired() {
		return true;
	}

	public Questionario getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
