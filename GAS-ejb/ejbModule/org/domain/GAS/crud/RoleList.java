package org.domain.GAS.crud;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.domain.GAS.entity.Role;

@Name("roleList")
public class RoleList extends EntityQuery<Role>
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7702543755447571597L;

	public RoleList()
    {
        setEjbql("select role from Role role");
    }
}
