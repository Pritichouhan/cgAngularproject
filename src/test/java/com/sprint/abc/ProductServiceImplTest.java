package com.sprint.abc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import com.sprint.abc.entities.Complaint;
import com.sprint.abc.entities.Engineer;
import com.sprint.abc.entities.Product;
import com.sprint.abc.exceptions.InvalidClientIdException;
import com.sprint.abc.exceptions.OperationFailedException;
import com.sprint.abc.exceptions.ProductNotFoundException;
import com.sprint.abc.exceptions.ResourceNotFoundException;
import com.sprint.abc.repository.ClientRepository;
import com.sprint.abc.repository.ProductRepository;
import com.sprint.abc.service.ClientServiceImpl;
import com.sprint.abc.service.ProductServiceImpl;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

@SpringBootTest

public class ProductServiceImplTest {
	
	@Mock
	private ClientRepository clientRepository;

	@Mock
	private ProductRepository productRepository;

	@InjectMocks 
	private ProductServiceImpl productService;
	

	@DisplayName("Sample test")
    @Test
    void sampleTest() {
        assertTrue(true);
    }
	
	@DisplayName("Test add product without exception")
	@Test
	void testAddProductwithoutException() {
		
		final String clientId = "c1000";
		final String productModelNumber = "m1000"; 
		Client client = new Client();
		client.setClientId(clientId);
		Product product = new Product();
		product.setModelNumber(productModelNumber);
		product.setDateofPurchase(LocalDate.now());
		product.setProductCategoryName("mobile");
		product.setWarrentyYears(5);
		product.setWarrantyDate(LocalDate.parse("2020-12-29"));
		product.setClientProduct(client);
		
		given(productRepository.findById(productModelNumber)).willReturn(Optional.of(product));
		given(clientRepository.findById(clientId)).willReturn(Optional.of(client));
		
		
		productService.addProduct(product, clientId);
		verify(productRepository).save(any(Product.class));

	}
	@DisplayName("Test add product when client id is invalid")
	@Test
	void testAddProductwithException1() {
		
		final String clientId="abc";
		final String modelNumber="m102";
		
		Client client= new Client();
		client.setClientId("c101");
		
		Product product = new Product();
		product.setModelNumber(modelNumber);
	    product.setProductName("Samsung");
	    product.setProductCategoryName("TV");
	    product.setDateofPurchase(LocalDate.parse("2020-12-23"));
	    product.setWarrentyYears(3);
	    product.setWarrantyDate(LocalDate.now().plusYears(product.getWarrentyYears()));
	    product.setClientProduct(client);
	    
		Exception exception = assertThrows(InvalidClientIdException.class, () -> {
	    	productService.addProduct(product, clientId);
	    });		
		
	    String expectedMessage = " Invalid Client Id " + clientId;
	    String actualMessage = exception.getMessage();
	    assertEquals(actualMessage,expectedMessage);
	}
	
	@DisplayName("Test add product when client id is empty")
	@Test
	void testAddProductwithException2() {
		
		final String clientId="";
		final String modelNumber="m102";
		
		Client client= new Client();
		client.setClientId("c101");
		
		Product product = new Product();
		product.setModelNumber(modelNumber);
	    product.setProductName("Samsung");
	    product.setProductCategoryName("TV");
	    product.setDateofPurchase(LocalDate.parse("2020-12-23"));
	    product.setWarrentyYears(3);
	    product.setWarrantyDate(LocalDate.now().plusYears(product.getWarrentyYears()));
	    product.setClientProduct(client);
	    
		Exception exception = assertThrows(InvalidClientIdException.class, () -> {
	    	productService.addProduct(product, clientId);
	    });		
		
	    String expectedMessage = " Invalid Client Id " + clientId;
	    String actualMessage = exception.getMessage();
	    assertEquals(actualMessage,expectedMessage);
	}
	
	
	@DisplayName("Test find product by Model Number")
	@Test
	void testFindProductByModelNumber() {
		final String modelNumber= "m100";
		
		Product product = new Product();
		
		Client client= new Client();
		client.setClientId("c101");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPassword("1111");
		client.setPhoneNumber("12345678");
		
		product.setModelNumber(modelNumber);
		product.setClientProduct(client);
		product.setDateofPurchase(LocalDate.now());
		product.setProductCategoryName("mobile");
		product.setWarrentyYears(5);
		product.setWarrantyDate(LocalDate.of(2023, 11, 10));
		product.setProductName("samsung");

        given(productRepository.findById("modelNumber")).willReturn(Optional.of(product));
        final Product expected  =productService.fetchProductByModelNumber("modelNumber");
        Assertions.assertThat(expected).isNotNull();	 
       
	}
	
