package org.domain.GAS.crud;

import org.domain.GAS.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("provincesList")
public class ProvincesList extends EntityQuery<Provinces> {

	private static final String EJBQL = "select provinces from Provinces provinces";

	private static final String[] RESTRICTIONS = {
			"lower(provinces.idprovinces) like concat(lower(#{provincesList.provinces.idprovinces}),'%')",
			"lower(provinces.nome) like concat(lower(#{provincesList.provinces.nome}),'%')", };

	private Provinces provinces = new Provinces();

	public ProvincesList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(110);
	}

	public Provinces getProvinces() {
		return provinces;
	}
}
