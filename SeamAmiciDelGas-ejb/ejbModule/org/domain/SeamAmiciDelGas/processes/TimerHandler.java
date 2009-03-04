package org.domain.SeamAmiciDelGas.processes;

import java.io.Serializable;

import java.util.Date;


import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import org.jbpm.graph.def.Action;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.job.Timer;
import org.jbpm.scheduler.SchedulerService;

import org.jbpm.svc.Services;
import org.jbpm.taskmgmt.exe.TaskInstance;

@Name("timerHandler")
@Scope(ScopeType.BUSINESS_PROCESS)
public class TimerHandler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	  
	 @In(value="dataProposta", scope=ScopeType.BUSINESS_PROCESS) 
	 private Date dataProposta;

	  public void execute() {
	    
		  ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
		  System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		  long data = dataProposta.getTime() - System.currentTimeMillis();
		  Date dueDate = new Date();
		  if(data < 0)
			  dueDate.setTime(0);
		  else 
			  dueDate.setTime(data);
		 
		  System.out.println("TEMPOOOOOOOOOOOOOOOO: "+dueDate.toString());
		
		 TaskInstance t = executionContext.getTaskInstance();
		 Timer timer = new Timer(t.getToken());
		 timer.setName("TimerPartecipa");
		 timer.setTransitionName("time-out");
		 timer.setDueDate(dueDate);
		 timer.setTaskInstance(t);
		 timer.setGraphElement(t.getTask().getTaskNode());
		 timer.setAction(new Action() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3893442458445592043L;

			public void execute(ExecutionContext arg0) throws Exception {
				// TODO Auto-generated method stub
				arg0.getTaskInstance().cancel();
				
			}});
		 SchedulerService s = (SchedulerService) executionContext.getJbpmContext().getServices().getService(Services.SERVICENAME_SCHEDULER);
		 s.createTimer(timer);
		// t.cancel();
		//  executionContext.getTask().
		 System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
	 

	  }
	  
}
