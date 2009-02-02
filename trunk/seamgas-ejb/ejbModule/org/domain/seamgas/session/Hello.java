package org.domain.seamgas.session;

import javax.ejb.Local;

@Local
public interface Hello
{
    public void hello();
    public String getValue();
    public void setValue(String value);
    public void destroy();

    // add additional interface methods here

}
