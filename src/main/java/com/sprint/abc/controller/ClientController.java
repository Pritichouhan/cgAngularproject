package com.sprint.abc.controller;

import static com.sprint.abc.util.AppConstants.LOGOUT_SUCCESS;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.abc.entities.Client;
import com.sprint.abc.entities.Complaint;
import com.sprint.abc.payload.BaseResponse;
import com.sprint.abc.service.ClientService;
import com.sprint.abc.service.LoginService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/client")
@Api(value = "Client", description = "Operations performed by client")
@Validated
public class ClientController {

	private static Logger logger = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private ClientService clientService;

	@Autowired
	private LoginService loginService;

	// REGISTER CLIENT
	@PostMapping("/add")
	@ApiOperation(value = "Register a client")
	public ResponseEntity<?> saveClient(@Valid @RequestBody Client client) {
		logger.info("Enter ClientController:: method=saveClient");

		Client clientObj = clientService.saveClient(client);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(clientObj);
		logger.info("Exit ClientController:: method=saveClient");
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	// SEARCH CLIENT DETAILS
	@GetMapping("/profile/{clientId}")
	@ApiOperation(value = "Search client details")
	public ResponseEntity<?> findClientById(@PathVariable("clientId") String clientId) {
		logger.info("Enter ClientController:: method=findClient");
		Client clientObj = clientService.findClient(clientId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(clientObj);
		logger.info("Exit ClientController:: method=findClient");
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	// VIEW COMPLAINTS BASED ON CLIENTID
	@GetMapping("/viewcompalint/{clientId}")
	@ApiOperation(value = "View complaints based on client id")
	public ResponseEntity<?> sortedClientComplaint(@PathVariable("clientId") String clientId) {
		logger.info("Enter ClientController:: method=getClientAllComplaintSorted");
		List<Complaint> clientObj = clientService.getClientAllComplaintSorted(clientId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(clientObj);
		logger.info("Exit ClientController:: method=getClientAllComplaintSorted");
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	// CHANGE COMPLAINT STATUS
	@PostMapping("/viewcompalint/{clientId}/{complaintId}")
	@ApiOperation(value = "change Complaint Status")
	public ResponseEntity<?> changeComplaintStatus(@PathVariable("clientId") String clientId,
			@PathVariable("complaintId") Integer complaintId) {
		logger.info("Enter ClientController:: method=changeComplaintStatus");
		String clientObj = clientService.changeComplaintStatus(clientId, complaintId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(clientObj);
		logger.info("Exit ClientController:: method=changeComplaintStatus");
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	@GetMapping("/logout/{clientId}")
	public ResponseEntity<?> logout(@PathVariable("clientId") String clientId) {
		logger.info("Enter ClientController:: method=logout");
		loginService.logout(clientId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(LOGOUT_SUCCESS);
		logger.info("Exit ClientController:: method=logout");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}
}
