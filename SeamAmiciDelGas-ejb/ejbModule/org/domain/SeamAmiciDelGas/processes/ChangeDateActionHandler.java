package org.domain.SeamAmiciDelGas.processes;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.domain.SeamAmiciDelGas.session.Message;
import org.hibernate.annotations.Persister;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jbpm.context.exe.VariableContainer;
import org.jbpm.context.exe.VariableInstance;
import org.jbpm.graph.def.Action;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;
@Name("changeDateActionHandler")
@Scope(ScopeType.BUSINESS_PROCESS)
public class ChangeDateActionHandler implements Serializable{

	@In(value="dataMassimaAccettazione", scope=ScopeType.BUSINESS_PROCESS) private Date dataMassimaAccettazione;
	
	public void execute() {
	
		ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
		TaskInstance t=executionContext.getTaskInstance();
		t.setDueDate(dataMassimaAccettazione);
		//t.setVariableLocally("dataMassimaAccettazione", dataMassimaAccettazione);
	}


	
	
}
