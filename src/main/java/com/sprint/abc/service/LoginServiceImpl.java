package com.sprint.abc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.abc.entities.Admin;
import com.sprint.abc.entities.Client;
import com.sprint.abc.entities.Engineer;
import com.sprint.abc.exceptions.InvalidAdminId;
import com.sprint.abc.exceptions.InvalidClientIdException;
import com.sprint.abc.exceptions.InvalidEngineerIdException;
import com.sprint.abc.exceptions.InvalidPasswordException;
import com.sprint.abc.repository.AdminRepository;
import com.sprint.abc.repository.ClientRepository;
import com.sprint.abc.repository.EngineerRepository;

import static com.sprint.abc.util.AppConstants.*;

@Service
public class LoginServiceImpl implements LoginService {

	private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private EngineerRepository engineerrepository;

	@Autowired
	private AdminRepository adminRepository;

	public Client login(String clientId, String password) {
		logger.info("Enter LoginServiceImpl:: method=login");
		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new InvalidClientIdException(INVALID_CLIENT_ID_CONST + clientId));
		if (!(client.getPassword().equals(password)))
			throw new InvalidPasswordException(INVALID_PASSWORD);
		logger.info("Exit LoginServiceImpl:: method=login");
		return client;
	}

	@Override
	public Client logout(String clientId) {
		logger.info("Enter LoginServiceImpl:: method=logout");
		Client client = clientRepository.findById(clientId)
				.orElseThrow(() -> new InvalidClientIdException(INVALID_CLIENT_ID_CONST + clientId));
		logger.info("Exit LoginServiceImpl:: method=logout");
		return client;
	}

	@Override
	public Engineer login2(String EngineerId, String password) {
		logger.info("Enter LoginServiceImpl:: method=login2");
		int id = Integer.parseInt(EngineerId);
		Engineer e = engineerrepository.findById(id).orElseThrow(
				() -> new InvalidEngineerIdException("Engineer" + "with id " + EngineerId + " does not exist"));
		if (!(e.getPassword().equals(password))) {
			throw new InvalidPasswordException("InCorrect Password");
		}
		logger.info("Exit LoginServiceImpl:: method=login2");
		return e;

	}

	@Override
	public Engineer logout2(int EngineerId) {
		logger.info("Enter LoginServiceImpl:: method=logout2");
		Engineer e = engineerrepository.findById(EngineerId).orElseThrow(
				() -> new InvalidEngineerIdException("Engineer" + "with id " + EngineerId + " does not exist"));
		logger.info("Exit LoginServiceImpl:: method=logout2");
		return e;
	}

	@Override
	public Admin login3(String AdminId, String password) {
		logger.info("Enter LoginServiceImpl:: method=login3");
		int id = Integer.parseInt(AdminId);
		Admin e = adminRepository.findById(id)
				.orElseThrow(() -> new InvalidAdminId("Admin" + "with id " + AdminId + " does not exist"));
		if (!(e.getPassword().equals(password))) {
			throw new InvalidPasswordException("InCorrect Password");
		}
		logger.info("Exit LoginServiceImpl:: method=login3");
		return e;

	}

	@Override
	public Admin logout3(int AdminId) {
		logger.info("Enter LoginServiceImpl:: method=logout3");
		Admin e = adminRepository.findById(AdminId)
				.orElseThrow(() -> new InvalidAdminId("Admin" + "with id " + AdminId + " does not exist"));
		logger.info("Exit LoginServiceImpl:: method=logout3");
		return e;
	}

}
