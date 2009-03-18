package org.domain.SeamAmiciDelGas.processes;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.domain.SeamAmiciDelGas.session.Message;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jbpm.context.exe.VariableContainer;
import org.jbpm.context.exe.VariableInstance;
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
	 //   taskNode.setSignal(TaskNode.SIGNAL_LAST_WAIT);
	   // Task riceviMessaggio = taskNode.getTask("riceviMessaggio");
	    Task task = taskNode.getTask(nameTask);
	    System.out.println(nameTask+" IMPOSSIBILEEEEEEEEEEEEEEEEEEEEE");
	    Message message = (Message) executionContext.getVariable("notifyMessageRequest");
	    String destinatario = (String) executionContext.getVariable("nomeDestinatario");
	    if(message == null)
	    {
	    	System.out.println("Messaggio NULLOOOOOOOO");
	    	return;
	    }
	    TaskInstance ti = tmi.createTaskInstance(task,executionContext);
    	Map tokenVariables=pi.getContextInstance().getTokenVariableMap(token).getVariables();
    	//ti.setVariableLocally("notifyMessageRequest", tokenVariables.get("notifyMessageReques"));
	    if(message.isBroadcast())
	    {
	    	//Processo Driver
	    	System.out.println("Messaggio BROADCAST");
	    	String [] poolActor = new String[1];
	   		poolActor[0] = message.getDestinatario();
	    	ti.setPooledActors(poolActor);
	    	ti.setPriority(Task.PRIORITY_HIGH);
	    }
	    else
	    {
	    	// Processo Questionario
	    	System.out.println("Messaggio NON BROADCAST +"+destinatario);
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
