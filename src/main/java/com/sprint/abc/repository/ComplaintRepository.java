package com.sprint.abc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.abc.entities.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer>{
	

}
