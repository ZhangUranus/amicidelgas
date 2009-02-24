package org.domain.SeamAmiciDelGas.session;

import java.io.*;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Version;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
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
    private String savePath;
    private String filepath = System.getProperty("user.home")+"/Desktop/fileupload/"; 
    private String filename; 
    private int length;
    private boolean useFlash = false;
    private int size;
    
    private int uploadsAvailable = 1;
    private boolean autoUpload = false;
    
  //  private ArrayList<File> files = new ArrayList<File>();
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
    	savePath = filepath+filename;
    	file = new File(savePath);
    	length = item.getData().length;
    	PrintStream f = new PrintStream(new FileOutputStream(file));
    	f.write(item.getData());
    	f.flush();
    	//files.add(file);
	    uploadsAvailable--;
	    size=1;
	    //size=files.size();
    }
    
    public void clearUploadData()
    {
    	//files.clear();
    	System.out.println("cancello?");
    	
    	file.delete();
    	//files.clear();
    	uploadsAvailable++;
    	size = 0;
    }

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
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
	
	public boolean isUseFlash() {
		return useFlash;
	}

	public void setUseFlash(boolean useFlash) {
		this.useFlash = useFlash;
	}

	public int getSize() 
	{
	/*	if (getFiles().size() > 0)
			return getFiles().size(); 
		else
			return 0;
*/
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	@NotNull
	public String getSavePath() {
		return savePath;
	}
}