package org.domain.SeamAmiciDelGas.test;

import org.testng.annotations.Test;
import org.jboss.seam.mock.SeamTest;

public class NewPuntiDiConsegnaTest extends SeamTest {

	@Test
	public void test_newPuntiDiConsegna() throws Exception {
		new FacesRequest() {
			@Override
			protected void invokeApplication() {
				//call action methods here
				invokeMethod("#{newPuntiDiConsegna.newPuntiDiConsegna}");
			}
		}.run();
	}
}
