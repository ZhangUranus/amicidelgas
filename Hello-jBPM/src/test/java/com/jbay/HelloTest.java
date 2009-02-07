package com.jbay;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
//import org.jbpm.jpdl.xml.JpdlXmlReader;

import junit.framework.TestCase;

public class HelloTest extends TestCase {
	
	public void testProcess() throws Exception {
		
		ProcessDefinition definition = 
			ProcessDefinition.parseXmlResource("C:/Users/Valerio/JEE-Workspace/Hello-jBPM/src/process/hello/processdefinition.xml");
		assertNotNull("Definition should not be null", definition);
		
		ProcessInstance instance = new ProcessInstance(definition);
		assertEquals("Instance is in start state", 
				instance.getRootToken().getNode().getName(), "start");

		instance.signal();
		assertEquals("Instance is in auction state", 
				instance.getRootToken().getNode().getName(), "auction");
		
		instance.signal();
		assertEquals("Instance is in end state", 
				instance.getRootToken().getNode().getName(), "end");
		
		assertTrue("Instance has ended", instance.hasEnded());
	}

}
