package org.domain.SeamAmiciDelGas.test;

import org.testng.annotations.Test;
import org.jboss.seam.mock.SeamTest;

public class RegistrazioneCyberContadinoTest extends SeamTest {

	@Test
	public void test_registrazioneCyberContadino() throws Exception {
		new FacesRequest() {
			@Override
			protected void invokeApplication() {
				//call action methods here
				invokeMethod("#{RegistrazioneCyberContadino.registrazioneCyberContadino}");
			}
		}.run();
	}
}
