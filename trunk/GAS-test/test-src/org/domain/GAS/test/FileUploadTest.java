package org.domain.GAS.test;

import org.testng.annotations.Test;
import org.jboss.seam.mock.SeamTest;

public class FileUploadTest extends SeamTest {

	@Test
	public void test_fileUpload() throws Exception {
		new FacesRequest() {
			@Override
			protected void invokeApplication() {
				//call action methods here
				invokeMethod("#{fileUpload.fileUpload}");
			}
		}.run();
	}
}
