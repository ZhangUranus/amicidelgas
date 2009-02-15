package org.domain.SeamAmiciDelGas.session;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import org.domain.SeamAmiciDelGas.entity.Role;

@Name("roleHome")
public class RoleHome extends EntityHome<Role>
{
    @RequestParameter Long roleId;

    @Override
    public Object getId()
    {
        if (roleId == null)
        {
            return super.getId();
        }
        else
        {
            return roleId;
        }
    }

    @Override @Begin
    public void create() {
        super.create();
    }

}
