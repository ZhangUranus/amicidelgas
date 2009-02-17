package org.domain.SeamAmiciDelGas.session;

import javax.ejb.Local;
import javax.faces.event.ActionEvent;

@Local
public interface RichiestaRegistrazione
{
    // seam-gen method
    public void richiestaRegistrazione();
    // add additional interface methods here
    
    public void destroy();

}
