package org.domain.GAS.test;

import org.testng.annotations.Test;
import org.jboss.seam.mock.SeamTest;

public class RichiestaRegistrazioneTest extends SeamTest {

	@Test
	public void test_richiestaRegistrazione() throws Exception {
		new FacesRequest() {
			@Override
			protected void invokeApplication() {
				//call action methods here
				invokeMethod("#{richiestaRegistrazione.richiestaRegistrazione}");
			}
		}.run();
	}
}
