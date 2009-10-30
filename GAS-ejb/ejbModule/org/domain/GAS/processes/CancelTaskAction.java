package org.domain.GAS.processes;
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
		TaskInstance  task = executionContext.getTaskInstance();
		Collection<TaskInstance> col  = executionContext.getTaskMgmtInstance().getTaskInstances();
		Iterator<TaskInstance> it = col.iterator();
		while(it.hasNext())
		{
			task = (TaskInstance) it.next();
			if(task.getName().equals(taskName) && task.isOpen())
			{
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
