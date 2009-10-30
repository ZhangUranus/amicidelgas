package org.domain.GAS.processes;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.job.Timer;

public class TimerHandler implements ActionHandler {
	
	private static final long serialVersionUID = -3037017664789033945L;
	
	  String timerName;
	  
	  private static final Log log = LogFactory.getLog(TimerHandler.class);
	
	public void execute(ExecutionContext executionContext ) 
	{
		  try 
		  {
	          Timer timer = executionContext.getTimer();
	          if (timer != null && timerName.equals(timer.getName())) {
	               Date dataQuestionario = (Date) executionContext.getVariable("dataQuestionario");
	               timer.setDueDate(dataQuestionario);
	          }
	     } catch (Exception ex) {
	          ex.printStackTrace();
	     }
	  }

	public String getTimerName() {
		return timerName;
	}

	public void setTimerName(String timerName) {
		this.timerName = timerName;
	}  
}

