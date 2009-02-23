package org.domain.SeamAmiciDelGas.session;

import javax.persistence.EntityManager;

import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Role;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.bpm.BeginTask;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.faces.FacesMessages;

@Name("richiestaDriverDecision")
@Scope(ScopeType.CONVERSATION)
public class RichiestaDriverDecision {

	@In protected FacesMessages facesMessages;
	@In protected EntityManager entityManager;
	@In(value="nomeRichiedente",scope = ScopeType.BUSINESS_PROCESS)
	protected String nomeRichiedente;
	
	@BeginTask @EndTask(transition="approva")
	public void approve(){
		Account account = (Account) entityManager.createQuery(
				"select account from Account account " +
				"where account.username = #{nomeRichiedente}")
				.getSingleResult();
		Role r= new Role();
		r.setName("driver");
		r.setAccount(account);
		entityManager.persist(r);
		facesMessages.add("L'utente è stato reso driver");
	}
	@BeginTask @EndTask(transition="rifiuta")
	public void reject(){
		facesMessages.add("La richiesta dell'utente è stata rifiutata");
	}
}
