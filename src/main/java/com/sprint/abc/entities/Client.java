package com.sprint.abc.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="client_tbl_1")
public class Client {

	
	@Id
	@NotEmpty(message="Client id should not be null")
	@Column(name="client_id")
	private String clientId; // unique String name like mike123
	
	@Column(name="password")
	@NotEmpty(message = "Password field cannot be empty")
	private String password;	
	
	@Column(name="address")
	@NotEmpty(message = "Address field cannot be empty")
	private String address;
	
	@Column(name="phone_number")
	@NotNull
	@Pattern(regexp="\\d{10}")
	private String phoneNumber;
	
	@JsonIgnore
	@OneToMany(mappedBy = "clientProduct",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Product> productOwned = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "client",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Complaint> complaintList = new ArrayList<>(); // client can view all complaints

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Product> getProductOwned() {
		return productOwned;
	}

	public void setProductOwned(List<Product> productOwned) {
		this.productOwned = productOwned;
	}

	public List<Complaint> getComplaintList() {
		return complaintList;
	}

	public void setComplaintList(List<Complaint> complaintList) {
		this.complaintList = complaintList;
	}
//	
//	@Override
//	public String toString() {
//		return "Client clientId=" + clientId + ", password=" + password + ", address=" + address + ", phoneNumber="+ phoneNumber + ", productOwned=" + productOwned + ", complaintList=" + complaintList + " ";
//	}
	
	
}
