package org.domain.SeamAmiciDelGas.processes;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.contexts.Context;
import org.jboss.seam.contexts.Contexts;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.job.Timer;

public class ChangeTimerDate implements ActionHandler{
	
	private static final long serialVersionUID = -2215287520579897722L;

	String nomeTimer;
	
	String nomeDueDate;
	  
	private static final Log log = LogFactory.getLog(ChangeTimerDate.class);
	
	public void execute(ExecutionContext executionContext ) {
	    
		  Context businessContext = Contexts.getBusinessProcessContext();
		  System.out.println("AChengeTimerDate");
		
		  try {

	          Timer timer = executionContext.getTimer();
	          
	          if (timer != null && nomeTimer.equals(timer.getName())) {
	               //Date data = (Date) executionContext.getVariable(nomeDueDate);
	        	  Date data= (Date) businessContext.get(nomeDueDate);
	    		   
	        	  System.out.println("DataIMPOSTATA: "+data);
	               if(data != null)
	               {
	            	   timer.setDueDate(data);
	               }
	               System.out.println("ORROREEEEEEEEEEEE DATA");
	               System.out.println(nomeTimer);
	               System.out.println("DataCAMBIATA: "+data);
	           
	          } else {
	               log.debug("Doesn't match: " + timer);
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