package com.sprint.abc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sprint.abc.entities.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	@Query("Select p FROM Product p where p.productCategoryName=:productCategoryName")
 public List<Product> findbyCategoryName(@Param("productCategoryName")String productCategoryName);

}
