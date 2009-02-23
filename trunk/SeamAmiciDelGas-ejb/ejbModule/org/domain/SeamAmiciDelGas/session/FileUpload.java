package org.domain.SeamAmiciDelGas.session;

import java.io.*;


import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Version;
import org.hibernate.validator.Length;
import org.jboss.seam.ScopeType;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;



@Name("newFileUpload")
@Scope(value=ScopeType.SESSION)
//@Table(name = "fileupload", catalog = "database_gas")
public class FileUpload implements Serializable
{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// seam-gen attributes (you should probably edit these)
    private Long id;
    private Integer version;
    private String name;
    private String savePath ;
    private String filepath = System.getProperty("user.home")+"/Desktop/fileupload/"; 
    private String filename; 
    private String contentType;
    private int length;
    private long timeStamp;
    private boolean useFlash = false;
    private int size;
    
    private int uploadsAvailable = 1;
    private boolean autoUpload = false;
    
    //private ArrayList<File> file = new ArrayList<File>();
    private File file;

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
    	UploadItem item = event.getUploadItem();
    	filename=item.getFileName();
    	file = new File(filepath+filename);
    	length = item.getData().length;
    	PrintStream f = new PrintStream(new FileOutputStream(file));
    	f.write(item.getData());
    	f.flush();
	    uploadsAvailable--;
	    size=1;
    }
    
    public void clearUploadData()
    {
    	//files.clear();
    	System.out.println("cancello?");
    	System.out.println(file.exists());
    	file.delete();
    	
    	uploadsAvailable = 1;
    	size = 0;
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
/*
	public ArrayList<File> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<File> files) {
		this.files = files;
	}
*/
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
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
	{/*
		if (getFiles().size() > 0)
			return getFiles().size(); 
		else
			return 0;*/
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	/*
	@Destroy @Remove
    public void destroy() {}
*/
}
