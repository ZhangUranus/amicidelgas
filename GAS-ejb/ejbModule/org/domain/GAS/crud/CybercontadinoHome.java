package org.domain.GAS.crud;

import org.domain.GAS.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;

@Name("cybercontadinoHome")
public class CybercontadinoHome extends EntityHome<Cybercontadino> {

	@In(create = true)
	AccountHome accountHome;
	@In(create = true)
	ComuneHome comuneHome;
	
	@Logger
	private Log log;

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
	
	@Override
	public String remove() {
		log.info("CONTADINO HOME id: "+getInstance().getPartitaIva());
		log.info("CONTADINO HOME id: "+getInstance().getCognomePresidente());
		Account a = getInstance().getAccount();
		
		a.setEliminato(true);
		update();
		return "contadino settato a cancellato";
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

	public List<Articolo> getArticolos() {
		return getInstance() == null ? null : new ArrayList<Articolo>(
				getInstance().getArticolos());
	}

}
