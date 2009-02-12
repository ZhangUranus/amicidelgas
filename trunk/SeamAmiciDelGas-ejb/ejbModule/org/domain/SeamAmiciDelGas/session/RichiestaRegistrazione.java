package org.domain.SeamAmiciDelGas.session;

import javax.ejb.Local;

import org.domain.SeamAmiciDelGas.crud.AccountHome;
import org.domain.SeamAmiciDelGas.crud.ComuneHome;
import org.domain.SeamAmiciDelGas.crud.ProvincesHome;
import org.domain.SeamAmiciDelGas.crud.UtenteHome;

@Local
public interface RichiestaRegistrazione
{
    public void richiestaRegistrazione();
    public UtenteHome getUtenteHome();
    public void setUtenteHome(UtenteHome utenteHome);
    public void setProvincesHome(ProvincesHome provincesHome);
    public ProvincesHome getProvincesHome();
    public void setComuneHome(ComuneHome comuneHome);
    public ComuneHome getComuneHome();
    public AccountHome getAccountHome();
    public void setAccountHome(AccountHome accountHome);
    public void destroy();
    public void dummyAction();
    // add additional interface methods here

}
