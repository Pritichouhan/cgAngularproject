
package com.sprint.abc.service;

import java.util.List;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprint.abc.entities.Complaint;
import com.sprint.abc.entities.Engineer;
import com.sprint.abc.exceptions.InValidComplaintIdException;
import com.sprint.abc.exceptions.InvalidEngineerIdException;
import com.sprint.abc.repository.ComplaintRepository;
import com.sprint.abc.repository.EngineerRepository;

@Service
public class IEngineerServiceImpl implements IEngineerService {

	private static Logger logger = LoggerFactory.getLogger(IEngineerServiceImpl.class);

	@Autowired
	private EngineerRepository engineerrepository;

	@Autowired
	private ComplaintRepository complaintrepository;

	/**
	 * This Method Returns a list of Complaints, status as open.
	 * 
	 * @param e This is the first parameter for this method - Engineer Object
	 */

	@Override
	public List<Complaint> getAllOpenComplaints(int eid) throws InvalidEngineerIdException {
		logger.info("Enter IEngineerServiceImpl:: method=getAllOpenComplaints");
		Optional<Engineer> e = engineerrepository.findById(eid);
		if (!e.isPresent()) {
			throw new InvalidEngineerIdException("Engineer with id " + eid + " does not exist.");
		}
		List<Complaint> clist = engineerrepository.getAllOpenComplaints(eid);
		logger.info("Exit IEngineerServiceImpl:: method=getAllOpenComplaints");
		return clist;
	}

	/**
	 * This Method Returns a list of Complaints, status as resolved.
	 * 
	 * @param e This is the first parameter for this method - Engineer Object
	 */

	@Override
	public List<Complaint> getResolvedComplaints(int eid) throws InvalidEngineerIdException {
		logger.info("Enter IEngineerServiceImpl:: method=getResolvedComplaints");
		Optional<Engineer> e = engineerrepository.findById(eid);
		if (!e.isPresent()) {
			throw new InvalidEngineerIdException("Engineer with id " + eid + " does not exist.");
		}
		List<Complaint> clist = engineerrepository.getResolvedComplaints(eid);
		logger.info("Exit IEngineerServiceImpl:: method=getResolvedComplaints");
		return clist;
	}

	/**
	 * This Method updates or changes the complaint table from complaint table.
	 * 
	 * @param complaintId This is the first parameter for this method - integer
	 *                    Object
	 */
	@Transactional
	@Override
	public void changeComplaintStatus(int eid, int complaintId, String status) throws InValidComplaintIdException {
		logger.info("Enter IEngineerServiceImpl:: method=changeComplaintStatus");

		Optional<Complaint> optionalComplaint = complaintrepository.findById(complaintId);
		if (optionalComplaint.isPresent()) {
			Complaint c = optionalComplaint.get();
			if (c.getEngineer().getEngineerId() == eid) {
				engineerrepository.changeComplaintStatus(complaintId, status);
			} else {
				throw new InvalidEngineerIdException("This Complaint is not assigned to engineer id" + eid);
			}
		} else {
			throw new InValidComplaintIdException("Complaint with id " + complaintId + " does not exist");
		}
		logger.info("Exit IEngineerServiceImpl:: method=changeComplaintStatus");

	}

	/**
	 * This Method Returns a list of all Complaints based on engineer id.
	 * 
	 * @param eid This is the parameter for this method - Engineer eid
	 */
	@Override
	public List<Complaint> getAllComplaints(int eid) throws InvalidEngineerIdException {
		logger.info("Enter IEngineerServiceImpl:: method=getAllComplaints");
		Optional<Engineer> e = engineerrepository.findById(eid);
		if (!e.isPresent()) {
			throw new InvalidEngineerIdException("Engineer with id " + eid + " does not exist.");
		}
		List<Complaint> clist = engineerrepository.getAllComplaints(eid);
		logger.info("Exit IEngineerServiceImpl:: method=getAllComplaints");
		return clist;
	}

	/**
	 * This Method Returns a list of Complaints according to the date.
	 * 
	 * @param eid This is the first parameter for this method - Engineer eid
	 * 
	 */

	@Override
	public List<Complaint> sortComplaintsByDate(int eid) throws InvalidEngineerIdException {
		logger.info("Enter IEngineerServiceImpl:: method=sortComplaintsByDate");

		Optional<Engineer> e = engineerrepository.findById(eid);
		if (!e.isPresent()) {
			throw new InvalidEngineerIdException("Engineer with id " + eid + " does not exist.");
		}
		List<Complaint> clist = engineerrepository.sortComplaintsByDate(eid);
		logger.info("Exit IEngineerServiceImpl:: method=sortComplaintsByDate");
		return clist;
	}

}
