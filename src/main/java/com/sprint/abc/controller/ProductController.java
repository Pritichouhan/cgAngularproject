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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.abc.entities.Complaint;
import com.sprint.abc.entities.Product;
import com.sprint.abc.exceptions.OperationFailedException;
import com.sprint.abc.exceptions.ProductNotFoundException;
import com.sprint.abc.payload.BaseResponse;
import com.sprint.abc.service.ProductService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/product")

public class ProductController {
	
	private static Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	// ADD PRODUCT
	@PostMapping("/addproduct/{id}")
	@ApiOperation(value = "Add a product")
	public ResponseEntity<?> insertProduct(@Valid @RequestBody Product product, @PathVariable("id") String clientId) {
		logger.info("Enter ProductController:: method=insertProduct");
		Product productObj = productService.addProduct(product, clientId);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(productObj);
		logger.info("Exit ProductController:: method=insertProduct");
		return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
	}

	// GET ALL PRODUCT
	@GetMapping("/viewregisteredproduct")
	@ApiOperation(value = "Show All Products")
	public ResponseEntity<?> fetchAllProductDetails() {
		logger.info("Enter ProductController:: method=fetchAllProductDetails");

		List<Product> productList = productService.fetchAllProducts();

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(productList);
		logger.info("Exit ProductController:: method=fetchAllProductDetails");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);

	}

	// GET PRODUCT WITH MODEL NUMBER
	@GetMapping("/show/{id}")
	@ApiOperation(value = "Search a product with modelNumber", response = Product.class)
	public ResponseEntity<?> fetchProductDetails(@PathVariable("id") String modelNumber) {
		logger.info("Enter ProductController:: method=fetchProductDetails");
		Product products = productService.fetchProductByModelNumber(modelNumber);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(products);
		logger.info("Enter ProductController:: method=fetchProductDetails");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	// GET PRODUCT WITH CATEGORYNAME
	@GetMapping("/show")
	@RequestMapping(value = "/product", method = RequestMethod.GET)
	@ApiOperation(value = "Search a product with an categoryname", response = Product.class)
	public ResponseEntity<?> fetchProductsByCategoryName(
			@RequestParam(value = "productCategoryName") String productCategoryName) {
		logger.info("Enter ProductController:: method=fetchProductsByCategoryName");

		List<Product> products = productService.fetchbyCategoryName(productCategoryName);

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatusCode(1);
		baseResponse.setResponse(products);
		logger.info("Enter ProductController:: method=fetchProductsByCategoryName");
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

	

	// DELETE PRODUCT
	@DeleteMapping("/delete/{modelNumber}")
	public ResponseEntity<?> removeProduct(@PathVariable("modelNumber") String modelNumber) {
		logger.info("Enter ProductController:: method=removeProduct");
		Product p = productService.removeProduct(modelNumber);
		logger.info("Enter ProductController:: method=removeProduct");
		return new ResponseEntity<>("product deleted", HttpStatus.OK);
	}
	
	@PutMapping("/update/{modelNumber}")
	public ResponseEntity<?> updatewarrentyyears(@PathVariable("modelNumber") String modelNumber, int warrentyyears) {
		logger.info("Enter ProductController:: method=updatewarrentyyears");
		productService.updateWarrentyYears(warrentyyears,modelNumber);
		logger.info("Exit ProductController:: method=updatewarrentyyears");
		return new ResponseEntity<>("product updated", HttpStatus.OK);
	}
}