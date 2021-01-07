package com.sprint.abc.service;

import static com.sprint.abc.util.AppConstants.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprint.abc.exceptions.InvalidClientIdException;
import com.sprint.abc.exceptions.OperationFailedException;
import com.sprint.abc.entities.Client;
import com.sprint.abc.entities.Complaint;
import com.sprint.abc.exceptions.ResourceNotFoundException;
import com.sprint.abc.repository.ClientRepository;
import com.sprint.abc.repository.ComplaintRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private static Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private ComplaintRepository complaintRepository;

	/**
	 * Register a client user using client object as parameter
	 *
	 * @param : client object is passed as parameter
	 * @return : client object
	 */

	@Transactional
	@Override
	public Client saveClient(Client client) {

		Client clientObj = null;
		try {
			Optional<Client> clientAvailable = clientRepository.findById(client.getClientId());
			if (clientAvailable.isPresent())
				throw new InvalidClientIdException(INVALID_CLIENT_ID_CONST + client.getClientId());
			clientObj = clientRepository.save(client);
		} catch (InvalidClientIdException e) {
			throw new InvalidClientIdException(e.getMessage());
		} catch (Exception e) {
			throw new OperationFailedException(OPERATION_FAILED_CONST);
		}
		return clientObj;
	}

	/**
	 * view user profile of the user by provided user id
	 *
	 * @param : client id is provided as parameter
	 * @return : client object
	 */

	@Override
	public Client findClient(String clientId) {
		logger.info("Enter ClientServiceIml:: method=findClient");

		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new InvalidClientIdException(INVALID_CLIENT_ID_CONST + clientId));
		logger.info("Exit ClientServiceIml:: method=findClient");
		return client;
	}

	/**
	 * view all client complaint sorted by registration date
	 *
	 * @param : client id is passed as parameter
	 * @return : list of complaints
	 */

	@Override
	public List<Complaint> getClientAllComplaintSorted(String clientId) {
		logger.info("Enter ClientServiceIml:: method=getClientAllComplaintSorted");

		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new InvalidClientIdException(INVALID_CLIENT_ID_CONST + clientId));
		List<Complaint> clientAllComplaints = client.getComplaintList();
		if (clientAllComplaints == null)
			throw new ResourceNotFoundException(RECORD_NOT_FOUND_CONST + clientId);
		Comparator<Complaint> registrationDate = new Comparator<Complaint>() {
			public int compare(Complaint c1, Complaint c2) {
				return (c2.getResolveDate()).compareTo(c1.getResolveDate());
			}
		};
		Collections.sort(clientAllComplaints, registrationDate);
		logger.info("Exit ClientServiceIml:: method=getClientAllComplaintSorted");
		return clientAllComplaints;
	}

	/**
	 * change complaint status of a particular client complaint
	 *
	 * @param : clientid as first parameter , complaint is as second parameter
	 * @return : string
	 */
	@Override
	public String changeComplaintStatus(String clientId, Integer complaintId) {
		logger.info("Enter ClientServiceIml:: method=changeComplaintStatus");

		String status = "failed";
		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new InvalidClientIdException(INVALID_CLIENT_ID_CONST + clientId));
		Complaint clientComplaint = complaintRepository.findById(complaintId)
				.orElseThrow(() -> new ResourceNotFoundException(COMPLAINT_NOT_FOUND_CONST + complaintId));
		if (clientId != clientComplaint.getClient().getClientId())
			throw new ResourceNotFoundException(RECORD_NOT_FOUND_CONST + clientId);
		try {
			clientComplaint.setStatus("Close");
			complaintRepository.saveAndFlush(clientComplaint);
			status = "successful";
		} catch (Exception e) {
			throw new OperationFailedException(OPERATION_FAILED_CONST);
		}
		logger.info("Exit ClientServiceIml:: method=changeComplaintStatus");

		return status;
	}
}
