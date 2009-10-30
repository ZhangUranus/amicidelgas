package org.domain.GAS.session;

import javax.ejb.Local;

@Local
public interface RegistrazioneCybercontadino
{
    // seam-gen method
    public boolean registrazioneCybercontadino();
    
    public String action();
    
   // public boolean testProcesso();

    // add additional interface methods here
    public void destroy();

}
