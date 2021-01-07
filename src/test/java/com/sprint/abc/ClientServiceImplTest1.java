package com.sprint.abc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import org.springframework.boot.test.context.SpringBootTest;

import com.sprint.abc.entities.Client;
import com.sprint.abc.exceptions.InvalidClientIdException;
import com.sprint.abc.exceptions.OperationFailedException;
import com.sprint.abc.exceptions.ResourceNotFoundException;
import com.sprint.abc.repository.ClientRepository;
import com.sprint.abc.service.ClientServiceImpl;

import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest1 {

	@Mock
	private ClientRepository clientRepository;

	@InjectMocks
	private ClientServiceImpl clientService;

	@Test
	void testSaveClient() {
		Client client = new Client();
		client.setClientId("c166");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPassword("1111");
		
		given(clientRepository.save(client)).willReturn(client);
		Client clientObj = clientService.saveClient(client);
		Assertions.assertThat(clientObj).isNotNull();
		verify(clientRepository).save(any(Client.class));

	}
	
	@Test
	@DisplayName("UserRegistration - If User already present")
	public void testSaveClientException1() {
		Client client = new Client();
		client.setClientId("c100");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPassword("1111");
		
		Client regUser = null;
		
		try {
			Mockito.doThrow(new InvalidClientIdException("Client id already available: " + client.getClientId()))
					.when(clientRepository).findById("c100");
			regUser = clientService.saveClient(client);
		} catch (InvalidClientIdException exception) {
			String expectedMessage = "Client id already available: " + client.getClientId();
			String actualMessage = exception.getMessage();
			System.out.println(actualMessage);
			assertEquals(expectedMessage, actualMessage);
		}
	}
	
	@Test
	@DisplayName("UserRegistration - Operation failed")
	public void testSaveClientException2() {
		Client client = new Client();
		client.setClientId("c133");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPassword("1111");
		
		Client regUser = null;
		
		try {
	//		given(clientRepository.findById(client.getClientId())).willReturn(Optional.of(client));
			Mockito.doThrow(new OperationFailedException("Something went wrong: "))
					.when(clientRepository).findById("c100");
			regUser = clientService.saveClient(client);
		} catch (OperationFailedException exception) {
			String expectedMessage = "Something went wrong: ";
			String actualMessage = exception.getMessage();
			System.out.println(actualMessage);
			assertEquals(expectedMessage, actualMessage);
		}
	}

	@Test
	@DisplayName("User Profile - successfully find user by id ")
	void testFindClient() {
		final String clientId = "c101";
		Client client = new Client();
		client.setClientId("c101");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPassword("1111");

		given(clientRepository.findById(clientId)).willReturn(Optional.of(client));
		Client expected = clientService.findClient(clientId);
		Assertions.assertThat(expected).isNotNull();
		assertSame(client,expected);
	}

	@Test
	@DisplayName("User Profile - user doesn't exist ")
	public void testFindClientByIdException1() {
		Client regUser = null;
		final String clientId = "c100";
		try {
			Mockito.doThrow(new InvalidClientIdException("Client not found for this id: "+ clientId)).when(clientRepository).findById("c100");
			regUser = clientService.findClient(clientId);
		} catch (Exception exception) {
			String expectedMessage = "Client not found for this id: " + clientId;
			String actualMessage = exception.getMessage();
			System.out.println(actualMessage);
			assertEquals(expectedMessage, actualMessage);
		}
	}
	
	@Test
	@DisplayName("Change complaint : invalid user id ")
	public void testChangeComplaintStatusException1() {
		String clientId = "c100";
		Integer complaintId = 100;
		try {
			Mockito.doThrow(new InvalidClientIdException("Client not found for this id: "+ clientId)).when(clientRepository).findById("c100");
			String regUser = clientService.changeComplaintStatus(clientId,complaintId);
		} catch (Exception exception) {
			String expectedMessage = "Client not found for this id: " + clientId;
			String actualMessage = exception.getMessage();
			System.out.println(actualMessage);
			assertEquals(expectedMessage, actualMessage);
		}	
	}
	
	@Test
	@DisplayName("Change complaint : invalid complaint id")
	public void testChangeComplaintStatusException2() {
		String clientId = "c100";
		Integer complaintId = 100;
		try {
			Mockito.doThrow(new ResourceNotFoundException("Complaint not found for this id: "+ clientId)).when(clientRepository).findById("c100");
			String regUser = clientService.changeComplaintStatus(clientId,complaintId);
		} catch (Exception exception) {
			String expectedMessage = "Complaint not found for this id: " + clientId;
			String actualMessage = exception.getMessage();
			System.out.println(actualMessage);
			assertEquals(expectedMessage, actualMessage);
		}	
	}
	
	@Test
	@DisplayName("Change complaint : operation failed")
	public void testChangeComplaintStatusException3() {
		String clientId = "c100";
		Integer complaintId = 100;
		try {
			Mockito.doThrow(new OperationFailedException("Something went wrong: "))
					.when(clientRepository).findById("c100");
			String regUser = clientService.changeComplaintStatus(clientId,complaintId);
		} catch (OperationFailedException exception) {
			String expectedMessage = "Something went wrong: ";
			String actualMessage = exception.getMessage();
			assertEquals(expectedMessage, actualMessage);
		}
	}
	
//	@Test
//	@DisplayName("Change complaint : success")
//	void testChangeComplaintStatus() {
//		Client client = new Client();
//		client.setClientId("c100");
//		client.setPassword("1234");
//		client.setAddress("mumbai");
//		client.setPassword("1111");
//		String clientId = "c100";
//		Integer complaintId = 100;
//		String status = "success";
//		
//		given(clientRepository.findById(clientId)).willReturn(Optional.of(client));
//		given(clientRepository.saveAndFlush(client)).willReturn(client);
//		
//		String actualMessage = clientService.changeComplaintStatus(clientId,complaintId);
//		String expectedMessage = "successful";
//		assertEquals(expectedMessage, actualMessage);
//
//	}
}
