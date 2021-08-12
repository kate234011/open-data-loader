package ru.fns.suppliers.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@RegisterForReflection
@NamedQuery(name = "UnfairSuppliersLog.findAll", query = "SELECT c  FROM UnfairSuppliersLog c")
public class UnfairSuppliersLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column
	private long id;

	@Column
	private String fileName;

	@Column
	private long dateCreated;

	@Column
	private long dateDownload;

	@Column
	private String sha256HexHash;

	public UnfairSuppliersLog( 	long id,
							   	String fileName,
							  	long dateCreated,
							  	long dateDownload,
							  	String sha256HexHash)
	{
		this.id = id;
		this.fileName = fileName;
		this.dateCreated = dateCreated;
		this.dateDownload = dateDownload;
		this.sha256HexHash = sha256HexHash;
	}

	public UnfairSuppliersLog( 	String fileName,
							  	long dateCreated,
							  	long dateDownload,
							  	String sha256HexHash)
	{
		this.fileName = fileName;
		this.dateCreated = dateCreated;
		this.dateDownload = dateDownload;
		this.sha256HexHash = sha256HexHash;
	}

	public UnfairSuppliersLog() {}
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logSequence")
	@SequenceGenerator(name = "logSequence", sequenceName = "logSeq", allocationSize = 1, initialValue = 10)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSha256HexHash() {
		return sha256HexHash;
	}

	public void setSha256HexHash(String sha256HexHash) {
		this.sha256HexHash = sha256HexHash;
	}

	public long getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(long dateCreated) {
		this.dateCreated = dateCreated;
	}

	public long getDateDownload() {
		return dateDownload;
	}

	public void setDateDownload(long dateDownload) {
		this.dateDownload = dateDownload;
	}

	@Override
	public int hashCode() {
		int result = 31 * fileName.hashCode() ;
		result = (int) (31 * result + dateCreated);
		result = 31 * result + sha256HexHash.hashCode();
		return result;
	}
}
