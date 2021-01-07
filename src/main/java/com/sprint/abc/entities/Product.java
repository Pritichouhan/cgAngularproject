package com.sprint.abc.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="product_tbl_1")
public class Product {

	@Id
	private String modelNumber;
	
	private String productName;
	
	@NotEmpty(message = "Product category name must not be empty")
	private String productCategoryName; // washing machine , TV,AC,SmartPhone
	
	@NotNull(message = "Date of purchase name must not be empty")
	private LocalDate dateofPurchase;
	
	
//	@NotNull(message = "warrenty years name must not be empty")
	private int warrentyYears;
	
	private LocalDate warrantyDate; // should be auto generated
	
//	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "clientid", nullable=true)
	private Client clientProduct;

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	public LocalDate getDateofPurchase() {
		return dateofPurchase;
	}

	public void setDateofPurchase(LocalDate dateofPurchase) {
		this.dateofPurchase = dateofPurchase;
	}

	public int getWarrentyYears() {
		return warrentyYears;
	}

	public void setWarrentyYears(int warrentyYears) {
		this.warrentyYears = warrentyYears;
	}

	public LocalDate getWarrantyDate() {
		return warrantyDate;
	}

	public void setWarrantyDate(LocalDate warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	public Client getClientProduct() {
		return clientProduct;
	}

	@JsonIgnore
	public void setClientProduct(Client clientProduct) {
		this.clientProduct = clientProduct;
	}
	
}