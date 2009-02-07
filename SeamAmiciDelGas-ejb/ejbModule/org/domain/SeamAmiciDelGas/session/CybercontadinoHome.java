package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("cybercontadinoHome")
public class CybercontadinoHome extends EntityHome<Cybercontadino> {

	@In(create = true)
	AccountHome accountHome;
	@In(create = true)
	ComuneHome comuneHome;

	public void setCybercontadinoPartitaIva(Integer id) {
		setId(id);
	}

	public Integer getCybercontadinoPartitaIva() {
		return (Integer) getId();
	}

	@Override
	protected Cybercontadino createInstance() {
		Cybercontadino cybercontadino = new Cybercontadino();
		return cybercontadino;
	}

	public void wire() {
		getInstance();
		Account account = accountHome.getDefinedInstance();
		if (account != null) {
			getInstance().setAccount(account);
		}
		Comune comune = comuneHome.getDefinedInstance();
		if (comune != null) {
			getInstance().setComune(comune);
		}
	}

	public boolean isWired() {
		if (getInstance().getAccount() == null)
			return false;
		if (getInstance().getComune() == null)
			return false;
		return true;
	}

	public Cybercontadino getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Questionario> getQuestionarios() {
		return getInstance() == null ? null : new ArrayList<Questionario>(
				getInstance().getQuestionarios());
	}

	public List<Questionario> getQuestionarios_1() {
		return getInstance() == null ? null : new ArrayList<Questionario>(
				getInstance().getQuestionarios_1());
	}

	public List<Articolo> getArticolos() {
		return getInstance() == null ? null : new ArrayList<Articolo>(
				getInstance().getArticolos());
	}

	public List<Articolo> getArticolos_1() {
		return getInstance() == null ? null : new ArrayList<Articolo>(
				getInstance().getArticolos_1());
	}

	public List<Questionario> getQuestionarios_2() {
		return getInstance() == null ? null : new ArrayList<Questionario>(
				getInstance().getQuestionarios_2());
	}

}
