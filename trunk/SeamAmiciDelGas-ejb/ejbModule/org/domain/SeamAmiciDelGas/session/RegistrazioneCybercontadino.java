package org.domain.SeamAmiciDelGas.session;

import javax.ejb.Local;

@Local
public interface RegistrazioneCybercontadino
{
    // seam-gen method
    public boolean registrazioneCyberContadino();

    // add additional interface methods here
    public void destroy();

}
