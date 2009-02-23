package org.domain.SeamAmiciDelGas.session;

import org.jboss.seam.annotations.Name;

@Name("file")
public class File {
	
	private String Name;
    private long length;
    private byte[] data;
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public long getLength() {
        return length;
    }
    public void setLength(long length) {
        this.length = length;
    }


}
