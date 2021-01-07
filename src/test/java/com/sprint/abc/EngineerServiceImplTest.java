package com.sprint.abc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;


import com.sprint.abc.entities.Client;
import com.sprint.abc.entities.Complaint;
import com.sprint.abc.entities.Engineer;
import com.sprint.abc.entities.Product;
import com.sprint.abc.exceptions.InvalidEngineerIdException;
import com.sprint.abc.repository.ComplaintRepository;
import com.sprint.abc.repository.EngineerRepository;
import com.sprint.abc.service.IEngineerServiceImpl;

import static org.mockito.BDDMockito.given;
@SpringBootTest
//@ExtendWith(MockitoExtension.class)
public class EngineerServiceImplTest {
	
	@Mock
	private EngineerRepository engineerRepository;
	
	@Mock
	private ComplaintRepository complaintRepository;
	
	@InjectMocks
	private IEngineerServiceImpl engineerService;
	
	@DisplayName("Test Get All Complaints")
	@Test
	void testgetAllComplaints() {
		final int engineerid = 101;
		
		Engineer e = new Engineer();
		e.setEngineerName("Alex");
		e.setEngineerId(101);
		e.setDomain("Mobile");
		e.setPassword("ftfhgy");
		
		
		Client client = new Client();
		client.setClientId("c101");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPhoneNumber("9833021673L");
		
		Product p = new Product();
		p.setClientProduct(client);
		p.setDateofPurchase(LocalDate.now());
		p.setModelNumber("p101");
		p.setProductCategoryName("Mobile");
		p.setProductName("Samsung123");
		p.setWarrantyDate(LocalDate.of(2021, 12, 21));
		p.setWarrentyYears(1);
		
		Complaint c1 = new Complaint();
		c1.setComplaintId(1001);
		c1.setComplaintName("Mobile Complaint");
		c1.setStatus("Open");
		c1.setProduct(p);
//		c1.setModelNumber("p101");
		c1.setClient(client);
		c1.setProduct(p);
		c1.setEngineer(e);
		
		Complaint c2 = new Complaint();
		c2.setComplaintId(1002);
		c2.setComplaintName("Mobile Complaint");
		c2.setStatus("Resloved");
		c2.setProduct(p);
		c2.setClient(client);
		c2.setProduct(p);
		c2.setEngineer(e);
		
		List<Complaint> expectedList = new ArrayList<Complaint>();
		expectedList.add(c1);
		expectedList.add(c2);
		client.setComplaintList(expectedList);
		
		given(engineerRepository.findById(engineerid)).willReturn(Optional.of(e));
		given(engineerRepository.getAllComplaints(e.getEngineerId())).willReturn(expectedList);
		List<Complaint> actualList = engineerService.getAllComplaints(engineerid);
		Assertions.assertThat(actualList).isEqualTo(expectedList);
		
		
	}
	
