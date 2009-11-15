package org.domain.GAS.crud;

import org.domain.GAS.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("pagamentoelettronicoList")
public class PagamentoelettronicoList extends EntityQuery<Pagamentoelettronico> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8104809157948442127L;

	private static final String EJBQL = "select pagamentoelettronico from Pagamentoelettronico pagamentoelettronico";

	private static final String[] RESTRICTIONS = { "lower(pagamentoelettronico.tipoCarta) like concat(lower(#{pagamentoelettronicoList.pagamentoelettronico.tipoCarta}),'%')", };

	private Pagamentoelettronico pagamentoelettronico = new Pagamentoelettronico();

	public PagamentoelettronicoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Pagamentoelettronico getPagamentoelettronico() {
		return pagamentoelettronico;
	}
}
