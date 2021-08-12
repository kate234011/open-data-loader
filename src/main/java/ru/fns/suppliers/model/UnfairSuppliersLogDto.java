package ru.fns.suppliers.model;
import org.apache.commons.codec.digest.DigestUtils;

public class UnfairSuppliersLogDto {

    private long id;

    private  String fileName;

    private long dateCreated;

    private long dateDownload;

    private String sha256HexHash;

    public UnfairSuppliersLogDto() { }

    public UnfairSuppliersLogDto(
        String fileName,
        long dateCreated
    ) {
        super();
        this.fileName = fileName;
        this.dateCreated = dateCreated;
        this.dateDownload = System.currentTimeMillis();
        this.sha256HexHash = DigestUtils.sha256Hex(fileName).toUpperCase();
    }

    public UnfairSuppliersLogDto(
        String fileName,
        long dateCreated,
        String sha256HexHash
    ) {
        super();
        this.fileName = fileName;
        this.dateCreated = dateCreated;
        this.dateDownload = System.currentTimeMillis();
        this.sha256HexHash = sha256HexHash;
    }

    public long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMd5Hash() {
        return sha256HexHash;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public long getDateDownload() {
        return dateDownload;
    }

}
