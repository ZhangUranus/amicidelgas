package org.domain.SeamAmiciDelGas.crud;

import java.util.Arrays;

import org.domain.SeamAmiciDelGas.entity.PuntiDiConsegna;
import org.jboss.seam.annotations.Name;


@Name("puntiDiConsegnaListExtend")
public class PuntiDiConsegnaListExtend extends PuntiDiConsegnaList {
	
	private static final String EJBQL = "select puntiDiConsegna from PuntiDiConsegna puntiDiConsegna";

	private static final String[] RESTRICTIONS = {
			"lower(puntiDiConsegna.indirizzo) like concat(lower(#{puntiDiConsegnaList.puntiDiConsegna.indirizzo}),'%')",
			"lower(puntiDiConsegna.coordinate) like concat(lower(#{puntiDiConsegnaList.puntiDiConsegna.coordinate}),'%')", };

	private PuntiDiConsegna puntiDiConsegna = new PuntiDiConsegna();
	private int count;

	public PuntiDiConsegnaListExtend() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(100);
		setCount(this.getResultList().size());
	}

	@Override
	public PuntiDiConsegna getPuntiDiConsegna() {
		return puntiDiConsegna;
	}
	
	

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	
}
