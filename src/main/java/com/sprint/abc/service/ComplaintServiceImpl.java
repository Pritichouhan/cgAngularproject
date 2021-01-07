package com.sprint.abc.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.abc.entities.Client;
import com.sprint.abc.entities.Complaint;
import com.sprint.abc.entities.Engineer;
import com.sprint.abc.entities.Product;
import com.sprint.abc.exceptions.InValidComplaintIdException;
import com.sprint.abc.exceptions.InvalidClientIdException;
import com.sprint.abc.exceptions.OutOfWarrantyException;
import com.sprint.abc.exceptions.RecordNotFoundException;
import com.sprint.abc.exceptions.ResourceNotFoundException;
import com.sprint.abc.repository.ClientRepository;
import com.sprint.abc.repository.ComplaintRepository;
import com.sprint.abc.repository.EngineerRepository;
import com.sprint.abc.repository.ProductRepository;

import static com.sprint.abc.util.AppConstants.OUT_OF_WARRANTY_CONST;
import static com.sprint.abc.util.AppConstants.COMPLAINT_NOT_FOUND_CONST;
import static com.sprint.abc.util.AppConstants.RECORD_NOT_FOUND_CONST;
import static com.sprint.abc.util.AppConstants.INVALID_CLIENT_ID_CONST;

@Service
public class ComplaintServiceImpl implements ComplaintService {

	private static Logger logger = LoggerFactory.getLogger(ComplaintServiceImpl.class);

	@Autowired
	private ComplaintRepository complaintRepository;
	@Autowired
	private EngineerRepository engineerRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ClientRepository clientRepository;

	/**
	 * This method is used change status of booked complaints
	 * 
	 * @param complaint id This is the first parameter to changeComplaintStatus
	 *                  method - Complaint id
	 * @return String with the status value
	 * @validation if complaint status is 'Open', then can be changed to 'Resolved'
	 *             OR if status is 'Resolved' then can be changed to 'Closed'
	 */

	@Override
	public void changeComplaintStatus(int complaintId) {
		logger.info("Enter ComplaintServiceImpl:: method=changeComplaintStatus");
		Complaint complaint = complaintRepository.findById(complaintId)
				.orElseThrow(() -> new InValidComplaintIdException(COMPLAINT_NOT_FOUND_CONST + complaintId));

		String status = complaint.getStatus();
		if (status.startsWith("Resolved")) {

			complaint.setStatus("Closed");
		}
		logger.info("Exit ComplaintServiceImpl:: method=changeComplaintStatus");
	}

	/**
	 * This method returns the Engineer object corresponding to a Complaint
	 * 
	 * @param complaintId This is the parameter of getEngineer method
	 * @return Engineer object of Engineer for that complaintId
	 */

	@Override
	public Engineer getEngineer(int complaintId) throws InValidComplaintIdException {
		logger.info("Enter ComplaintServiceImpl:: method=getEngineer");
		Complaint complaint = complaintRepository.findById(complaintId)
				.orElseThrow(() -> new InValidComplaintIdException(COMPLAINT_NOT_FOUND_CONST + complaintId));

		logger.info("Exit ComplaintServiceImpl:: method=getEngineer");

		return complaint.getEngineer();
	}

	/**
	 * This method returns the Product object corresponding to a Complaint
	 * 
	 * @param complaintId This is the parameter of getEngineer method
	 * @return Product object of Product corresponding to that complaintId
	 */

	@Override
	public Product getProductByComplaint(int complaintId) throws InValidComplaintIdException {
		logger.info("Enter ComplaintServiceImpl:: method=getProductByComplaint");
		Complaint complaint = complaintRepository.findById(complaintId)
				.orElseThrow(() -> new InValidComplaintIdException(COMPLAINT_NOT_FOUND_CONST + complaintId));
		logger.info("Exit ComplaintServiceImpl:: method=getProductByComplaint");
		return complaint.getProduct();
	}

	/**
	 * This method returns the List of Complaints booked by a Client
	 * 
	 * @param client This is the parameter of getClientAllComplaints method - Client
	 *               object
	 * @return List<Complaint> of all the Complaints for a client
	 */

	@Override
	public List<Complaint> getClientAllComplaints(String clientId) throws RecordNotFoundException {
		logger.info("Enter ComplaintServiceImpl:: method=getClientAllComplaints");
		try {
//			Client client = clientRepository.findById(clientId).get();
			Optional<Client> client = clientRepository.findById(clientId);
			if (!client.isPresent()) {
				throw new ResourceNotFoundException(RECORD_NOT_FOUND_CONST + clientId);
			}
			List<Complaint> clientAllComplaintsList = client.get().getComplaintList();
			if (clientAllComplaintsList.isEmpty()) {
				throw new RecordNotFoundException("Record Not Found");
			}
			logger.info("Exit ComplaintServiceImpl:: method=getClientAllComplaints");
			return client.get().getComplaintList();
		} catch (RecordNotFoundException e) {
			throw new RecordNotFoundException(RECORD_NOT_FOUND_CONST + clientId);
		}

	}

	/**
	 * This method returns the List of all Open Complaints booked by a Client
	 * 
	 * @param client This is the parameter of getClientAllOpenComplaints method -
	 *               Client object
	 * @return List<Complaint> of all the Open Complaints for a client
	 */

