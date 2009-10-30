package org.domain.GAS.processes;

import java.util.Map;

import org.domain.GAS.session.Message;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

public class CambiaDestinatarioHandler implements ActionHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2785379940181442314L;
	
	String nameTask;

	public void execute(ExecutionContext executionContext) throws Exception 
	{
		Token token = executionContext.getToken();
	    TaskMgmtInstance tmi = executionContext.getTaskMgmtInstance();
	    ProcessInstance pi= executionContext.getProcessInstance();
	    TaskNode taskNode = (TaskNode) executionContext.getNode();
	    Task task = taskNode.getTask(nameTask);
	    Message message = (Message) executionContext.getVariable("notifyMessageRequest");
	    String destinatario = (String) executionContext.getVariable("nomeDestinatario");
	    if(message == null)
	    {
	    	return;
	    }
	    TaskInstance ti = tmi.createTaskInstance(task,executionContext);
    	Map tokenVariables=pi.getContextInstance().getTokenVariableMap(token).getVariables();
	    if(message.isBroadcast())
	    {
	    	//Processo Driver
	    	String [] poolActor = new String[1];
	   		poolActor[0] = message.getDestinatario();
	    	ti.setPooledActors(poolActor);
	    	ti.setPriority(Task.PRIORITY_HIGH);
	    }
	    else
	    {
	    	// Processo Questionario
	    	ti.setActorId(destinatario);
	    	ti.setPriority(Task.PRIORITY_HIGHEST);
	    }
	}

	public String getNameTask() {
		return nameTask;
	}

	public void setNameTask(String nameTask) {
		this.nameTask = nameTask;
	}
}
