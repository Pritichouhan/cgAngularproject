package com.sprint.abc.service;

import com.sprint.abc.entities.Admin;
import com.sprint.abc.entities.Client;
import com.sprint.abc.entities.Engineer;

public interface LoginService {

	public Client login(String username, String password);

	public Client logout(String clientId);

	

//	public Engineer login2(int EngineerId, String password);

	public Engineer logout2(int EngineerId);

//	public Admin login3(int AdminId, String password);

	public Admin logout3(int AdminId);

	Engineer login2(String EngineerId, String password);

	Admin login3(String AdminId, String password);
}
