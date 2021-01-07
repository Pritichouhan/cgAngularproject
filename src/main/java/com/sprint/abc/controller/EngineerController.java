package com.sprint.abc.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.abc.entities.Complaint;
import com.sprint.abc.entities.Engineer;
import com.sprint.abc.payload.BaseResponse;
import com.sprint.abc.service.IEngineerServiceImpl;
import com.sprint.abc.service.ProductServiceImpl;

@RestController
@RequestMapping("/engineer")
public class EngineerController {
	
	private static Logger logger = LoggerFactory.getLogger(EngineerController.class);
	

	
	

	//getResolvedComplaints
	

	//getAllComplaints
	
	//sortComplaintsByDate
	

	//changeComplaintStatus
	
	@Autowired
	private IEngineerServiceImpl engineerservice;
	
	//GET ALL OPEN COMPLAINTS
	@GetMapping("/opencomplaints/{id}")
	public ResponseEntity<?> getAllOpenComplaints(@PathVariable("id") int eid){
		logger.info("Enter EngineerController:: method=getAllOpenComplaints");
		List<Complaint> clist = engineerservice.getAllOpenComplaints(eid);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(clist);
		logger.info("Exit EngineerController:: method=getAllOpenComplaints");
		return new ResponseEntity<>(baseResponse,HttpStatus.OK);
		
	}
	
	//CHANGE STATUS OF COMPLAINT
	@PutMapping("/changestatus/{eid}/{id}/{status}")
	public ResponseEntity<?> changeComplaintStatus(@PathVariable("eid") int eid,@PathVariable("id") int ComplaintId, @PathVariable("status") String status){
		logger.info("Enter EngineerController:: method=changeComplaintStatus");
		engineerservice.changeComplaintStatus(eid,ComplaintId, status);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse("Updated");
		logger.info("Exit EngineerController:: method=changeComplaintStatus");
		return new ResponseEntity<>(baseResponse,HttpStatus.OK);
	}
	
	//GET ALL RESOLVED COMPLAINTS
	@GetMapping("/resolvedcomplaints/{id}")
	public ResponseEntity<?> getResolvedComplaints(@PathVariable("id") int eid){
		logger.info("Enter EngineerController:: method=getResolvedComplaints");
		List<Complaint> clist = engineerservice.getResolvedComplaints(eid);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(clist);
		logger.info("Exit EngineerController:: method=getResolvedComplaints");
		return new ResponseEntity<>(baseResponse,HttpStatus.OK);
		
	}
	
	//GET ALL COMPLAINTS
	@GetMapping("/allcomplaints/{id}")
	public ResponseEntity<?> getAllComplaints(@PathVariable("id") int eid){
		logger.info("Enter EngineerController:: method=getAllComplaints");
		List<Complaint> clist = engineerservice.getAllComplaints(eid);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(clist);
		logger.info("Exit EngineerController:: method=getAllComplaints");
		return new ResponseEntity<>(baseResponse,HttpStatus.OK);
		
	}
	
	//SORT COMPLAINTS 
	@GetMapping("/sortcomplaints/{id}")
	public ResponseEntity<?> sortComplaints(@PathVariable("id") int eid){
		logger.info("Enter EngineerController:: method=sortComplaints");
		List<Complaint> clist = engineerservice.sortComplaintsByDate(eid);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(clist);
		logger.info("Exit EngineerController:: method=sortComplaints");
		return new ResponseEntity<>(baseResponse,HttpStatus.OK);
		
	}
	
	
	
	
}
