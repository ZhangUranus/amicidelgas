package org.domain.SeamAmiciDelGas.crud;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.domain.SeamAmiciDelGas.entity.FileUpload;

@Name("fileUploadList")
public class FileUploadList extends EntityQuery<FileUpload>
{
    public FileUploadList()
    {
        setEjbql("select fileUpload from FileUpload fileUpload");
    }
}
