package org.domain.GAS.test;

import org.testng.annotations.Test;
import org.jboss.seam.mock.SeamTest;

public class NewPuntiDiConsegnaTest extends SeamTest {

	@Test
	public void test_newPuntiDiConsegna() throws Exception {
		new FacesRequest("/mediatore/puntiDiConsegna.xhtml") {
			
			@Override
			protected void processValidations() throws Exception
			{
			   validateValue("#{newComunePuntoConsegna.comune.nome}", "Benevento");
			   validateValue("#{newPuntiDiConsegna.indirizzo}", "piazza roma");
			   assert !isValidationFailure();
			}
			
			@Override
			protected void updateModelValues() throws Exception
			{
			   setValue("#{newComunePuntoConsegna.comune.nome}", "Benevento");
			   setValue("#{newPuntiDiConsegna.indirizzo}", "piazza roma");
			}


			@Override
			protected void invokeApplication() {
				//call action methods here
				assert invokeMethod("#{addPuntiDiConsegna.persistExtend()}").equals(true);
			}
			
			@Override
			protected void renderResponse()
			{
			   assert getValue("#{newComunePuntoConsegna.comune.nome}").equals("Benevento");
			   assert getValue("#{newPuntiDiConsegna.indirizzo}").equals("piazza roma");
			}


			
		}.run();
	}
}
