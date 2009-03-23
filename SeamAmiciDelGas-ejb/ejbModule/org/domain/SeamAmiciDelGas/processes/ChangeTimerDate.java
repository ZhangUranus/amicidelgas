package org.domain.SeamAmiciDelGas.processes;

import java.util.Date;
import org.jboss.seam.contexts.Context;
import org.jboss.seam.contexts.Contexts;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.job.Timer;

public class ChangeTimerDate implements ActionHandler{
	
	private static final long serialVersionUID = -2215287520579897722L;

	String nomeTimer;
	String nomeDueDate;

	public void execute(ExecutionContext executionContext ) {
	    
		  Context businessContext = Contexts.getBusinessProcessContext();	
		  try {
	          Timer timer = executionContext.getTimer();
	          if (timer != null && nomeTimer.equals(timer.getName())) 
	          {
	        	  Date data= (Date) businessContext.get(nomeDueDate);
	        	  if(data != null)
	        		  timer.setDueDate(data);
	          }
	     } catch (Exception ex) {
	          ex.printStackTrace();
	     }
	  }
	
	public String getNomeTimer() {
		return nomeTimer;
	}

	public void setNomeTimer(String nomeTimer) {
		this.nomeTimer = nomeTimer;
	}
	
	public String getNomeDueDate() {
		return nomeDueDate;
	}

	public void setNomeDueDate(String nomeDueDate) {
		this.nomeDueDate = nomeDueDate;
	}
}
