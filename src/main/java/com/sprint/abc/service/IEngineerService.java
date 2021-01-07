package com.sprint.abc.service;

import java.time.LocalDate;
import java.util.List;

import com.sprint.abc.entities.Complaint;
import com.sprint.abc.entities.Engineer;
import com.sprint.abc.exceptions.InValidComplaintIdException;
import com.sprint.abc.exceptions.InvalidEngineerIdException;
public interface IEngineerService {

	
	public List<Complaint> getAllOpenComplaints(int eid) throws InvalidEngineerIdException;
	
	public List<Complaint> getResolvedComplaints(int eid) throws InvalidEngineerIdException;
		
	public void changeComplaintStatus(int eid,int complaintId,String status)throws InValidComplaintIdException; // returns updated Status;
	
	public List<Complaint> getAllComplaints(int eid) throws InvalidEngineerIdException;
	
	public List<Complaint> sortComplaintsByDate(int eid) throws InvalidEngineerIdException;
	
}
