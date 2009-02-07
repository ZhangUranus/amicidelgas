package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("pagamentoelettronicoHome")
public class PagamentoelettronicoHome extends EntityHome<Pagamentoelettronico> {

	public void setPagamentoelettronicoIdPagamentoElettronico(Integer id) {
		setId(id);
	}

	public Integer getPagamentoelettronicoIdPagamentoElettronico() {
		return (Integer) getId();
	}

	@Override
	protected Pagamentoelettronico createInstance() {
		Pagamentoelettronico pagamentoelettronico = new Pagamentoelettronico();
		return pagamentoelettronico;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Pagamentoelettronico getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Account> getAccounts() {
		return getInstance() == null ? null : new ArrayList<Account>(
				getInstance().getAccounts());
	}

	public List<Account> getAccounts_1() {
		return getInstance() == null ? null : new ArrayList<Account>(
				getInstance().getAccounts_1());
	}

}
