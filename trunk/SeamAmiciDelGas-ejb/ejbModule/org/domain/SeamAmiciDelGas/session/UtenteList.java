package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("utenteList")
public class UtenteList extends EntityQuery<Utente> {

	private static final String EJBQL = "select utente from Utente utente";

	private static final String[] RESTRICTIONS = {
			"lower(utente.codiceFiscale) like concat(lower(#{utenteList.utente.codiceFiscale}),'%')",
			"lower(utente.nome) like concat(lower(#{utenteList.utente.nome}),'%')",
			"lower(utente.cognome) like concat(lower(#{utenteList.utente.cognome}),'%')",
			"lower(utente.pathQuestionario) like concat(lower(#{utenteList.utente.pathQuestionario}),'%')",
			"lower(utente.indirizzo) like concat(lower(#{utenteList.utente.indirizzo}),'%')",
			"lower(utente.recapitoTelefonico) like concat(lower(#{utenteList.utente.recapitoTelefonico}),'%')",
			"lower(utente.email) like concat(lower(#{utenteList.utente.email}),'%')",
			"lower(utente.username) like concat(lower(#{utenteList.utente.username}),'%')",
			"lower(utente.luogoNascita) like concat(lower(#{utenteList.utente.luogoNascita}),'%')", };

	private Utente utente = new Utente();

	public UtenteList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Utente getUtente() {
		return utente;
	}
}
