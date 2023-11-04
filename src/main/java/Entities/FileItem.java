package Entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class FileItem implements Item {
    private StringProperty fname;
    private StringProperty fsize = new SimpleStringProperty("");
    private StringProperty ftype = new SimpleStringProperty("");
    private StringProperty dadded = new SimpleStringProperty("");
    private StringProperty fid = new SimpleStringProperty("");
    private StringProperty fowner = new SimpleStringProperty("");
    private StringProperty fdir = new SimpleStringProperty("");

    public FileItem(String fname, String fsize, String fid, String fowner, String dadded){
        this.fname = new SimpleStringProperty(fname);
        this.fsize = new SimpleStringProperty(fsize);
        this.fid = new SimpleStringProperty(fid);
        this.fowner = new SimpleStringProperty(fowner);
        this.dadded = new SimpleStringProperty(dadded);

        this.fdir = new SimpleStringProperty("/mnt/userDir/" + fid + "/" + fname);
    }

    public FileItem(String fileName, String fileSize, String fileOwner){
        this.fname = new SimpleStringProperty(fileName);
        this.fsize = new SimpleStringProperty(fileSize);
        this.fowner = new SimpleStringProperty(fileOwner);
    }

    public String getFname() {
        return fname.get();
    }

    public String getFtype() {
        return ftype.get();
    }

    public String getFsize() {
        return fsize.get();
    }

    public String getDadded() {
        return dadded.get();
    }

    public String getFowner() { return fowner.get(); }

    public String getFdir() { return fdir.get(); }

    public String getFid() { return fid.get(); }

    public String toString(){
        return String.format("fileItem{ id: %s, name: %s, type: %s, owner: %s, path: %s, size: %s, added: %s }", getFid(), getFname(), getFtype(), getFowner(), getFdir(), getFsize(), getDadded());
    }

}