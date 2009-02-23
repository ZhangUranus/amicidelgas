package org.domain.SeamAmiciDelGas.crud;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityHome;

import org.domain.SeamAmiciDelGas.entity.FileUpload;

@Name("fileUploadHome")
public class FileUploadHome extends EntityHome<FileUpload>
{
    @RequestParameter Long fileUploadId;

    @Override
    public Object getId()
    {
        if (fileUploadId == null)
        {
            return super.getId();
        }
        else
        {
            return fileUploadId;
        }
    }

    @Override @Begin
    public void create() {
        super.create();
    }

}
