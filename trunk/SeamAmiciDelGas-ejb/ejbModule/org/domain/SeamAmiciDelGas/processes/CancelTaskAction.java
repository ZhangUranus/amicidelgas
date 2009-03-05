package org.domain.SeamAmiciDelGas.processes;




import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jbpm.graph.exe.ExecutionContext;
@Name("cancelAction")
@Scope(ScopeType.BUSINESS_PROCESS)
public class CancelTaskAction {
	
	 /**
	 */
	private static final long serialVersionUID = 6157822508821288152L;
	/**
	 * 
	 */

	public void execute() throws Exception {
		    
		  ExecutionContext executionContext= ExecutionContext.currentExecutionContext();
		  System.out.println("Excetute TTTTTTTTTTTTTTTTTTTTTTTTTTT");
		  executionContext.getTaskInstance().cancel();
	  }

}
