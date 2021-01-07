package com.sprint.abc.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sprint.abc.entities.Client;
import com.sprint.abc.entities.Complaint;
import com.sprint.abc.entities.Engineer;

public interface EngineerRepository extends JpaRepository<Engineer, Integer> {
	
	@Query("Select c from Complaint c where c.engineer.engineerId = :engineerid and c.status='Open'")
	public List<Complaint> getAllOpenComplaints(@Param("engineerid") int eid) ;
	
	@Query("Select c from Complaint c where c.engineer.engineerId = :engineerid and c.status = 'Resolved'")
	public List<Complaint> getResolvedComplaints(@Param("engineerid") int eid) ;
	
	@Query("Select c from Complaint c where c.engineer.engineerId = :engineerid")
	public List<Complaint> getAllComplaints(@Param("engineerid") int eid) ;
	
	@Modifying
	@Query("update Complaint set status = :status where complaintId = :complaintId")
	public void changeComplaintStatus(@Param("complaintId") int complaintId, @Param("status") String status) ;
	
	@Query("Select c from Complaint c where c.engineer.engineerId = :engineerid ORDER BY c.complaintDate")
	public List<Complaint> sortComplaintsByDate(@Param("engineerid") int eid);
}



