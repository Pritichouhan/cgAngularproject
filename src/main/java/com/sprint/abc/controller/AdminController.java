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

import com.sprint.abc.entities.Admin;
import com.sprint.abc.entities.Complaint;
import com.sprint.abc.entities.Engineer;
import com.sprint.abc.payload.BaseResponse;
import com.sprint.abc.service.AdminService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/admin")
public class AdminController {

	private static Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;

//	@Autowired
//	private IAdminRepository adminRepo;

	// ADD ENGINEER
	@PostMapping("/")
	@ApiOperation(value = "Adding Engineer")
	public ResponseEntity<?> addEngineer(@Valid @RequestBody Engineer e) {
		logger.info("Enter AdminController:: method=addEngineer");
		adminService.saveEngineer(e);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(e);
		logger.info("Exit AdminController:: method=addEngineer");
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);

	}

	// DELETE ENGINEER
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete Engineer by Id")
	public ResponseEntity<?> removeById(@PathVariable("id") int id) {
		logger.info("Enter AdminController:: method=removeById");
		adminService.removeEngineer(id);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse("Engineer Removed");
		logger.info("Exit AdminController:: method=removeById");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	// GET ENGINEER BY ID
	@GetMapping("/get/{id}")
	@ApiOperation(value = "Get Engineer by Id")
	public ResponseEntity<?> getEngineerById(@PathVariable("id") int id) {
		logger.info("Enter AdminController:: method=getEngineerById");
		Engineer engineer = adminService.getEngineer(id);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(engineer);
		logger.info("Exit AdminController:: method=getEngineerById");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	// CHANGE ENGINEER DOMAIN
	@PutMapping("/{id}/{newDomain}")
	@ApiOperation(value = "Changing Engineer Domain")
	public ResponseEntity<?> changeDomain(@PathVariable("id") int id, @PathVariable("newDomain") String domain) {
		logger.info("Enter AdminController:: method=changeDomain");
		Engineer e = adminService.changeDomain(id, domain);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(e);
		logger.info("Exit AdminController:: method=changeDomain");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	// GET LIST OF COMPLAINTS WITH STATUS
	@GetMapping("/list/{status}")
	@ApiOperation(value = "List of complaints with status")
	public ResponseEntity<?> getListByStatus(@PathVariable("status") String status) {
		logger.info("Enter AdminController:: method=getListByStatus");
		List<Complaint> list = adminService.getComplaints(status);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(list);
		logger.info("Exit AdminController:: method=getListByStatus");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	// GET LIST OF COMPLAINTS WITH PRODUCT CATAEGORY NAME
	@GetMapping("/findbyname/{productCategory}")
	@ApiOperation(value = "List of complaints with Product category name")
	public ResponseEntity<?> getListByCategoryName(@PathVariable("productCategory") String productCategoryName) {
		logger.info("Enter AdminController:: method=getListByCategoryName");
		List<Complaint> list = adminService.getComplaintsByProducts(productCategoryName);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(list);
		logger.info("Exit AdminController:: method=getListByCategoryName");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	// ALLOCATE ENGINEER TO COMPLAINT
	@PutMapping("/allocateengineer/{id}/{engId}")
	@ApiOperation(value = "Allocating engineer to complaint")
	public ResponseEntity<?> allocateEngineer(@PathVariable("id") int complaintId, @PathVariable("engId") int engId) {
		logger.info("Enter AdminController:: method=allocateEngineer");
		adminService.allocateEngineer(complaintId, engId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse("engineer allocated");
		logger.info("Exit AdminController:: method=allocateEngineer");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);

	}

	// REPLACE ENGINEER FROM COMPLAINT
	@PutMapping("/replaceengineer/{id}/{empId}")
	@ApiOperation(value = "Replacing engineer in complaint")
	public ResponseEntity<?> replaceEngineer(@PathVariable("id") int complaintId, @PathVariable("empId") int empId) {
		logger.info("Enter AdminController:: method=replaceEngineer");
		adminService.replaceEngineerFromComplaint(complaintId, empId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(1);
		logger.info("Exit AdminController:: method=replaceEngineer");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);

	}

	// GET PROFILE OF ADMIN
	@GetMapping("/profile/{id}")
	@ApiOperation(value = "Profile of Aadmin")
	public ResponseEntity<?> viewProfile(@PathVariable("id") int adminId) {
		logger.info("Enter AdminController:: method=viewProfile");
		Admin admin = adminService.viewProfile(adminId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(admin);
		logger.info("Exit AdminController:: method=viewProfile");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	// ADD ADMIN
	@PostMapping("/addadmin")
	@ApiOperation(value = "Adding Admin")
	public ResponseEntity<?> addAdmin(@RequestBody Admin admin) {
		logger.info("Enter AdminController:: method=addAdmin");
		adminService.addAdmin(admin);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(admin);
		logger.info("Exit AdminController:: method=addAdmin");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

}