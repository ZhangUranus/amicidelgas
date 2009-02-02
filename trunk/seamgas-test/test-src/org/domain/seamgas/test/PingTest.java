package org.domain.seamgas.test;

import org.testng.annotations.Test;
import org.jboss.seam.mock.SeamTest;

public class PingTest extends SeamTest {

	@Test
	public void test_ping() throws Exception {
		new FacesRequest() {
			@Override
			protected void invokeApplication() {
				//call action methods here
				invokeMethod("#{ping.ping}");
			}
		}.run();
	}
}
