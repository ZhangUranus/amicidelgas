/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.domain.SeamAmiciDelGas.processes;



import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.job.Timer;

public class ChangeDueDateTimer implements ActionHandler {

  
  private static final long serialVersionUID = 1705L;
  private static final Log log = LogFactory.getLog(ChangeDueDateTimer.class);

  String timerName;

  public void execute(ExecutionContext executionContext) throws Exception {
	
	  try {
          Timer timer = executionContext.getTimer();
          
          if (timer != null && timerName.equals(timer.getName())) {
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
               Date dataMassima = (Date) executionContext.getVariable("dataMassimaAccettazione");
               System.out.println("dataMassima: "+dataMassima);
               timer.setDueDate(dataMassima);
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
