package com.sprint.abc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.sprint.abc.entities.Complaint;
import com.sprint.abc.entities.Engineer;
import com.sprint.abc.entities.Product;
import com.sprint.abc.exceptions.InValidDomainException;
import com.sprint.abc.exceptions.InvalidEngineerIdException;
import com.sprint.abc.exceptions.ResourceNotFoundException;
import com.sprint.abc.repository.AdminRepository;
import com.sprint.abc.repository.ComplaintRepository;
import com.sprint.abc.repository.EngineerRepository;
import com.sprint.abc.service.AdminServiceImpl;

@SpringBootTest
public class AdminServiceImplTest {
	
	@Mock
	private AdminRepository adminRepo;
	@Mock
	private EngineerRepository engineerRepo;
	@Mock
	private ComplaintRepository complaintRepo;
	
	@InjectMocks
	private AdminServiceImpl adminService;
	
	
	@Test
	public void testAddEngineer() {
		Engineer e=new Engineer();
		e.setEngineerId(101);
		e.setEngineerName("Rutvi");
		e.setDomain("TV");
		e.setPassword("e101");
		given(engineerRepo.save(e)).willReturn(e);
		adminService.saveEngineer(e);
		verify(engineerRepo, times(1)).save(e);
	}
	
	@Test
	public void testFindEnginerByIdWhenExceptionThrown() {
		Engineer e=new Engineer();
		e.setEngineerId(101);
		e.setEngineerName("Rutvi");
		e.setDomain("TV");
		e.setPassword("e101");
				
		final int employeeId = 1000;
		Exception exception = assertThrows(InvalidEngineerIdException.class, () -> {
	    	adminService.getEngineer(employeeId);
	    });		
	    String expectedMessage = "Id not present:"+employeeId;
	    String actualMessage = exception.getMessage();
	    assertNotEquals(actualMessage,expectedMessage);
	}

	@Test
	public void TestRemoveById() {
		Engineer e=new Engineer();
		final int employeeId = 1002;
		e.setEngineerId(employeeId);
		
		when(engineerRepo.findById(employeeId)).thenReturn(Optional.of(e));
		adminService.removeEngineer(employeeId);
		verify(engineerRepo,times(1)).deleteById(employeeId);
		
	}
	
	@Test
	public void ReplaceEngineerThrowsException() {
		Engineer e=new Engineer();
		final int employeeId = 1002;
		final int complaintId = 2001;
		final String domain="TV";
		final String productCategoryName="AC";
		e.setEngineerId(employeeId);
		e.setDomain(domain);
		Product p=new Product();
		p.setProductCategoryName(productCategoryName);
	    Complaint c=new Complaint();
	    c.setComplaintId(complaintId);
	    c.setEngineer(e);
	    c.setProduct(p);
	    given(engineerRepo.findById(employeeId)).willReturn(Optional.of(e)); 
	    given(complaintRepo.findById(complaintId)).willReturn(Optional.of(c));
	    Exception ex=assertThrows(InValidDomainException.class,() ->{
			
	    	 adminService.replaceEngineerFromComplaint(complaintId, employeeId);
			});
	String actual="Domain not found";
	String expected=ex.getMessage();
	assertNotEquals(expected,actual);
	   
	    
	 //   verify(complaintRepo,times(1)).save(c);  
       	    
	}
	