	@Override
	public List<Complaint> getClientAllOpenComplaints(String clientId) {
		logger.info("Enter ComplaintServiceImpl:: method=getClientAllOpenComplaints");
//		Client client = clientRepository.findById(clientId).get();
		Optional<Client> client = clientRepository.findById(clientId);
		if (!client.isPresent()) {
			throw new ResourceNotFoundException(RECORD_NOT_FOUND_CONST + clientId);
		}
		List<Complaint> clientAllOpenComplaints = new ArrayList<>();
		List<Complaint> complaints = client.get().getComplaintList();
		try {
			if (complaints.isEmpty()) {
				throw new RecordNotFoundException("Record Not Found");

			} else {
				for (Complaint c : complaints) {
					if (c.getStatus().equalsIgnoreCase("Open"))
						clientAllOpenComplaints.add(c);
				}
				logger.info("Exit ComplaintServiceImpl:: method=getClientAllOpenComplaints");
				return clientAllOpenComplaints;
			}
		} catch (RecordNotFoundException e) {
			throw new RecordNotFoundException(RECORD_NOT_FOUND_CONST + clientId);
		}
	}

	/**
	 * This method returns the deleted complaints
	 * 
	 * @param complaint id This is the parameter of deleteComplaint method -
	 *                  complaint id
	 * @return Complaint object
	 */

	@Override
	public Complaint deleteComplaint(int ComplaintId) {
		logger.info("Enter ComplaintServiceImpl:: method=deleteComplaint");
		Optional<Complaint> complaintObj = complaintRepository.findById(ComplaintId);
		if (!complaintObj.isPresent()) {
			throw new ResourceNotFoundException(RECORD_NOT_FOUND_CONST + ComplaintId);
		} else {
			complaintRepository.delete(complaintObj.get());
		}
		logger.info("Exit ComplaintServiceImpl:: method=deleteComplaint");
		return complaintObj.get();
	}

	@Transactional
	@Override
	public boolean replaceEngineer(int complaintId, int engineerId) {
		logger.info("Enter ComplaintServiceImpl:: method=replaceEngineer");

		Complaint complaint = complaintRepository.findById(complaintId).get();
		Engineer engineer = engineerRepository.findById(engineerId).get();
		if (complaint.getEngineer() == null) {
			complaint.setEngineer(engineer);
			complaintRepository.save(complaint);
		}
		logger.info("Exit ComplaintServiceImpl:: method=replaceEngineer");
		return true;
	}

	/**
	 * This method is used to book complaints
	 * 
	 * @param client    This is the first parameter to bookComplaint method - Client
	 *                  object
	 * @param complaint This is the second parameter to bookComplaint method -
	 *                  Complaint object
	 * @param product   This is the third parameter to bookComplaint method -
	 *                  Product object
	 * @return boolean true, if complaint is booked OR false, if complaint is not
	 *         booked
	 * @exception OutOfWarrantyException if product warranty date is not correct
	 */

	@Transactional
	@Override
	public boolean bookComplaint(String clinetId, Complaint complaint, String productModelNumber)
			throws OutOfWarrantyException {
		logger.info("Enter ComplaintServiceImpl:: method=bookComplaint");

//		Product product = productRepository.findById(productModelNumber).get();
		Optional<Product> product = productRepository.findById(productModelNumber);
		Optional<Client> clientinput = clientRepository.findById(clinetId);

		if (!product.isPresent()) {
			throw new ResourceNotFoundException(RECORD_NOT_FOUND_CONST + productModelNumber);
		}
		if (!clientinput.isPresent()) {
			throw new ResourceNotFoundException(RECORD_NOT_FOUND_CONST + clinetId);
		}
		Client client = product.get().getClientProduct();
		try {
			if (product.get().getClientProduct().getClientId().equals(clinetId)) {
				if (product.get().getWarrantyDate().compareTo(LocalDate.now()) >= 0) {
					complaint.setResolveDate(LocalDate.now().plusDays(15));
					complaint.setClient(client);
					complaint.setProduct(product.get());
					complaint.setStatus("Open");
					complaint.setComplaintDate(LocalDate.now());
					complaintRepository.save(complaint);
					logger.info("Exit ComplaintServiceImpl:: method=bookComplaint");
					return true;
				} else {
					throw new OutOfWarrantyException(OUT_OF_WARRANTY_CONST + productModelNumber);
				}
			} else {
				throw new InvalidClientIdException(INVALID_CLIENT_ID_CONST + clinetId);
			}
		} catch (OutOfWarrantyException e) {
			throw new OutOfWarrantyException(e.getMessage());
		} catch (InvalidClientIdException e) {
			throw new InvalidClientIdException(e.getMessage());
		}

		catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	/**
	 * This method returns the List of all Complaints.
	 */
	@Override
	public List<Complaint> viewAllComplaints() {
		logger.info("Enter ComplaintServiceImpl:: method=viewAllComplaints");

		List<Complaint> list = complaintRepository.findAll();
		logger.info("Exit ComplaintServiceImpl:: method=viewAllComplaints");
		return list;
	}
}