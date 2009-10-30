package org.domain.GAS.crud;

import org.domain.GAS.entity.*;

import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("provincesHome")
public class ProvincesHome extends EntityHome<Provinces> {

	public void setProvincesIdprovinces(String id) {
		setId(id);
	}

	public String getProvincesIdprovinces() {
		return (String) getId();
	}

	@Override
	protected Provinces createInstance() {
		Provinces provinces = new Provinces();
		return provinces;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Provinces getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Comune> getComunes() {
		return getInstance() == null ? null : new ArrayList<Comune>(
				getInstance().getComunes());
	}

}
