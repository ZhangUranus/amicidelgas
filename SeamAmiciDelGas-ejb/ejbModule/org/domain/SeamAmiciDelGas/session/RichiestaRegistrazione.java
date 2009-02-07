package org.domain.SeamAmiciDelGas.session;

import javax.ejb.Local;

@Local
public interface RichiestaRegistrazione
{
    public void richiestaRegistrazione();
    public UtenteHome getUtenteHome();
    public void setUtenteHome(UtenteHome utenteHome);
    public AccountHome getAccountHome();
    public void setAccountHome(AccountHome accountHome);
    public void destroy();
    public void dummyAction();
    // add additional interface methods here

}
