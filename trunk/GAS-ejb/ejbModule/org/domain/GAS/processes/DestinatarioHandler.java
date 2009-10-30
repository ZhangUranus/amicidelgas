package org.domain.GAS.processes;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.Assignable;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.domain.GAS.session.*;


public class DestinatarioHandler implements AssignmentHandler
{
	private static final long serialVersionUID = 8901841172235529657L;
	String nameTask;
	public void assign(Assignable assign, ExecutionContext executionContext) throws Exception 
	{
		Message messaggio = (Message) executionContext.getVariable("notifyMessageRequest");
		String destinatario = messaggio.getDestinatario();
    	assign.setActorId(destinatario);
    	TaskInstance ti = executionContext.getTaskInstance();
    	if(messaggio.isBroadcast())
    		ti.setPriority(Task.PRIORITY_HIGH);
    	else
    		ti.setPriority(Task.PRIORITY_HIGHEST);
	}

	public String getNameTask() {
		return nameTask;
	}

	public void setNameTask(String nameTask) {
		this.nameTask = nameTask;
	}
}
