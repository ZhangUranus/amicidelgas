package org.domain.GAS.test;

import org.testng.annotations.Test;
import org.jboss.seam.mock.SeamTest;

public class ControllaParitaIvaTest extends SeamTest {

	@Test
	public void test_controllaParitaIva() throws Exception {
		new FacesRequest() {
			@Override
			protected void invokeApplication() {
				//call action methods here
				invokeMethod("#{ControllaParitaIva.controllaParitaIva}");
			}
		}.run();
	}
}