	@DisplayName("Test find product by Model Number when product is not found")
	@Test
	public void testFindProductBymodelNumberWhenExceptionThrown() {
		final String modelNumber= "m100";
		
		Product product = new Product();
		
		Client client= new Client();
		client.setClientId("c101");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPassword("1111");
		client.setPhoneNumber("12345678");
		
		product.setModelNumber(modelNumber);
		product.setClientProduct(client);
		product.setDateofPurchase(LocalDate.now());
		product.setProductCategoryName("mobile");
		product.setWarrentyYears(5);
		product.setWarrantyDate(LocalDate.of(2023, 11, 10));
		product.setProductName("samsung");
		
		Exception exception = assertThrows(ProductNotFoundException.class, () -> {
	    	productService.fetchProductByModelNumber(modelNumber);
	    });		
	    String expectedMessage = "Product not found for this id: "+modelNumber;
	    String actualMessage = exception.getMessage();
	    assertEquals(actualMessage,expectedMessage);
	}
	
	@DisplayName("Test find product by Product Category Name")
	@Test
	public void testFindProductByCategoryName() {
		final String productCategoryName= "TV";
		Product product = new Product();
		
		Client client= new Client();
		client.setClientId("c101");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPassword("1111");
		client.setPhoneNumber("12345678");
		
		product.setModelNumber("m101");
		product.setClientProduct(client);
		product.setDateofPurchase(LocalDate.now());
		product.setProductCategoryName(productCategoryName);
		product.setWarrentyYears(5);
		product.setWarrantyDate(LocalDate.of(2023, 11, 10));
		product.setProductName("samsung");
		product.setClientProduct(client);
		
		List<Product> expectedProducts = Arrays.asList(product);
		
		given(productRepository.findbyCategoryName(productCategoryName)).willReturn(expectedProducts);
        final List<Product> expected  =productService.fetchbyCategoryName(productCategoryName);
        Assertions.assertThat(expected).isNotNull();	
		
		
	}
	
	@DisplayName("Test Get all products")
	@Test
	public void testGetAllProducts() {

		
		Client client= new Client();
		client.setClientId("c101");
		client.setPassword("1234");
		client.setAddress("mumbai");
		client.setPassword("1111");
		client.setPhoneNumber("12345678");
		
		Product product = new Product();
		product.setModelNumber("m102");
		product.setClientProduct(client);
		product.setDateofPurchase(LocalDate.now());
		product.setProductCategoryName("mobile");
		product.setWarrentyYears(5);
		product.setWarrantyDate(LocalDate.of(2023, 11, 10));
		product.setProductName("samsung");

		List<Product> expectedProducts = Arrays.asList(product);

        //Mockito.doReturn(expectedProducts).when(productRepository).findAll();
        given(productRepository.findAll()).willReturn(expectedProducts);
        
        // when
        List<Product> actualProducts = productService.fetchAllProducts();
        // then
        Assertions.assertThat(actualProducts).isEqualTo(expectedProducts);
        //assertEquals(actualProducts,expectedProducts);
    }
	    
	@DisplayName("Test delete product by model number")
	@Test
	public void shouldBeDeleted() {
		final String modelNumber="m102";
		Product product =   new Product();
		product.setModelNumber(modelNumber);
		given(productRepository.findById(modelNumber)).willReturn(Optional.of(product));
		
		productService.removeProduct(modelNumber);
		productService.removeProduct(modelNumber);
		
		verify(productRepository,times(2)).delete(product);
	}	
}
	




		
	
