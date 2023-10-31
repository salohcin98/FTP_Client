package Entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a file
 */
public class FileItem {
    private final StringProperty fname;
    private final StringProperty fsize;
    private final StringProperty fowner;

    private final StringProperty dadded;
    private final StringProperty fid;
    private final StringProperty fdir;

    public FileItem(String fileName, String fileSize, String fileID, String fileOwner, String dateadded){
        this.fname = new SimpleStringProperty(fileName);
        this.fsize = new SimpleStringProperty(fileSize);
        this.fowner = new SimpleStringProperty(fileOwner);

        this.dadded = new SimpleStringProperty(dateadded);
        this.fid = new SimpleStringProperty(fileID);
        this.fdir = new SimpleStringProperty("/mnt/userDir/" + fid + "/" + fname);
    }

    public FileItem(String fileName, String fileSize, String fileOwner){
        this.fname = new SimpleStringProperty(fileName);
        this.fsize = new SimpleStringProperty(fileSize);
        this.fowner = new SimpleStringProperty(fileOwner);

        this.dadded = new SimpleStringProperty("");
        this.fid = new SimpleStringProperty("");
        this.fdir = new SimpleStringProperty("");
    }

    public String getFname() { return fname.get(); }

    public String getFsize() { return fsize.get(); }

    public String getDadded() { return dadded.get(); }

    public String getFowner() { return fowner.get(); }

    public String getFdir() { return fdir.get(); }

    public String getFid() { return fid.get(); }
}