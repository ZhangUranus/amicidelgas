package org.domain.SeamAmiciDelGas.processes;
import java.util.Collection;
import java.util.Iterator;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

public class CancelTaskAction implements ActionHandler{
	
	private static final long serialVersionUID = 6157822508821288152L;

	String taskName;
	public void execute(ExecutionContext executionContext) throws Exception 
	{
		
		    
		System.out.println("Excetute TTTTTTTTTTTTTTTTTTTTTTTTTTT");
		TaskInstance  task = executionContext.getTaskInstance();
		Collection<TaskInstance> col  = executionContext.getTaskMgmtInstance().getTaskInstances();
		Iterator<TaskInstance> it = col.iterator();
		while(it.hasNext())
		{
			task = (TaskInstance) it.next();
			System.out.println("IL TASKKKKKKKKKK "+task.getName());
			//if(task.getName().equals("partecipa_alla_visita") && task.isOpen())
			if(task.getName().equals(taskName) && task.isOpen())
			{
				System.out.println("Task ID: "+task.getId());
				task.setSignalling(false);
				task.cancel();
			}
		}
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}
