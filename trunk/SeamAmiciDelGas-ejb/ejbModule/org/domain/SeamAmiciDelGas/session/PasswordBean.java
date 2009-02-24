package org.domain.SeamAmiciDelGas.session;
import org.hibernate.validator.Length;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;


@Name("passwordBean")
@Scope(ScopeType.SESSION)
public class PasswordBean {
	@Length(max=16 , min=6)
	private String password;
	
	@Length(max=16)
	private String confirm;
	
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getConfirm() { return confirm; }
	public void setConfirm(String confirm) { this.confirm = confirm; }
	public boolean verify() {
		return confirm != null && confirm.equals(password);
	}
}