package com.sprint.abc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.sprint.abc.entities.Client;
import com.sprint.abc.entities.Complaint;
import com.sprint.abc.entities.Engineer;
import com.sprint.abc.entities.Product;
import com.sprint.abc.exceptions.OutOfWarrantyException;
import com.sprint.abc.exceptions.ResourceNotFoundException;
import com.sprint.abc.repository.ClientRepository;
import com.sprint.abc.repository.ComplaintRepository;
import com.sprint.abc.repository.EngineerRepository;
import com.sprint.abc.repository.ProductRepository;
import com.sprint.abc.service.ClientServiceImpl;
import com.sprint.abc.service.ComplaintServiceImpl;
import com.sprint.abc.service.ProductServiceImpl;

@SpringBootTest
public class ComplaintServiceImplTest {
	
	@Mock
	private ComplaintRepository complaintRepository;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private ClientRepository clientRepository;
	@Mock
	private EngineerRepository engineerRepository;
	
	@InjectMocks
	private ComplaintServiceImpl complaintService;
	@InjectMocks
	private ProductServiceImpl productService;
	@InjectMocks
	private ClientServiceImpl clientService;
	
	@DisplayName("Test Mock complaintService + complaintRepository")
    @Test
    void sampleTest() {
        assertTrue(true);
    }
	
	
	
	@DisplayName("Test Book complaint without exception ")
	@Test
	public void testBookComplaintWithoutException() {
		
		final String clientId = "c1000";
		final String productModelNumber = "m1000"; 
		final int complaintId =32;
		Client client = new Client();
		client.setClientId(clientId);
		Product product = new Product();
		product.setModelNumber(productModelNumber);
		product.setDateofPurchase(LocalDate.now());
		product.setProductCategoryName("mobile");
		product.setWarrentyYears(5);
		product.setWarrantyDate(LocalDate.parse("2020-12-29"));
		product.setClientProduct(client);
		product.setClientProduct(client);
		Complaint complaint =   new Complaint();
		complaint.setComplaintId(complaintId);
		
		given(productRepository.findById(productModelNumber)).willReturn(Optional.of(product));
		given(clientRepository.findById(clientId)).willReturn(Optional.of(client));
		given(complaintRepository.findById(complaintId)).willReturn(Optional.of(complaint));
		
		complaintService.bookComplaint(clientId, complaint, productModelNumber);
		verify(complaintRepository).save(any(Complaint.class));

	}
	
