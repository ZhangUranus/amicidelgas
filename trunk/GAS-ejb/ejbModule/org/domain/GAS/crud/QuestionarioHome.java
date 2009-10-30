package org.domain.GAS.crud;

import org.domain.GAS.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("questionarioHome")
public class QuestionarioHome extends EntityHome<Questionario> {

	@In(create = true)
	AccountHome accountHome;
	@In(create = true)
	CybercontadinoHome cybercontadinoHome;

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
		Cybercontadino cybercontadino = cybercontadinoHome.getDefinedInstance();
		if (cybercontadino != null) {
			getInstance().setCybercontadino(cybercontadino);
		}
	}

	public boolean isWired() {
		if (getInstance().getAccount() == null)
			return false;
		if (getInstance().getCybercontadino() == null)
			return false;
		return true;
	}

	public Questionario getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
