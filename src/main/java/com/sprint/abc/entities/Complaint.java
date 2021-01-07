package com.sprint.abc.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="complaint_tbl_1")
public class Complaint {

	@Id
	@GeneratedValue
	private int complaintId;
	
	@NotEmpty(message = "Complaint name cannot be empty")
	private String complaintName;
	
	private String status; // open , resolve online , resolve after home visit , resolved , closed
	// note : engineer cannot open or close the complaint
	
//	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate resolveDate;
	
	private LocalDate complaintDate;
	


	//	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "engineerid", nullable = true)
	private Engineer engineer;
	
//	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "clientid", nullable = true)
	private Client client;
	
//	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "modelnumber",  nullable = true)
    private Product product ;
 

	public Product getProduct() {
		return product;
	}

	@JsonIgnore
	public void setProduct(Product product) {
		this.product = product;
	}

	public int getComplaintId() {
		return complaintId;
	}

	
	public void setComplaintId(int complaintId) {
		this.complaintId = complaintId;
	}

	public String getComplaintName() {
		return complaintName;
	}

	public void setComplaintName(String complaintName) {
		this.complaintName = complaintName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public LocalDate getResolveDate() {
		return resolveDate;
	}

	public void setResolveDate(LocalDate resolveDate) {
		this.resolveDate = resolveDate;
	}

	public Engineer getEngineer() {
		return engineer;
	}

	@JsonIgnore
	public void setEngineer(Engineer engineer) {
		this.engineer = engineer;
	}

	public Client getClient() {
		return client;
	}

	@JsonIgnore
	public void setClient(Client client) {
		this.client = client;
	}
	
	public LocalDate getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(LocalDate complaintDate) {
		this.complaintDate = complaintDate;
	}
	
//	@Override
//	public String toString() {
//		return "Complaint [complaintId=" + complaintId + ", complaintName=" + complaintName + ", status=" + status
//				+ ", engineer=" + engineer + ", client=" + client + ", product=" + product + "]";
//	}

}
