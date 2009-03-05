package org.domain.SeamAmiciDelGas.processes;

import java.io.Serializable;

import java.util.Date;
import java.util.GregorianCalendar;


import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import org.jbpm.graph.def.Action;
import org.jbpm.graph.def.Node;

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
		 System.out.println("Prelevata l'istanza "+ t.getId()+"del task"+ t.getTask().getName());
		 Timer timer = new Timer(t.getToken());
		 timer.setName("TimerPartecipa");
		 timer.setTransitionName("time-out");
		 GregorianCalendar gc= new GregorianCalendar();
		 gc.roll(GregorianCalendar.MINUTE, 1);
			 timer.setDueDate(gc.getTime());
			
		 timer.setTaskInstance(t);
		 Node currentNode=t.getTask().getTaskNode();
		 timer.setGraphElement(currentNode);
		 currentNode.fireEvent("timer-create", executionContext);
		 CancelTaskAction act = new CancelTaskAction();
		 Action azione = new Action();
		 azione.setName("Cancella Task");
		 azione.setActionExpression("#{cancelAction.execute}");
		 timer.setAction(azione);
		 
		 executionContext.setTimer(timer);
		 
		 SchedulerService s = (SchedulerService) executionContext.getJbpmContext().getServices().getService(Services.SERVICENAME_SCHEDULER);
		 s.createTimer(timer);
		// t.cancel();
		//  executionContext.getTask().
		 System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
	 
		 
	  }
	  
}
