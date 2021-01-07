package com.sprint.abc.service;

import java.util.List;
import java.util.Optional;

import com.sprint.abc.entities.Product;
import com.sprint.abc.exceptions.InvalidClientIdException;
import com.sprint.abc.exceptions.ProductNotFoundException;
import com.sprint.abc.exceptions.RecordNotFoundException;

public interface ProductService {
	
	public Product addProduct(Product product, String clientId);
	
	public List<Product> fetchAllProducts();
	
	public Product fetchProductByModelNumber(String modelNumber) throws ProductNotFoundException;
	
	public List<Product> fetchbyCategoryName(String productCategoryName);
	
	Product removeProduct(String modelNumber);

	void updateWarrentyYears(int warrentyyears,String modelNumber);


	
}
