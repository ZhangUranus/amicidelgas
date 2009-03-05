package org.domain.SeamAmiciDelGas.processes;

import static org.jboss.seam.annotations.Install.BUILT_IN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.Unwrap;
import org.jboss.seam.bpm.Actor;
import org.jboss.seam.bpm.ManagedJbpmContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Support for the pooled task list.
 * 
 * @see TaskInstanceList
 * @author Gavin King
 */
@Name("pooledTaskInstanceListForType")
@Scope(ScopeType.APPLICATION)
@Install(precedence=BUILT_IN, dependencies="org.jboss.seam.bpm.jbpm")
public class PooledTaskInstanceListForType
{
   
   @Unwrap
   @Transactional
   public Map<String, List<TaskInstance>> getPooledTaskInstanceListForType()
   {
      Actor actor = Actor.instance();
      String actorId = actor.getId();
      if ( actorId == null ) return null;
      ArrayList groupIds = new ArrayList( actor.getGroupActorIds());
      groupIds.add(actorId);
      
      Map<String, List<TaskInstance>> map = new HashMap<String, List<TaskInstance>>();
      List<TaskInstance> taskInstances = ManagedJbpmContext.instance().getGroupTaskList(groupIds);
      for ( TaskInstance task: taskInstances )
      {
         String name = task.getName();
         List<TaskInstance> list = map.get(name);
         if (list==null)
         {
            list = new ArrayList<TaskInstance>();
            map.put(name, list);
         }
         list.add(task);
      }
      return map;
   }
   
}