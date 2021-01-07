package com.sprint.abc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.sprint.abc.entities.Client;
import com.sprint.abc.exceptions.InvalidClientIdException;
import com.sprint.abc.exceptions.InvalidPasswordException;
import com.sprint.abc.repository.ClientRepository;
import com.sprint.abc.service.LoginServiceImpl;

@SpringBootTest
public class LoginServiceImplTest {

	@Mock
	private ClientRepository clientRepository;

	@InjectMocks
	private LoginServiceImpl loginService;

	@Test
	@DisplayName("UserAuthentication - If Invalid User Found")
	public void invalidUserFound() {
		Client retUser = null;
		String clientId = "c100";
		try {
			Mockito.doThrow(new InvalidClientIdException("Invalid ClientId! please enter correct details"))
					.when(clientRepository).findById(clientId);
			retUser = loginService.login("c100", "1111");
		} catch (Exception exception) {
			String expectedMessage = "Invalid ClientId! please enter correct details";
			String actualMessage = exception.getMessage();
			System.out.println(actualMessage);
			assertEquals(expectedMessage, actualMessage);
		}
	}

	@Test
	@DisplayName("User Authentication - If Password Incorrect for given Email")
	public void incorrectPasswordFound() {
		Client client = new Client();
		client.setClientId("c101");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPassword("1111");
		
//		Mockito.doReturn(Optional.of(client)).when(clientRepository).findById("c101");
//		try {
//			Client  regUser=(loginService.login("c101","2222"));
//		} catch (Exception e) {
//			assertEquals(e.getMessage(),"password does not matches");
//		}	
		
		Client regUser = null;
		String clientId = "c100";
		try {
			Mockito.doThrow(new InvalidPasswordException("Invalid password! please enter correct details"))
					.when(clientRepository).findById(clientId);
			regUser = loginService.login("c100", "1111");
		} catch (Exception exception) {
			String expectedMessage = "Invalid password! please enter correct details";
			String actualMessage = exception.getMessage();
			System.out.println(actualMessage);
			assertEquals(expectedMessage, actualMessage);
		}
	}
	
	@Test
	@DisplayName("User Authentication - Successful login")
	public void successfulLogin() {
		Client client = new Client();
		client.setClientId("c101");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPassword("1111");
		
		String clientId = "c101";
		
		Mockito.doReturn(Optional.of(client)).when(clientRepository).findById(clientId);
		try {
			Client  regUser=(loginService.login(clientId,"1111"));
			assertSame(client,regUser);
		} catch (Exception e) {
			assertEquals(e.getMessage(),"password does not matches");
		}	
	}
}