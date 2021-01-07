package com.sprint.abc.service;

import java.util.List;

import com.sprint.abc.entities.Admin;
import com.sprint.abc.entities.Complaint;
import com.sprint.abc.entities.Engineer;
import com.sprint.abc.exceptions.InValidDomainException;
import com.sprint.abc.exceptions.InvalidAdminId;
import com.sprint.abc.exceptions.InvalidEngineerIdException;

public interface AdminService {
	
	public void saveEngineer(Engineer engineer);
	public Engineer changeDomain(int engineerId,String newDomain)throws InValidDomainException,InvalidEngineerIdException;
	public void removeEngineer(int engineerId) throws InvalidEngineerIdException;
	
	public List<Complaint> getComplaintsByProducts(String productCategoryName);
	public List<Complaint> getComplaints(String status);
	public Engineer getEngineer(int id) throws InvalidEngineerIdException;
	
	public void replaceEngineerFromComplaint(int complaintId, int empId)throws InValidDomainException; // replace engineer from the complaint and allocate new engineer
	public void allocateEngineer(int complaintId, int engId) throws InValidDomainException;
	public Admin viewProfile(int adminId) throws InvalidAdminId;
	public void addAdmin(Admin admin);
	
	
}


