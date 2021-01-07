package com.sprint.abc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sprint.abc.entities.Admin;
import com.sprint.abc.entities.Complaint;

public interface AdminRepository extends JpaRepository<Admin,Integer>{
	
	@Query(value="select c from Complaint c where c.status=:x")
	public List<Complaint> getComplaintsByStatus(@Param("x") String status);
 

}
