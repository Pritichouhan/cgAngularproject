package com.sprint.abc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprint.abc.entities.Admin;
import com.sprint.abc.entities.Complaint;
import com.sprint.abc.entities.Engineer;
import com.sprint.abc.exceptions.InValidDomainException;
import com.sprint.abc.exceptions.InvalidAdminId;
import com.sprint.abc.exceptions.InvalidEngineerIdException;
import com.sprint.abc.repository.AdminRepository;
import com.sprint.abc.repository.ComplaintRepository;
import com.sprint.abc.repository.EngineerRepository;
import com.sprint.abc.repository.ProductRepository;
import com.sprint.abc.util.AppConstants;


@Service
public class AdminServiceImpl implements AdminService {
	
	private static Logger logger=LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private AdminRepository adminDao;
	@Autowired
	private EngineerRepository engineerDao;
	@Autowired
	private ComplaintRepository complaintDao;
	@Autowired
	private ProductRepository productDao;

	/* This method is use to add Engineer to the database */

	@Transactional
	@Override
	public void saveEngineer(Engineer engineer) {
		engineerDao.save(engineer);
	}

	/*
	 * This method is used to change the domain of Engineer by comparing whether the
	 * domain he belongs to is correct or not and Id is correct or not. If correct,
	 * then change its domain otherwise throws exception.
	 */

	@Override
	public Engineer changeDomain(int engineerId, String newDomain) {
		Optional<Engineer> engineer = engineerDao.findById(engineerId);
		Engineer e1 = null;
		if (engineer.isPresent()) {
			if (engineer.get().getDomain().equalsIgnoreCase("TV") || engineer.get().getDomain().equalsIgnoreCase("AC")
					|| engineer.get().getDomain().equalsIgnoreCase("Mobile Phone")
					|| engineer.get().getDomain().equalsIgnoreCase("Washing Machine")) {
				if (engineer.get().getEngineerId() != 0) {
					try {
						engineer.get().setDomain(newDomain);
						e1 = engineerDao.save(engineer.get());
					} catch (Exception e) {

						throw new InValidDomainException(AppConstants.INVALID_DOMAIN_EXCEPTION);
					}
				} else {
					throw new InvalidEngineerIdException(AppConstants.INVALID_ENGINEER_ID_EXCEPTION + engineerId);
				}

			} else {
				throw new InValidDomainException(AppConstants.INVALID_DOMAIN_EXCEPTION + engineer.get().getDomain());

			}
		} else {
			throw new InvalidEngineerIdException(AppConstants.INVALID_ENGINEER_ID_EXCEPTION + " " + engineerId);
		}

		return e1;

	}

	/*
	 * This method is used to remove the engineer from database by comparing the
	 * engineer Id If id is there in database then it will remove otherwise will
	 * throw invalidId exception.
	 */

	@Override
	public void removeEngineer(int engineerId) {

		Optional<Engineer> emp = engineerDao.findById(engineerId); // .get();
		if (emp == null) {
			throw new InvalidEngineerIdException(AppConstants.INVALID_ENGINEER_ID_EXCEPTION + engineerId);
		} else {
			try {

				engineerDao.deleteById(engineerId);
			} catch (RuntimeException e) {
				throw new InvalidEngineerIdException(AppConstants.INVALID_ENGINEER_ID_EXCEPTION + engineerId);
			}

		}

	}

	/*
	 * This method will get all the complaint details by passing productCategoryName
	 * as argument
	 */

	@Override
	public List<Complaint> getComplaintsByProducts(String productCategoryName) {
		logger.info("Enter AdminServiceImpl:: method=getComplaintsByProducts");
		List<Complaint> clist = new ArrayList<>();
		List<Complaint> list = complaintDao.findAll();
		for (Complaint lists : list) {
			if (lists.getProduct().getProductCategoryName().equalsIgnoreCase(productCategoryName)) {
				clist.add(lists);
			}
		}
		logger.info("Exit AdminServiceImpl:: method=getComplaintsByProducts");
		return clist;
	}

	/*
	 * This method will get all the complaint details by passing productCategoryName
	 * from product table and status from Complaint table as argument
	 */

	@Override
	public List<Complaint> getComplaints(String status) {
		logger.info("Enter AdminServiceImpl:: method=getComplaints");
		List<Complaint> list = adminDao.getComplaintsByStatus(status);
		if (!list.isEmpty()) {
			return list;
		}
		logger.info("Exit AdminServiceImpl:: method=getComplaints");
		return list;
	}

	/*
	 * This method will replace engineer to particular complainId by comparing the
	 * domain he belongs to is correct or not. If correct, then engineer will be
	 * replaced otherwise, will throw invaliDomian exception
	 */

	@Transactional
	@Override
	public void replaceEngineerFromComplaint(int complaintId, int empId) {
		logger.info("Enter AdminServiceImpl:: method=replaceEngineerFromComplaint");
		Complaint complaint = complaintDao.findById(complaintId).get();
		Engineer engineer = engineerDao.findById(empId).get();
		if (complaint.getEngineer() != null) {
			if (complaint.getProduct().getProductCategoryName().equalsIgnoreCase(engineer.getDomain())) {
				complaint.setEngineer(engineer);
				
				complaintDao.save(complaint);
				logger.info("Exit AdminServiceImpl:: method=replaceEngineerFromComplaint");
			} else {
				throw new InValidDomainException(AppConstants.INVALID_DOMAIN_EXCEPTION + engineer.getDomain());
			}
			
		}
	}

	/*
	 * This method will give Engineer details from the database. If correct, then
	 * engineer will be replaced otherwise, will throw invaliDomian exception
	 */

	@Override
	public Engineer getEngineer(int id) {
		logger.info("Enter AdminServiceImpl:: method=getEngineer");
		Optional<Engineer> e = engineerDao.findById(id);
		if (!e.isPresent()) {
			throw new InvalidEngineerIdException(AppConstants.INVALID_ENGINEER_ID_EXCEPTION + " " + id);
		}
		logger.info("Exit AdminServiceImpl:: method=getEngineer");
		return e.get();

	}

	/*
	 * This method will allocate engineer to particular complainId by comparing the
	 * domain he belongs to is correct or not. If correct, then engineer will be
	 * allocated otherwise, will throw invaliDomian exception
	 */

	@Transactional
	@Override
	public void allocateEngineer(int complaintId, int engId) {
		logger.info("Enter AdminServiceImpl:: method=allocateEngineer");
		Complaint complaint = complaintDao.findById(complaintId).get();
		Engineer engineer = engineerDao.findById(engId).get();
		if (complaint.getEngineer() == null) {
			if (complaint.getProduct().getProductCategoryName().equalsIgnoreCase(engineer.getDomain())) {
				complaint.setEngineer(engineer);
				complaintDao.save(complaint);
				logger.info("Exit AdminServiceImpl:: method=allocateEngineer");
			} else {
				throw new InValidDomainException(AppConstants.INVALID_DOMAIN_EXCEPTION + engineer.getDomain());
			}
		}

	}
	
	/* Fetch admin data from Admin table using admintId
	 */
	@Override
	public Admin viewProfile(int adminId) {

		Admin admin = adminDao.findById(adminId).get();
		if (admin == null) {
			throw new InvalidAdminId(AppConstants.INVALID_AMIN_ID + adminId);
		}
		return admin;
	}
	
	
	/* Add admin data in Admin table
	 */
	@Override
	public void addAdmin(Admin admin) {
		adminDao.save(admin);

	}

}
