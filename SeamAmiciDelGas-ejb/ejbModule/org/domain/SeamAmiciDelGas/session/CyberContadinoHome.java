package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("cyberContadinoHome")
public class CyberContadinoHome extends EntityHome<CyberContadino> {

	public void setCyberContadinoPartitaIva(Integer id) {
		setId(id);
	}

	public Integer getCyberContadinoPartitaIva() {
		return (Integer) getId();
	}

	@Override
	protected CyberContadino createInstance() {
		CyberContadino cyberContadino = new CyberContadino();
		return cyberContadino;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public CyberContadino getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Questionario> getQuestionarios() {
		return getInstance() == null ? null : new ArrayList<Questionario>(
				getInstance().getQuestionarios());
	}

}
