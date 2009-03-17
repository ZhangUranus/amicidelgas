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

public class ChangeDueDateTimerOrdine implements ActionHandler {

  
  private static final long serialVersionUID = 17056455L;
  private static final Log log = LogFactory.getLog(ChangeDueDateTimerOrdine.class);

  String timerName;
  String nomeData;

  public void execute(ExecutionContext executionContext) throws Exception {
	
	  try {
          Timer timer = executionContext.getTimer();
          
          if (timer != null && timerName.equals(timer.getName())) {
               Date dataMassima = (Date) executionContext.getVariable(nomeData);
               System.out.println("dataMassimaaaaaaaaaaaaaaaaaaaa: "+dataMassima);
               timer.setDueDate(dataMassima);
          } else {
               log.debug("Doesn't match: " + timer);
          }
     } catch (Exception ex) {
          ex.printStackTrace();
     }
  }
 public String getTimerName() {
      return timerName;
 }

 public void setTimerName(String timerName) {
      this.timerName = timerName;
 }
public String getNomeData() {
	return nomeData;
}
public void setNomeData(String nomeData) {
	this.nomeData = nomeData;
}

}
