package org.domain.SeamAmiciDelGas.processes;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.job.Timer;
import org.jbpm.scheduler.SchedulerService;
import org.jbpm.svc.Services;

public class DeleteCreateTimer implements ActionHandler{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6748316904438698058L;

	private static final Log log = LogFactory.getLog(DeleteCreateTimer.class);

	  String timerName;

	  public void execute(ExecutionContext executionContext) throws Exception {
		
		  try {
	          Timer timer = executionContext.getTimer();
	          
	          if (timer != null && timerName.equals(timer.getName())) {
	        	  
	        	  Date dataCompilazioneQuestionario = (Date) executionContext.getVariable("dataCompilazioneQuestionario");
	        	  if(dataCompilazioneQuestionario == null)
	        	  {
	        		  SchedulerService schedulerService = (SchedulerService) Services.getCurrentService(Services.SERVICENAME_SCHEDULER);
		              schedulerService.deleteTimersByName(timer.getName(), executionContext.getToken());
	        	  }
	        	  else
	        	  {
	             /*  String dueDate = delay;
	               System.out.println("DELAY: "+delay);
	                           // get the value of a variable
	               if (delay.startsWith("#{")) {
	                    dueDate = (String) executionContext.getVariable(delay.substring(2, delay.length() -1));
	              }
	                           log.info("Changing the timer: " + timer + " to fire in " + dueDate);

	               SchedulerService schedulerService = (SchedulerService) Services.getCurrentService(Services.SERVICENAME_SCHEDULER);

	               delete the existing timer
	               schedulerService.deleteTimersByName(timer.getName(), executionContext.getToken());

	               create a new one with the right delay
	               Duration duration = new Duration(dueDate);
	               Date dueDateDate = businessCalendar.add(new Date(), duration);
	               timer.setDueDate(dueDateDate);
	               schedulerService.createTimer(timer);
	*/
	               System.out.println("dataCompilazioneQuestionario: "+dataCompilazioneQuestionario);
	               timer.setDueDate(dataCompilazioneQuestionario);
	        	  }
	          } else {
	               log.debug("Doesn't match: " + timer);
	          }
	     } catch (Exception ex) {
	          ex.printStackTrace();
	     }
	  }
	/*  
	  public String getDelay() {
	      return delay;
	 }

	 public void setDelay(String delay) {
	      this.delay = delay;
	 }
	*/
	 public String getTimerName() {
	      return timerName;
	 }

	 public void setTimerName(String timerName) {
	      this.timerName = timerName;
	 }


}
