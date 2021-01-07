package com.sprint.abc.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.abc.entities.Complaint;
import com.sprint.abc.entities.Engineer;
import com.sprint.abc.entities.Product;
import com.sprint.abc.payload.BaseResponse;
import com.sprint.abc.service.ComplaintService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/complaint")
@Api(value = "Complaint Module", description = "Operations on Complaint Module")
public class ComplaintController {

	private static Logger logger = LoggerFactory.getLogger(ComplaintController.class);

	@Autowired
	private ComplaintService complaintService;

	// BOOK A COMPLAINT
	@PostMapping("/book/{clinetId}/{productModelNumber}")
	@ApiOperation(value = "Book a complaint")
	public ResponseEntity<?> bookComplaint(@PathVariable("clinetId") String clinetId,
			@Valid @RequestBody Complaint complaint, @PathVariable("productModelNumber") String productModelNumber) {
		logger.info("Enter ComplaintController:: method=bookComplaint");

		complaintService.bookComplaint(clinetId, complaint, productModelNumber);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse("Complaint is Booked");
		logger.info("Exit ComplaintController:: method=bookComplaint");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	// GET CLIENT'S ALL COMPLAINTS
	@GetMapping("/all/{clientId}")
	@ApiOperation(value = "Get client all complaints ")
	public ResponseEntity<?> getClientAllComplaints(@PathVariable("clientId") String clientId) {
		logger.info("Enter ComplaintController:: method=getClientAllComplaints");

		List<Complaint> list = complaintService.getClientAllComplaints(clientId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(list);
		logger.info("Exit ComplaintController:: method=getClientAllComplaints");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);

	}

	// GET CLIENT'S ALL OPEN COMPLAINTS
	@GetMapping("/allOpen/{clientId}")
	@ApiOperation(value = "Get client all Open complaints ")
	public ResponseEntity<?> getClientAllOpenComplaints(@PathVariable("clientId") String clientId) {
		logger.info("Enter ComplaintController:: method=getClientAllOpenComplaints");
		List<Complaint> list = complaintService.getClientAllOpenComplaints(clientId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(list);
		logger.info("Exit ComplaintController:: method=getClientAllOpenComplaints");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	// GET PRODUCT DETAILS FROM COMPLAINT
	@GetMapping("/product/{complaintId}")
	@ApiOperation(value = "Get Product details for a Complaint ")
	public ResponseEntity<?> getProductByComplaint(@PathVariable("complaintId") int complaintId) {
		logger.info("Enter ComplaintController:: method=getProductByComplaint");
		Product product = complaintService.getProductByComplaint(complaintId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(product);
		logger.info("Exit ComplaintController:: method=getProductByComplaint");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
		
	}

	// GET ENGINEER DETAILS FROM COMPLAINT
	@GetMapping("/engineer/{complaintId}")
	@ApiOperation(value = "Get Engineer details for a Complaint ")
	public ResponseEntity<?> getEngineerByComplaint(@PathVariable("complaintId") int complaintId) {
		logger.info("Enter ComplaintController:: method=getEngineerByComplaint");
		Engineer engineer = complaintService.getEngineer(complaintId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(engineer);
		logger.info("Exit ComplaintController:: method=getEngineerByComplaint");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	// DELETE COMPLAINT
	@DeleteMapping("/delete/{complaintId}")
	@ApiOperation(value = "Delete Complaint ")
	public ResponseEntity<?> deleteComplaint(@PathVariable("complaintId") int complaintId) {
		logger.info("Enter ComplaintController:: method=deleteComplaint");
		Complaint complaint = complaintService.deleteComplaint(complaintId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(complaint);
		logger.info("Exit ComplaintController:: method=deleteComplaint");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	@PutMapping("/engineer/{complaintId}/{engineerId}")
	@ApiOperation(value = "Allot Engineer to complaint ")
	public ResponseEntity<?> replaceEngineer(@PathVariable int complaintId, @PathVariable int engineerId) {
		logger.info("Enter ComplaintController:: method=replaceEngineer");

		boolean result = complaintService.replaceEngineer(complaintId, engineerId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse("Engineer Replaced");
		logger.info("Exit ComplaintController:: method=replaceEngineer");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	// VIEW ALL COMPLAINTS
	@GetMapping("/all")
	public ResponseEntity<?> viewAllComplaints() {
		logger.info("Enter ComplaintController:: method=viewAllComplaints");
		List<Complaint> list = complaintService.viewAllComplaints();
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(list);
		logger.info("Exit ComplaintController:: method=viewAllComplaints");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	@PutMapping("/{complaintId}")
	public ResponseEntity<?> changeStatus(@PathVariable int complaintId) {
		logger.info("Enter ComplaintController:: method=changeStatus");

		complaintService.changeComplaintStatus(complaintId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse("Status Changed.");
		logger.info("Exit ComplaintController:: method=changeStatus");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

}
