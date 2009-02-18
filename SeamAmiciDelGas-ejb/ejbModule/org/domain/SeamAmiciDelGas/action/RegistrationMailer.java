package org.domain.SeamAmiciDelGas.action;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.Renderer;

@Name("registrationMailer")
public class RegistrationMailer {
	
	@In private Renderer renderer;
	public void sendWelcomeEmail() {
		renderer.render("/email/welcome.xhtml");
	}
}
