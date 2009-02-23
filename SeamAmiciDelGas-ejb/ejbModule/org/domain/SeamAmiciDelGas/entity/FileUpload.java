package org.domain.SeamAmiciDelGas.entity;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Version;

import org.domain.SeamAmiciDelGas.session.File;
import org.hibernate.validator.Length;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;



@Entity
@Name("newFileUpload")
//@Table(name = "fileupload", catalog = "database_gas")
public class FileUpload implements Serializable
{
	
    // seam-gen attributes (you should probably edit these)
    private Long id;
    private Integer version;
    private String name;
    private String savePath; 
    private String filepath; 
    private String filename; 
    private String contentType;
    private int length;
    private long timeStamp;
    private boolean useFlash = false;
    private int size;
    
    private int uploadsAvailable = 5;
    private boolean autoUpload = false;
    
    private ArrayList<File> files = new ArrayList<File>();

    // add additional entity attributes

    // seam-gen attribute getters/setters with annotations (you probably should edit)

    @Id @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    private void setVersion(Integer version) {
        this.version = version;
    }

    @Length(max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void listener(UploadEvent event) throws IOException
    {
    	System.out.println("in listener");
    	UploadItem item = event.getUploadItem();
    	System.out.println("in listener11111");
 	    File file = new File();
 	   System.out.println("in listener2222222"+item.getData().length);
 	  System.out.println("in listener2222222"+item.getFileName());
 	  file.setName(item.getFileName());
 	   System.out.println("in listener3333333333");
 	   file.setLength(item.getData().length);
 	   System.out.println("in listener444444444");
	    file.setData(item.getData());
	    System.out.println("in listener55555555555");
 	    files.add(file);
 	   System.out.println("in listener666666666");
	    uploadsAvailable--;
	    System.out.println("in listener777777777");
    }
    
    public void paint(OutputStream outputStream, Object obj) throws IOException
    {
    	outputStream.write((byte[])obj);
    	System.out.println("FileUploadBean.paint()"); 

    }
    
    public void clearUploadData()
    {
    	files.clear();
    	setUploadsAvailable(5);
    }

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getUploadsAvailable() {
		return uploadsAvailable;
	}

	public void setUploadsAvailable(int uploadsAvailable) {
		this.uploadsAvailable = uploadsAvailable;
	}

	public boolean isAutoUpload() {
		return autoUpload;
	}

	public void setAutoUpload(boolean autoUpload) {
		this.autoUpload = autoUpload;
	}

	public ArrayList<File> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<File> files) {
		this.files = files;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public long getTimeStamp() {
		return System.currentTimeMillis();  
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public boolean isUseFlash() {
		return useFlash;
	}

	public void setUseFlash(boolean useFlash) {
		this.useFlash = useFlash;
	}

	public int getSize() 
	{
		if (getFiles().size() > 0)
			return getFiles().size(); 
		else
			return 0;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
