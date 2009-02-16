package org.domain.SeamAmiciDelGas.crud;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.domain.SeamAmiciDelGas.entity.Role;

@Name("roleList")
public class RoleList extends EntityQuery<Role>
{
    public RoleList()
    {
        setEjbql("select role from Role role");
    }
}