	@DisplayName("Test Get All Complaints With InvalidEngineerIdException")
	@Test
	void testgetAllComplaintswithException() {
		final int engineerid = 101;
		
		Engineer e = new Engineer();
		e.setEngineerName("Alex");
		e.setEngineerId(101);
		e.setDomain("Mobile");
		e.setPassword("ftfhgy");
		
		
		Client client = new Client();
		client.setClientId("c101");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPhoneNumber("9833021673L");
		
		Product p = new Product();
		p.setClientProduct(client);
		p.setDateofPurchase(LocalDate.now());
		p.setModelNumber("p101");
		p.setProductCategoryName("Mobile");
		p.setProductName("Samsung123");
		p.setWarrantyDate(LocalDate.of(2021, 12, 21));
		p.setWarrentyYears(1);
		
		Complaint c1 = new Complaint();
		c1.setComplaintId(1001);
		c1.setComplaintName("Mobile Complaint");
		c1.setStatus("Open");
//		c1.setModelNumber("p101");
		c1.setClient(client);
		c1.setProduct(p);
		c1.setEngineer(e);
		
		Complaint c2 = new Complaint();
		c2.setComplaintId(1002);
		c2.setComplaintName("Mobile Complaint");
		c2.setStatus("Resloved");
//		c2.setModelNumber("p101");
		c2.setClient(client);
		c2.setProduct(p);
		c2.setEngineer(e);
		
		List<Complaint> expectedList = new ArrayList<Complaint>();
		expectedList.add(c1);
		expectedList.add(c2);
		client.setComplaintList(expectedList);
		
		//given(engineerRepository.findById(engineerid)).willReturn(Optional.of(e));
		Exception exception = assertThrows(InvalidEngineerIdException.class, ()->{
			engineerService.getAllOpenComplaints(engineerid);
		});
		
		String expectedMessage = "Engineer with id "+engineerid+" does not exist.";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage,expectedMessage);
		
		
	}
	
	@DisplayName("Test Get All Open Complaints")
	@Test
	void testgetAllOpenComplaints() {
		final int engineerid = 101;
		
		Engineer e = new Engineer();
		e.setEngineerName("Alex");
		e.setEngineerId(101);
		e.setDomain("Mobile");
		e.setPassword("ftfhgy");
		
		Client client = new Client();
		client.setClientId("c101");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPhoneNumber("9833021673L");
		
		Product p = new Product();
		p.setClientProduct(client);
		p.setDateofPurchase(LocalDate.now());
		p.setModelNumber("p101");
		p.setProductCategoryName("Mobile");
		p.setProductName("Samsung123");
		p.setWarrantyDate(LocalDate.of(2021, 12, 21));
		p.setWarrentyYears(1);
		
		Complaint c1 = new Complaint();
		c1.setComplaintId(1001);
		c1.setComplaintName("Mobile Complaint");
		c1.setStatus("Open");
//		c1.setModelNumber("p101");
		c1.setClient(client);
		c1.setProduct(p);
		c1.setEngineer(e);
		
		Complaint c2 = new Complaint();
		c2.setComplaintId(1002);
		c2.setComplaintName("Mobile Complaint");
		c2.setStatus("Open");
//		c2.setModelNumber("p101");
		c2.setClient(client);
		c2.setProduct(p);
		c2.setEngineer(e);
		
		List<Complaint> expectedList = new ArrayList<Complaint>();
		expectedList.add(c1);
		expectedList.add(c2);
		client.setComplaintList(expectedList);
		
		given(engineerRepository.findById(engineerid)).willReturn(Optional.of(e));
		given(engineerRepository.getAllOpenComplaints(e.getEngineerId())).willReturn(expectedList);
		List<Complaint> actualList = engineerService.getAllOpenComplaints(engineerid);
		Assertions.assertThat(actualList).isEqualTo(expectedList);
		
		
	}
	
	@DisplayName("Test Get All Open Complaints with Exception")
	@Test
	void testgetAllOpenComplaintsExceptionThrown() {
		final int engineerid = 101;
		
		Engineer e = new Engineer();
		e.setEngineerName("Alex");
		e.setEngineerId(101);
		e.setDomain("Mobile");
		e.setPassword("ftfhgy");
		
		Client client = new Client();
		client.setClientId("c101");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPhoneNumber("9833021673L");
		
		Product p = new Product();
		p.setClientProduct(client);
		p.setDateofPurchase(LocalDate.now());
		p.setModelNumber("p101");
		p.setProductCategoryName("Mobile");
		p.setProductName("Samsung123");
		p.setWarrantyDate(LocalDate.of(2021, 12, 21));
		p.setWarrentyYears(1);
		
		Complaint c1 = new Complaint();
		c1.setComplaintId(1001);
		c1.setComplaintName("Mobile Complaint");
		c1.setStatus("Open");
//		c1.setModelNumber("p101");
		c1.setClient(client);
		c1.setProduct(p);
		c1.setEngineer(e);
		
		Complaint c2 = new Complaint();
		c2.setComplaintId(1002);
		c2.setComplaintName("Mobile Complaint");
		c2.setStatus("Open");
//		c2.setModelNumber("p101");
		c2.setClient(client);
		c2.setProduct(p);
		c2.setEngineer(e);
		
		List<Complaint> expectedList = new ArrayList<Complaint>();
		expectedList.add(c1);
		expectedList.add(c2);
		client.setComplaintList(expectedList);
		
		//given(engineerRepository.findById(engineerid)).willReturn(Optional.of(e));	
		Exception exception = assertThrows(InvalidEngineerIdException.class, ()->{
			engineerService.getAllOpenComplaints(engineerid);
		});
		
		String expectedMessage = "Engineer with id "+engineerid+" does not exist.";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage,expectedMessage);
		
		
	}
	
	@DisplayName("Test Resolved Complaints")
	@Test
	void testgetResolvedComplaints() {
		final int engineerid = 101;
		
		Engineer e = new Engineer();
		e.setEngineerName("Alex");
		e.setEngineerId(101);
		e.setDomain("Mobile");
		e.setPassword("ftfhgy");
		
		Client client = new Client();
		client.setClientId("c101");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPhoneNumber("9833021673L");
		
		Product p = new Product();
		p.setClientProduct(client);
		p.setDateofPurchase(LocalDate.now());
		p.setModelNumber("p101");
		p.setProductCategoryName("Mobile");
		p.setProductName("Samsung123");
		p.setWarrantyDate(LocalDate.of(2021, 12, 21));
		p.setWarrentyYears(1);
		
		Complaint c1 = new Complaint();
		c1.setComplaintId(1001);
		c1.setComplaintName("Mobile Complaint");
		c1.setStatus("Open");
//		c1.setModelNumber("p101");
		c1.setClient(client);
		c1.setProduct(p);
		c1.setEngineer(e);
		
		Complaint c2 = new Complaint();
		c2.setComplaintId(1002);
		c2.setComplaintName("Mobile Complaint");
		c2.setStatus("Resolved");
//		c2.setModelNumber("p101");
		c2.setClient(client);
		c2.setProduct(p);
		c2.setEngineer(e);
		
		List<Complaint> expectedList = new ArrayList<Complaint>();
		expectedList.add(c2);
		client.setComplaintList(expectedList);
		
		given(engineerRepository.findById(engineerid)).willReturn(Optional.of(e));
		given(engineerRepository.getResolvedComplaints(e.getEngineerId())).willReturn(expectedList);
		List<Complaint> actualList = engineerService.getResolvedComplaints(engineerid);
		Assertions.assertThat(actualList).isEqualTo(expectedList);
		
		
	}
	
	@DisplayName("Test Resolved Complaints with InvalidEngineerIdException")
	@Test
	void testgetResolvedComplaintswithException() {
		final int engineerid = 101;
		
		Engineer e = new Engineer();
		e.setEngineerName("Alex");
		e.setEngineerId(101);
		e.setDomain("Mobile");
		e.setPassword("ftfhgy");
		
		Client client = new Client();
		client.setClientId("c101");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPhoneNumber("9833021673L");
		
		Product p = new Product();
		p.setClientProduct(client);
		p.setDateofPurchase(LocalDate.now());
		p.setModelNumber("p101");
		p.setProductCategoryName("Mobile");
		p.setProductName("Samsung123");
		p.setWarrantyDate(LocalDate.of(2021, 12, 21));
		p.setWarrentyYears(1);
		
		Complaint c1 = new Complaint();
		c1.setComplaintId(1001);
		c1.setComplaintName("Mobile Complaint");
		c1.setStatus("Open");
//		c1.setModelNumber("p101");
		c1.setClient(client);
		c1.setProduct(p);
		c1.setEngineer(e);
		
		Complaint c2 = new Complaint();
		c2.setComplaintId(1002);
		c2.setComplaintName("Mobile Complaint");
		c2.setStatus("Resolved");
//		c2.setModelNumber("p101");
		c2.setClient(client);
		c2.setProduct(p);
		c2.setEngineer(e);
		
		List<Complaint> expectedList = new ArrayList<Complaint>();
		expectedList.add(c2);
		client.setComplaintList(expectedList);
		
		//given(engineerRepository.findById(engineerid)).willReturn(Optional.of(e));
		Exception exception = assertThrows(InvalidEngineerIdException.class, ()->{
			engineerService.getAllOpenComplaints(engineerid);
		});
		
		String expectedMessage = "Engineer with id "+engineerid+" does not exist.";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage,expectedMessage);
		
		
	}
	
	
	@DisplayName("Test sort Complaints by date")
	@Test
	void testsortComplaintsbydate() {
		final int engineerid = 101;
		
		Engineer e = new Engineer();
		e.setEngineerName("Alex");
		e.setEngineerId(101);
		e.setDomain("Mobile");
		e.setPassword("ftfhgy");
		
		Client client = new Client();
		client.setClientId("c101");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPhoneNumber("9833021673L");
		
		Product p = new Product();
		p.setClientProduct(client);
		p.setDateofPurchase(LocalDate.now());
		p.setModelNumber("p101");
		p.setProductCategoryName("Mobile");
		p.setProductName("Samsung123");
		p.setWarrantyDate(LocalDate.of(2021, 12, 21));
		p.setWarrentyYears(1);
		
		Complaint c1 = new Complaint();
		c1.setComplaintId(1001);
		c1.setComplaintName("Mobile Complaint");
		c1.setStatus("Open");
		c1.setComplaintDate(LocalDate.parse("2020-10-12"));
//		c1.setModelNumber("p101");
		c1.setClient(client);
		c1.setProduct(p);
		c1.setEngineer(e);
		
		Complaint c2 = new Complaint();
		c2.setComplaintId(1002);
		c2.setComplaintName("Mobile Complaint");
		c2.setStatus("Open");
		c2.setComplaintDate(LocalDate.parse("2020-08-10"));
//		c2.setModelNumber("p101");
		c2.setClient(client);
		c2.setProduct(p);
		c2.setEngineer(e);
		
		List<Complaint> expectedList = new ArrayList<Complaint>();
		expectedList.add(c2);
		expectedList.add(c1);
		client.setComplaintList(expectedList);
		
		given(engineerRepository.findById(engineerid)).willReturn(Optional.of(e));
		given(engineerRepository.sortComplaintsByDate(e.getEngineerId())).willReturn(expectedList);
		List<Complaint> actualList = engineerService.sortComplaintsByDate(engineerid);
		Assertions.assertThat(actualList).isEqualTo(expectedList);
	}
	
	@DisplayName("Test sort Complaints by date with InvalidEngineerIdException")
	@Test
	void testsortComplaintsbydatewithException() {
		final int engineerid = 101;
		
		Engineer e = new Engineer();
		e.setEngineerName("Alex");
		e.setEngineerId(101);
		e.setDomain("Mobile");
		e.setPassword("ftfhgy");
		
		Client client = new Client();
		client.setClientId("c101");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPhoneNumber("9833021673L");
		
		Product p = new Product();
		p.setClientProduct(client);
		p.setDateofPurchase(LocalDate.now());
		p.setModelNumber("p101");
		p.setProductCategoryName("Mobile");
		p.setProductName("Samsung123");
		p.setWarrantyDate(LocalDate.of(2021, 12, 21));
		p.setWarrentyYears(1);
		
		Complaint c1 = new Complaint();
		c1.setComplaintId(1001);
		c1.setComplaintName("Mobile Complaint");
		c1.setStatus("Open");
		c1.setComplaintDate(LocalDate.parse("2020-10-12"));
//		c1.setModelNumber("p101");
		c1.setClient(client);
		c1.setProduct(p);
		c1.setEngineer(e);
		
		Complaint c2 = new Complaint();
		c2.setComplaintId(1002);
		c2.setComplaintName("Mobile Complaint");
		c2.setStatus("Open");
		c2.setComplaintDate(LocalDate.parse("2020-08-10"));
//		c2.setModelNumber("p101");
		c2.setClient(client);
		c2.setProduct(p);
		c2.setEngineer(e);
		
		List<Complaint> expectedList = new ArrayList<Complaint>();
		expectedList.add(c2);
		expectedList.add(c1);
		client.setComplaintList(expectedList);
		
		//given(engineerRepository.findById(engineerid)).willReturn(Optional.of(e));
		Exception exception = assertThrows(InvalidEngineerIdException.class, ()->{
			engineerService.getAllOpenComplaints(engineerid);
		});
		
		String expectedMessage = "Engineer with id "+engineerid+" does not exist.";
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage,expectedMessage);
	}
	
	
	
}