	@DisplayName("Test Book complaint with OutOfWarrantyException")
	@Test
	public void testBookComplaintWhenOutOfWarrantyExceptionIsThrown() {
		
		final String clientId = "c1000";
		final String productModelNumber = "m1000"; 
		final int complaintId =32;
		
		Engineer engineer = new Engineer();
		engineer.setEngineerId(102);
		engineer.setPassword("101");
		engineer.setEngineerName("Preeti");
		engineer.setDomain("TV");
		
		Client client = new Client();
		client.setClientId(clientId);
		Product product = new Product();
		product.setModelNumber(productModelNumber);
		product.setDateofPurchase(LocalDate.parse("2019-12-12"));
		product.setProductCategoryName("mobile");
		product.setWarrentyYears(1);
		product.setWarrantyDate(LocalDate.parse("2020-12-12"));
		product.setClientProduct(client);
		product.setClientProduct(client);
		Complaint complaint =   new Complaint();
		complaint.setComplaintId(complaintId);
		
		given(productRepository.findById(productModelNumber)).willReturn(Optional.of(product));
		given(clientRepository.findById(clientId)).willReturn(Optional.of(client));
		given(complaintRepository.findById(complaintId)).willReturn(Optional.of(complaint));
		
		Exception exception = assertThrows(OutOfWarrantyException.class, () -> {
			complaintService.bookComplaint(clientId, complaint, productModelNumber);
	    });	
		
		String expectedMessage = "Product is out of warrenty"+productModelNumber;
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage,expectedMessage);
	}
	
	@DisplayName("Test Book complaint with InvalidClientIdException")
	@Test
	public void testBookComplaintWhenInvalidClientIdExceptionIsThrown() {
		
		final String clientId = "c1000";
		final String clientId2 ="c1002";
		final String productModelNumber = "m1000"; 
		final int complaintId =32;
		
		Client client = new Client();
		client.setClientId(clientId);
		
		Client client2 = new Client();
		client.setClientId(clientId2);
		
		Product product = new Product();
		product.setModelNumber(productModelNumber);
		product.setDateofPurchase(LocalDate.now());
		product.setProductCategoryName("mobile");
		product.setWarrentyYears(5);
		product.setWarrantyDate(LocalDate.parse("2020-12-29"));
		product.setClientProduct(client);

		Complaint complaint =   new Complaint();
		complaint.setComplaintId(complaintId);
		
		given(productRepository.findById(productModelNumber)).willReturn(Optional.of(product));
		given(clientRepository.findById(clientId2)).willReturn(Optional.of(client2));
		given(complaintRepository.findById(complaintId)).willReturn(Optional.of(complaint));
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			complaintService.bookComplaint(clientId, complaint, productModelNumber);
	    });	
		
		String expectedMessage = "Record not found for this id "+clientId;
		String actualMessage = exception.getMessage();
		assertEquals(actualMessage,expectedMessage);
	}
	
	
	
	@DisplayName("Test Book complaint with RuntimeException")
	@Test
	public void testBookComplaintWithRuntimeException() {
		
		Client client = new Client();
		client.setClientId("c101");
		client.setPassword("c101");
		client.setAddress("Indore");
		client.setPhoneNumber("9876543210L");
		
	
		Product product = new Product();
		product.setModelNumber("m104");
		product.setDateofPurchase(LocalDate.now());
		product.setProductCategoryName("mobile");
		product.setWarrentyYears(5);
		product.setWarrantyDate(LocalDate.parse("2020-12-29"));
		product.setClientProduct(client);

		
		Complaint complaint = new Complaint();
		complaint.setComplaintName("xyz");
		complaint.setClient(client);
		complaint.setProduct(product);

		
		try {
			complaintService.bookComplaint(client.getClientId(), complaint, product.getModelNumber());
			verify(complaintRepository).save(any(Complaint.class));
		} catch (RuntimeException e) {
			e.getMessage();
		}
	
	}
	
	@DisplayName("Test Get all complaints")
	@Test
	public void testGetAllComplaints() {
		final String clientId = "c101";
		final int complaintId = 11;
		
		Client client = new Client();
		client.setClientId("c101");
		client.setPassword("c101");
		client.setAddress("Indore");
		client.setPhoneNumber("9876543210L");
		
		Complaint complaint1 = new Complaint();
		complaint1.setComplaintId(11);
		complaint1.setComplaintName("xyz");
		complaint1.setClient(client);
		complaint1.setStatus("Open");
		
		Complaint complaint2 = new Complaint();
		complaint2.setComplaintId(12);
		complaint2.setComplaintName("xyz");
		complaint2.setClient(client);
		complaint2.setStatus("closed");
		
		List<Complaint> expected = new ArrayList<>();
		expected.add(complaint1);
		client.setComplaintList(expected);

		given(clientRepository.findById(client.getClientId())).willReturn(Optional.of(client));
		given(complaintRepository.findById(complaint1.getComplaintId())).willReturn(Optional.of(complaint1));
		given(complaintRepository.findById(complaint2.getComplaintId())).willReturn(Optional.of(complaint2));
		
		given(complaintRepository.findAll()).willReturn(expected);
		List<Complaint> actual = complaintService.getClientAllOpenComplaints(client.getClientId());
		Assertions.assertThat(actual).isEqualTo(expected);
		
	}

	@DisplayName("Test Get all Open complaints")
	@Test
	public void testGetAllOpenComplaints() {
		final String clientId = "c101";
		final int complaintId = 11;
		
		Client client = new Client();
		client.setClientId("c101");
		client.setPassword("c101");
		client.setAddress("Indore");
		client.setPhoneNumber("9876543210L");
		
		Complaint complaint1 = new Complaint();
		complaint1.setComplaintId(11);
		complaint1.setComplaintName("xyz");
		complaint1.setClient(client);
		complaint1.setStatus("Open");
		
		Complaint complaint2 = new Complaint();
		complaint2.setComplaintId(12);
		complaint2.setComplaintName("xyz");
		complaint2.setClient(client);
		complaint2.setStatus("closed");
		
		List<Complaint> expected = new ArrayList<>();
		expected.add(complaint1);
		client.setComplaintList(expected);

		given(clientRepository.findById(client.getClientId())).willReturn(Optional.of(client));
		given(complaintRepository.findById(complaint1.getComplaintId())).willReturn(Optional.of(complaint1));
		given(complaintRepository.findById(complaint2.getComplaintId())).willReturn(Optional.of(complaint2));
		
		given(complaintRepository.findAll()).willReturn(expected);
		List<Complaint> actual = complaintService.getClientAllOpenComplaints(client.getClientId());
		Assertions.assertThat(actual).isEqualTo(expected);

	}
	
	@DisplayName("Test Delete complaint by id")
	@Test
	public void shouldBeDeleted() {
		final int complaintId =32;
		Complaint complaint =   new Complaint();
		complaint.setComplaintId(complaintId);
		given(complaintRepository.findById(complaintId)).willReturn(Optional.of(complaint));
		
		complaintService.deleteComplaint(complaintId);
		complaintService.deleteComplaint(complaintId);
		
		verify(complaintRepository,times(2)).delete(complaint);
	}
	
	@DisplayName("Test Get Product by Complaint Id")
	@Test
	public void testProductByComplaint() {
		final String clientId = "c1000";
		final String productModelNumber = "m1000"; 
		final int complaintId =32;
		
		Client client = new Client();
		client.setClientId(clientId);
		Product product = new Product();
		product.setModelNumber(productModelNumber);
		product.setDateofPurchase(LocalDate.parse("2019-12-12"));
		product.setProductCategoryName("mobile");
		product.setWarrentyYears(1);
		product.setWarrantyDate(LocalDate.parse("2020-12-12"));
		product.setClientProduct(client);
		product.setClientProduct(client);
		
		
		Complaint complaint =   new Complaint();
		complaint.setComplaintId(complaintId);
		complaint.setProduct(product);
		
		
		given(productRepository.findById(productModelNumber)).willReturn(Optional.of(product));
		given(clientRepository.findById(clientId)).willReturn(Optional.of(client));
		
		given(complaintRepository.findById(complaintId)).willReturn(Optional.of(complaint));
		
		Product expected = complaintService.getProductByComplaint(complaintId);
		assertEquals(expected, product);
	}
	
	@DisplayName("Test Get Engineer by Complaint Id")
	@Test
	public void testEngineerByComplaint() {
		
		final int complaintId =32;
		final int engineerId = 102;

		Engineer engineer = new Engineer();
		engineer.setEngineerId(engineerId);
		engineer.setPassword("101");
		engineer.setEngineerName("Preeti");
		engineer.setDomain("TV");
		Complaint complaint =   new Complaint();
		complaint.setComplaintId(complaintId);
		complaint.setEngineer(engineer);
		
		given(engineerRepository.findById(engineerId)).willReturn(Optional.of(engineer));
		given(complaintRepository.findById(complaintId)).willReturn(Optional.of(complaint));
		
		Engineer expected = complaintService.getEngineer(complaintId);
		assertEquals(expected, engineer);
	}
	
}
