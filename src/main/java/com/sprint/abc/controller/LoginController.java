package com.sprint.abc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.abc.entities.Engineer;
import com.sprint.abc.payload.BaseResponse;
import com.sprint.abc.payload.LoginReqPayload;
import com.sprint.abc.service.ClientService;
import com.sprint.abc.service.LoginService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import static com.sprint.abc.util.AppConstants.LOGIN_SUCCESS;
import static com.sprint.abc.util.AppConstants.LOGOUT_SUCCESS;

@RestController
@RequestMapping("/login")
@Api(value="Login and Logout", description="Login and logout controller")
public class LoginController {
	
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private LoginService loginService;
	

	@PostMapping("/client/")
	@ApiOperation(value = "Login Operation")
	public ResponseEntity<?> clientLogin(@RequestBody LoginReqPayload loginReqPayload) {
		logger.info("Enter LoginController:: method=clientLogin");
	    loginService.login(loginReqPayload.getUsername(),loginReqPayload.getPassword());
	    BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(LOGIN_SUCCESS);	
		logger.info("Exit LoginController:: method=clientLogin");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}
	
	@PostMapping("/logout/{clientId}")
    public ResponseEntity<?> logout(@PathVariable("clientId") String clientId) {
		logger.info("Enter LoginController:: method=logout");
		loginService.logout(clientId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(LOGOUT_SUCCESS);	
		logger.info("Exit LoginController:: method=logout");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
	
	@PostMapping("/Engineer"
			+ "/")
	@ApiOperation(value = "Login Operation")
	public ResponseEntity<?> engineerLogin(@RequestBody LoginReqPayload loginReqPayload){
		logger.info("Enter LoginController:: method=engineerLogin");
	   loginService.login2(loginReqPayload.getUsername(), loginReqPayload.getPassword());
	   BaseResponse baseResponse = new BaseResponse();
	   baseResponse.setStatusCode(1);
	   baseResponse.setResponse(LOGIN_SUCCESS);	
	   logger.info("Exit LoginController:: method=engineerLogin");
	   return new ResponseEntity<>("Login Successfull", HttpStatus.OK);
	}
	
	@PostMapping("/logout/{engineerid}")
    public ResponseEntity<?> logout2(@PathVariable("engineerid") int engineerid) {
		logger.info("Enter LoginController:: method=logout2");
		loginService.logout2(engineerid);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(LOGOUT_SUCCESS);
		logger.info("Enter LoginController:: method=logout2");
        return new ResponseEntity<>("Logout Successfull", HttpStatus.OK);
	}

	@PostMapping("/Admin/")
	@ApiOperation(value = "Login Operation")
	public ResponseEntity<?> adminLogin(@RequestBody LoginReqPayload loginReqPayload){
		logger.info("Enter LoginController:: method=adminLogin");
		loginService.login2(loginReqPayload.getUsername(), loginReqPayload.getPassword());
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(LOGIN_SUCCESS);	
	   logger.info("Exit LoginController:: method=adminLogin");
	   return new ResponseEntity<>("Login Successfull", HttpStatus.OK);
	}
	
	@PostMapping("/logout/{adminid}")
    public ResponseEntity<?> logout3(@PathVariable("adminid") int adminid) {
		logger.info("Enter LoginController:: method=logout3");
		loginService.logout3(adminid);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(LOGOUT_SUCCESS);
		logger.info("Enter LoginController:: method=logout3");
        return new ResponseEntity<>("Logout Successfull", HttpStatus.OK);
	}
}


