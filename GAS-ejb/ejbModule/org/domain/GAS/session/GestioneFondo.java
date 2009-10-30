package org.domain.GAS.session;

import java.util.GregorianCalendar;

import org.domain.GAS.crud.AccountHome;
import org.domain.GAS.entity.Account;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name(value="gestioneFondo")
@Scope(ScopeType.SESSION)
public class GestioneFondo {

	@In(value="accountHome", create=true)
	private AccountHome accountHome;
	
	@In(value="currentAccount")
	private Account currentAccount;

	
	public boolean isFondoSufficiente(float prezzo) {
		return currentAccount.getFondo()>=prezzo;
	}
	
	public void plusFondo(float prezzo) {
		currentAccount.setFondo(currentAccount.getFondo()+prezzo);
		accountHome.setAccountUsername(currentAccount.getUsername());
		Account a = accountHome.find();	
		a.setFondo(currentAccount.getFondo());
		accountHome.update();
	}
	
	public void lessFondo(float prezzo) {
		currentAccount.setFondo(currentAccount.getFondo()-prezzo);
		accountHome.setAccountUsername(currentAccount.getUsername());
		Account a = accountHome.find();	
		a.setFondo(currentAccount.getFondo());
		accountHome.update();
	}
	
	public float getFondo() {
		return currentAccount.getFondo();
	}
	
}
