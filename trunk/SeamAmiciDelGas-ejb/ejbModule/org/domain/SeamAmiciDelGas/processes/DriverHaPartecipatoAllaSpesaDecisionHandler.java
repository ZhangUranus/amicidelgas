package org.domain.SeamAmiciDelGas.processes;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.Decision;
import org.jbpm.graph.node.DecisionHandler;

public class DriverHaPartecipatoAllaSpesaDecisionHandler implements DecisionHandler {

	public String responsabileIsDriver;
	public String decide(ExecutionContext executionContext) throws Exception 
	{
		Object isResp = executionContext.getVariable(responsabileIsDriver);
		
		Boolean value = (Boolean) isResp;
		String transition = value.booleanValue() ? "Si" : "no";
		return transition;

	}
	public String getResponsabileIsDriver() {
		return responsabileIsDriver;
	}
	public void setResponsabileIsDriver(String responsabileIsDriver) {
		this.responsabileIsDriver = responsabileIsDriver;
	}
}
