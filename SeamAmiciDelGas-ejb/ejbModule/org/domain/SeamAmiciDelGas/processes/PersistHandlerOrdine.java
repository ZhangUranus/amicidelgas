package org.domain.SeamAmiciDelGas.processes;


import org.domain.SeamAmiciDelGas.crud.OrdineHome;
import org.domain.SeamAmiciDelGas.entity.Ordine;

import org.jboss.seam.Component;
import org.jboss.seam.contexts.Context;
import org.jboss.seam.contexts.Contexts;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class PersistHandlerOrdine implements ActionHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6366543473564581185L;

	public void execute(ExecutionContext executionContext) throws Exception 
	{
		Context businessContext = Contexts.getBusinessProcessContext();
		OrdineHome ordineHome = (OrdineHome) Component.getInstance("ordineHome",true);
		
		Ordine ordine = (Ordine) businessContext.get("ordine");
		ordineHome.setId(ordine.getIdordine());
		Ordine newOrdine = ordineHome.find();
		newOrdine.setConcluso(true);
		ordineHome.update();
	}

}
