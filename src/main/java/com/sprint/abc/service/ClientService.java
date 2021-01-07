package com.sprint.abc.service;

 

import java.util.List;

 

import com.sprint.abc.exceptions.OperationFailedException;
import com.sprint.abc.exceptions.ResourceNotFoundException;
import com.sprint.abc.entities.Client;
import com.sprint.abc.entities.Complaint;

 

public interface ClientService {
    
    public Client saveClient(Client client) throws OperationFailedException;

 

    public Client findClient(String clientId) throws ResourceNotFoundException;

 

    public String changeComplaintStatus(String clientId, Integer complaintId);

 

    public List<Complaint> getClientAllComplaintSorted(String clientId);

 

//    public List<Client> getAllClientComplaints();

 

//    public List<Complaint> getAllClientComplaintDetails(String clientId);

 

}
 