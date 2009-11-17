package org.domain.GAS.test;

import org.testng.annotations.Test;
import org.jboss.seam.mock.SeamTest;
import org.jboss.seam.mock.AbstractSeamTest.FacesRequest;

public class RegistrazioneCyberContadinoTest extends SeamTest {

	@Test
	public void test_registrazioneCyberContadino() throws Exception {
		new FacesRequest("/contadino/registrazioneCybercontadinoPasso3.xhtml") {
			
			@Override
			protected void processValidations() throws Exception
			{
			   validateValue("#{newCybercontadino.partitaIva}", "00000000000");
			   validateValue("#{newCybercontadino.nomeAzienda}", "Azienda");
			   validateValue("#{newCybercontadino.nomePresidente}", "nomePresidente");
			   validateValue("#{newCybercontadino.cognomePresidente}", "cognomePresidente");
			   validateValue("#{newComuneProvinciaBean.provincia.idprovinces}", "BN");
			   validateValue("#{newComuneProvinciaBean.comune.nome}", "Benevento");
			   validateValue("#{newCybercontadino.recapitoTelefonico}", "0824123456");
			   validateValue("#{newCybercontadino.email}", "laser@gmail.com");
			   validateValue("#{newAccount.username}", "contadino3");
			   validateValue("#{passwordBean.password}", "contadino");
			   validateValue("#{newPagamento.tipoCarta}", "Visa");
			   validateValue("#{newCybercontadino.coordinate}", "coordinate");
			   validateValue("#{newCybercontadino.indirizzo}", "piazza roma");			   
			   assert !isValidationFailure();
			}
			
			@Override
			protected void updateModelValues() throws Exception
			{
				setValue("#{newCybercontadino.partitaIva}", "00000000000");
				setValue("#{newCybercontadino.nomeAzienda}", "Azienda");
				setValue("#{newCybercontadino.nomePresidente}", "nomePresidente");
				setValue("#{newCybercontadino.cognomePresidente}", "cognomePresidente");
				setValue("#{newComuneProvinciaBean.provincia.idprovinces}", "BN");
				setValue("#{newComuneProvinciaBean.comune.nome}", "Benevento");
				setValue("#{newCybercontadino.recapitoTelefonico}", "0824123456");
				setValue("#{newCybercontadino.email}", "laser@gmail.com");
				setValue("#{newAccount.username}", "contadino3");
				setValue("#{passwordBean.password}", "contadino");
				setValue("#{newPagamento.tipoCarta}", "Visa");
				setValue("#{newCybercontadino.coordinate}", "coordinate");
				setValue("#{newCybercontadino.indirizzo}", "piazza roma");
			}
		
			@Override
			protected void invokeApplication() {
				//call action methods here
				invokeMethod("#{registrazioneCybercontadino.registrazioneCybercontadino}");
			}
			
			@Override
			protected void renderResponse()
			{
				assert getValue("#{newCybercontadino.partitaIva}").equals("00000000000");
				assert getValue("#{newCybercontadino.nomeAzienda}").equals( "Azienda");
				assert getValue("#{newCybercontadino.nomePresidente}").equals("nomePresidente");
				assert getValue("#{newCybercontadino.cognomePresidente}").equals("cognomePresidente");
				assert getValue("#{newComuneProvinciaBean.provincia.idprovinces}").equals("BN");
				assert getValue("#{newComuneProvinciaBean.comune.nome}").equals("Benevento");
				assert getValue("#{newCybercontadino.recapitoTelefonico}").equals("0824123456");
				assert getValue("#{newCybercontadino.email}").equals("laser@gmail.com");
				assert getValue("#{newAccount.username}").equals("contadino3");
				assert getValue("#{passwordBean.password}").equals("contadino");
				assert getValue("#{newPagamento.tipoCarta}").equals("Visa");
				assert getValue("#{newCybercontadino.coordinate}").equals("coordinate");
				assert getValue("#{newCybercontadino.indirizzo}").equals("piazza roma");
			}
			
		}.run();
	}
}
