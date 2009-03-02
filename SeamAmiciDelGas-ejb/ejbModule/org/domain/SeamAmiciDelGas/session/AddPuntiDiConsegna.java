package org.domain.SeamAmiciDelGas.session;

import javax.ejb.Local;

@Local
public interface AddPuntiDiConsegna
{
    // seam-gen method
    public void addPuntiDiConsegna();

    // add additional interface methods here

    public boolean persistExtend();
    
    public void destroy();
}
