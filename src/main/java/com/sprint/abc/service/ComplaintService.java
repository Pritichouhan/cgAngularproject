package com.sprint.abc.service;

import java.util.List;

import com.sprint.abc.entities.Client;
import com.sprint.abc.entities.Complaint;
import com.sprint.abc.entities.Engineer;
import com.sprint.abc.entities.Product;
import com.sprint.abc.exceptions.InValidComplaintIdException;
import com.sprint.abc.exceptions.OutOfWarrantyException;
import com.sprint.abc.exceptions.RecordNotFoundException;


public interface ComplaintService {
	
	
	public void changeComplaintStatus(int complaintId);
	
	public Engineer getEngineer(int complaintId)throws InValidComplaintIdException;
	public Product getProductByComplaint(int complaintId)throws InValidComplaintIdException;
	
	public boolean bookComplaint(String clinetId,Complaint complaint,String productModelNumber)throws OutOfWarrantyException;
	
	public List<Complaint> getClientAllComplaints(String clientId) throws RecordNotFoundException;
	
	public List<Complaint> getClientAllOpenComplaints(String clientId);
	
	public Complaint deleteComplaint(int ComplaintId);
	
	public boolean replaceEngineer(int complaintId, int engineerId);
	
	public List<Complaint> viewAllComplaints();
}
