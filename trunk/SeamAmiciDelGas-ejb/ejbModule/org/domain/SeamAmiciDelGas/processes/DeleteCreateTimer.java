package org.domain.SeamAmiciDelGas.processes;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.job.Timer;
import org.jbpm.scheduler.SchedulerService;
import org.jbpm.svc.Services;

public class DeleteCreateTimer implements ActionHandler
{
	private static final long serialVersionUID = 6748316904438698058L;
	private static final Log log = LogFactory.getLog(DeleteCreateTimer.class);
	String timerName;
	public void execute(ExecutionContext executionContext) throws Exception 
	{
	  try {
          Timer timer = executionContext.getTimer();
          if (timer != null && timerName.equals(timer.getName())) 
          { 
        	  Date dataTimerDefinitiva  = (Date) executionContext.getVariable("dataTimer");
        	  if(dataTimerDefinitiva  == null)
        	  {
        		  SchedulerService schedulerService = (SchedulerService) Services.getCurrentService(Services.SERVICENAME_SCHEDULER);
	              schedulerService.deleteTimersByName(timer.getName(), executionContext.getToken());
        	  }
        	  else
        		  timer.setDueDate(dataTimerDefinitiva);
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
