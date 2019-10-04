package sg.edu.nus.comp.cs3219.viz.common.entity.record;

import sg.edu.nus.comp.cs3219.viz.common.entity.UserProfile;

import javax.persistence.*;

@Exportable(name = "File Record", nameInDB = "file_record")
@Entity
public class FileRecord {

    @EmbeddedId
    private FileId fileId;

    @Column(name = "file_name")
    private String fileName;

    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserProfile userProfile;
/*
    public FileRecord(long userId, String fileNumber, String fileName) {
        this.fileName = fileName;
        this.fileId = new FileId(userId, fileNumber);
    }

    //JPA expects a default constructor
    protected FileRecord() {
    }*/
    public FileRecord() {
        this.fileId = new FileId();
    }

    public UserProfile getUserProfile() {
        return this.userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getFileNumber() {
        return this.fileId.getFileNumber();
    }

    public void setFileNumber(String fileNumber) {
        this.fileId.setFileNumber(fileNumber);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}