	@Test
	public void shouldReplaceEngineer() {
		Engineer e=new Engineer();
		final int employeeId = 1002;
		final int complaintId = 2001;
		final String domain="TV";
		final String productCategoryName="TV";
		e.setEngineerId(employeeId);
		e.setDomain(domain);
		Product p=new Product();
		p.setProductCategoryName(productCategoryName);
	    Complaint c=new Complaint();
	    c.setComplaintId(complaintId);
	    c.setEngineer(e);
	    c.setProduct(p);
	    given(engineerRepo.findById(employeeId)).willReturn(Optional.of(e)); 
	    given(complaintRepo.findById(complaintId)).willReturn(Optional.of(c));
	     adminService.replaceEngineerFromComplaint(complaintId, employeeId);
	     verify(complaintRepo,times(1)).save(c); 
	}

	
	@Test
	public void getComplaintsByProduct()
	{
		Product p=new Product();
		p.setModelNumber("p101");
		p.setProductCategoryName("AC");
		p.setProductName("azure");
		p.setWarrantyDate(LocalDate.of(2020, 10, 10));
		p.setWarrentyYears(1);
		
		Complaint c=new Complaint();
		c.setComplaintId(1001);
		c.setComplaintName("broken");
		c.setStatus("open");
		c.setProduct(p);
		List<Complaint> list=new ArrayList<>();
		given(adminService.getComplaintsByProducts("AC")).willReturn(list);
		list.add(c);
		List<Complaint> clist=adminService.getComplaintsByProducts("AC");
		assertEquals(1,clist.size());
				
	}
	
	@Test
	public void getComplaintsByStatus()
	{
		
		Complaint c=new Complaint();
		c.setComplaintId(1001);
		c.setComplaintName("broken");
		c.setStatus("open");
		List<Complaint> list=new ArrayList<>();
		given(adminService.getComplaints("open")).willReturn(list);
		list.add(c);
		List<Complaint> clist=adminService.getComplaints("open");
		assertEquals(1,clist.size());
		assertNotEquals(2,clist.size());
				
	}
	
	@Test
	public void changeDomain()
	{
		final int id=100;
		final String domain="Laptop";
		final String domain1="AC";
		Engineer e=new Engineer();
		e.setEngineerId(id);
		e.setEngineerName("Rutvi");
		e.setDomain(domain);
		e.setPassword("e101");
		given(engineerRepo.findById(id)).willReturn(Optional.of(e));
		Exception ex=assertThrows(InValidDomainException.class,() ->{
		
				adminService.changeDomain(id, domain1);
				});
		String actual="Domain not found";
		String expected=ex.getMessage();
		assertNotEquals(expected,actual);
		//verify(engineerRepo,times(1)).save(e);
	}
	
	@Test
	public void shouldAllocateEngineerThrowsException() {
		Engineer e=new Engineer();
		final int employeeId = 1002;
		final int complaintId = 2001;
		final String domain="TV";
		final String productCategoryName="AC";
		e.setEngineerId(employeeId);
		e.setDomain(domain);
		Product p=new Product();
		p.setProductCategoryName(productCategoryName);
	    Complaint c=new Complaint();
	    c.setComplaintId(complaintId);
	   // c.setEngineer(e);
	    c.setProduct(p);
	    given(engineerRepo.findById(employeeId)).willReturn(Optional.of(e)); 
	    given(complaintRepo.findById(complaintId)).willReturn(Optional.of(c));
	    Exception ex=assertThrows(InValidDomainException.class,() ->{
			
	    	  adminService.allocateEngineer(complaintId, employeeId);
			});
	String actual="Domain not found";
	String expected=ex.getMessage();
	assertNotEquals(expected,actual);
	  
	    
	   // verify(complaintRepo,times(1)).save(c);  
       	    
	}
	
	@Test
	public void shouldAllocateEngineer() {
		Engineer e=new Engineer();
		final int employeeId = 1002;
		final int complaintId = 2001;
		final String domain="TV";
		final String productCategoryName="TV";
		e.setEngineerId(employeeId);
		e.setDomain(domain);
		Product p=new Product();
		p.setProductCategoryName(productCategoryName);
	    Complaint c=new Complaint();
	    c.setComplaintId(complaintId);
	   // c.setEngineer(e);
	    c.setProduct(p);
	    given(engineerRepo.findById(employeeId)).willReturn(Optional.of(e)); 
	    given(complaintRepo.findById(complaintId)).willReturn(Optional.of(c));
	     adminService.allocateEngineer(complaintId, employeeId);
	     verify(complaintRepo,times(1)).save(c); 
	}

}
