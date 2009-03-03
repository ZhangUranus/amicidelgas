package org.domain.SeamAmiciDelGas.processes;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.bpm.BeginTask;
import org.jboss.seam.annotations.bpm.EndTask;

@Name("richiestaDriverAck")
public class RichiestaDriverAck {

	@BeginTask @EndTask(transition="acknowledge")
	public void acknowledge(){
		
	}
	
}
