package org.domain.GAS.processes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.domain.GAS.session.Message;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jbpm.context.exe.VariableContainer;
import org.jbpm.context.exe.VariableInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;
@Name("multipleNotifyActionHandler")
@Scope(ScopeType.BUSINESS_PROCESS)
public class MultipleNotifyActionHandler implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5778508191868142049L;
	@In(value="notifyMessage", scope=ScopeType.BUSINESS_PROCESS, required=false) 
	private Message message;
	
	public void execute() {
		ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
		Token token = executionContext.getToken();
	    TaskMgmtInstance tmi = executionContext.getTaskMgmtInstance();
	    ProcessInstance pi= executionContext.getProcessInstance();
	   
	    TaskNode taskNode = (TaskNode) executionContext.getNode();
	    taskNode.setSignal(TaskNode.SIGNAL_LAST_WAIT);
	    Task receiveMessage = taskNode.getTask("ReceiveMessage");
	    List<String> recipients = message.getRecipients();
	    Iterator<String> it=recipients.iterator();
	    while(it.hasNext()){
	    	TaskInstance ti = tmi.createTaskInstance(receiveMessage,executionContext);
	    	Map tokenVariables=pi.getContextInstance().getTokenVariableMap(token).getVariables();
	    	//ti.setVariableLocally("notifyMessage", tokenVariables.get("notifyMessage"));
	    	ti.setVariableLocally("notifyMessage", tokenVariables.get("notifyMessage"));
	    	if(message.isBroadcast())
	    		ti.setPooledActors(new String[]{it.next()});
	    	else
	    		ti.setActorId(it.next());
	    }
	}


	
	
}
