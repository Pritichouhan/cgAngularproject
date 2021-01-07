package com.sprint.abc.service;

import static com.sprint.abc.util.AppConstants.PRODUCT_NOT_FOUND_CONST;
import static com.sprint.abc.util.AppConstants.RECORD_NOT_FOUND_CONST;
import static com.sprint.abc.util.AppConstants.INVALID_CLIENT_ID_CONST;
import static com.sprint.abc.util.AppConstants.OPERATION_FAILED_CONST;
import static com.sprint.abc.util.AppConstants.PRODUCTCATEGORY_NOT_FOUND_CONST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprint.abc.entities.Client;
import com.sprint.abc.entities.Complaint;
import com.sprint.abc.entities.Engineer;
import com.sprint.abc.entities.Product;
import com.sprint.abc.exceptions.*;
import com.sprint.abc.repository.ClientRepository;
import com.sprint.abc.repository.ProductRepository;
import com.sprint.abc.util.AppConstants;

@Service
public class ProductServiceImpl implements ProductService {

	private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository productrepository;

	@Autowired
	private ClientRepository clientrepository;

	/**
	 * Method to add a product in the database
	 * 
	 * 
	 */

	@Transactional
	@Override
	public Product addProduct(Product product, String clientId) {
		logger.info("Enter ProductServiceImpl:: method=addProduct");
		try {
			Optional<Client> clientAvailable = clientrepository.findById(clientId);
			
			if (!clientAvailable.isPresent())
				throw new InvalidClientIdException(INVALID_CLIENT_ID_CONST + clientId);
			else {
				product.setClientProduct(clientAvailable.get());
				product.setWarrantyDate(product.getDateofPurchase().plusYears(product.getWarrentyYears()));
				product = productrepository.save(product);
				logger.info("Exit ProductServiceImpl:: method=addProduct");
				return product;	
			} 
		} catch (InvalidClientIdException e) {
			throw new InvalidClientIdException(INVALID_CLIENT_ID_CONST + clientId);
		}	
	}


	/**
	 * Method to read all the products from the database.
	 ** 
	 * @return List of product from the database.
	 */

	@Override
	public List<Product> fetchAllProducts() {
		logger.info("Enter ProductServiceImpl:: method=getAllProducts");
		List<Product> products = productrepository.findAll();
		logger.info("Exit ProductServiceImpl:: method=getAllProducts");
		return products;
	}

	/**
	 * Method to read product based on model number.
	 * 
	 * @param modelnumber : This is the parameter to fetch product.
	 * @return Product information based on modelNumber
	 * 
	 */

	@Override
	public Product fetchProductByModelNumber(String modelNumber) {
		logger.info("Enter ProductServiceImpl:: method=fetchProductByModelNumber");
		Optional<Product> product = productrepository.findById(modelNumber);

		if (!product.isPresent())
			throw new ProductNotFoundException(PRODUCT_NOT_FOUND_CONST + modelNumber);
		logger.info("Exit ProductServiceImpl:: method=fetchProductByModelNumber");
		return product.get();
	}

	/**
	 * Method to read products based on categoryName
	 * 
	 * @param categoryName : This is the parameter to get product.
	 * @return Product information based on product category. for eg: if product
	 *         category is TV,it will fetch the details of all products whose
	 *         category is TV.
	 */

	@Override
	public List<Product> fetchbyCategoryName(String productCategoryName) {
		logger.info("Enter ProductServiceImpl:: method=fetchbyCategoryName");
		List<Product> clist = new ArrayList<>();
		List<Product> list = productrepository.findbyCategoryName(productCategoryName);
		for (Product lists : list) {
			if (lists.getProductCategoryName().equalsIgnoreCase(productCategoryName)) {
				clist.add(lists);
			} 	
			else {
				throw new ProductNotFoundException(PRODUCTCATEGORY_NOT_FOUND_CONST + productCategoryName);
			}
		}
		logger.info("Enter ProductServiceImpl:: method=fetchbyCategoryName");
		return clist;
	}
	

	/**
	 * Method to remove product from the database .
	 */
	@Override
	public Product removeProduct(String modelNumber) {
		Product p = productrepository.findById(modelNumber).get();
		productrepository.delete(p);
		return p;
	}
	
	@Transactional
	@Override
	public void updateWarrentyYears(int warrentyyears,String modelNumber) {
		logger.info("Enter ProductServiceImpl:: method=updateWarrentyYears");
		Product product = productrepository.findById(modelNumber).get();
		Product existingproduct=product;
			try {
				existingproduct.setWarrentyYears(warrentyyears);
				existingproduct.setWarrantyDate(product.getDateofPurchase().plusYears(product.getWarrentyYears()));
				productrepository.saveAndFlush(existingproduct);
				logger.info("Exit ProductServiceImpl:: method=updateWarrentyYears");
			} catch(RuntimeException e) {
				throw e;
			}
	}
}
			
	
