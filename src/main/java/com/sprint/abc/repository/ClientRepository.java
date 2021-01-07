package com.sprint.abc.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.abc.entities.Client;

public interface ClientRepository extends JpaRepository<Client, String> {




}
