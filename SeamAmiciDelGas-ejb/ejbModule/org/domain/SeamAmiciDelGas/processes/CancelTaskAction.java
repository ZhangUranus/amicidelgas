package org.domain.SeamAmiciDelGas.processes;




import org.jbpm.graph.exe.ExecutionContext;

public class CancelTaskAction {
	
	 /**
	 */
	private static final long serialVersionUID = 6157822508821288152L;
	/**
	 * 
	 */

	public void execute(ExecutionContext executionContext) throws Exception {
		    

		  System.out.println("Excetute TTTTTTTTTTTTTTTTTTTTTTTTTTT");
		  executionContext.getTaskInstance().cancel();
	  }

}
