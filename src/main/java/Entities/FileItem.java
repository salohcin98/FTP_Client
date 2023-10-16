package Entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class FileItem {
    private StringProperty fname;
    private StringProperty fsize = new SimpleStringProperty("");
    private StringProperty ftype = new SimpleStringProperty("");
    private StringProperty dadded = new SimpleStringProperty("");
    private StringProperty fid = new SimpleStringProperty("");
    private StringProperty fowner = new SimpleStringProperty("");
    private StringProperty fdir = new SimpleStringProperty("");

    private boolean isFolder = false;
    @Getter
    private final List<FileItem> children = new ArrayList<>();

    public FileItem(String fileName, String fileSize, String fileID, String fileOwner, String dateadded){
        this.fname = new SimpleStringProperty(fileName);
        this.fsize = new SimpleStringProperty(fileSize);
        this.fid = new SimpleStringProperty(fileID);
        this.fowner = new SimpleStringProperty(fileOwner);
        this.dadded = new SimpleStringProperty(dateadded);
        this.fdir = new SimpleStringProperty("/mnt/userDir/" + fid + "/" + fname);
    }

    public FileItem(String fileName, String fileSize, String fileOwner){
        this.fname = new SimpleStringProperty(fileName);
        this.fsize = new SimpleStringProperty(fileSize);
        this.fowner = new SimpleStringProperty(fileOwner);
    }

    public FileItem(String folderName, List<FileItem> children){
        this.fname = new SimpleStringProperty(folderName);
        isFolder = true;
        this.children.addAll(children);
    }

    public FileItem(String folderName){
        this.fname = new SimpleStringProperty(folderName);
        isFolder = true;
    }

    public boolean isFolder(){
        return isFolder;
    }

    public void addChildren(List<FileItem> children){
        this.children.addAll(children);
    }

    public void addChildren(FileItem child){
        children.add(child);
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


}