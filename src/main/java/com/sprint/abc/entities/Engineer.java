package com.sprint.abc.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="engineer_tbl_1")
public class Engineer {
	
	@Id
	@Min(value=1,message="Id should not be zero")
	private int engineerId; // treat like login id
	
	@NotEmpty(message = "Password must not be empty")
	private String password;
	
	@NotEmpty(message = "Engineer name must not be empty")
	private String engineerName;
	
	@NotEmpty(message = "Domain must not be empty")
	private String domain; // like washing machine , AC, Mobile phone
	
	@JsonIgnore
	@OneToMany(mappedBy = "engineer",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Complaint> complaints;
	
	
	public int getEngineerId() {
		return engineerId;
	}
	public void setEngineerId(int engineerId) {
		this.engineerId = engineerId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEngineerName() {
		return engineerName;
	}
	public void setEngineerName(String engineerName) {
		this.engineerName = engineerName;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public List<Complaint> getComplaints() {
		return complaints;
	}
	public void setComplaints(List<Complaint> complaints) {
		this.complaints = complaints;
	}
	
	
	
	
}
