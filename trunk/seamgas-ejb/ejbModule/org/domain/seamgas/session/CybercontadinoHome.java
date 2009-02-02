package org.domain.seamgas.session;

import org.domain.seamgas.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("cybercontadinoHome")
public class CybercontadinoHome extends EntityHome<Cybercontadino> {

	@In(create = true)
	AccountHome accountHome;
	@In(create = true)
	ComuneHome comuneHome;
	@In(create = true)
	ArticoloHome articoloHome;

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
		Articolo articolo = articoloHome.getDefinedInstance();
		if (articolo != null) {
			getInstance().setArticolo(articolo);
		}
	}

	public boolean isWired() {
		if (getInstance().getAccount() == null)
			return false;
		if (getInstance().getComune() == null)
			return false;
		if (getInstance().getArticolo() == null)
			return false;
		return true;
	}

	public Cybercontadino getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
