package in.auth_oauth2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.auth_oauth2.entity.Role;
import in.auth_oauth2.model.request.RoleCreateRequest;
import in.auth_oauth2.model.response.RoleCreateResponse;
import in.auth_oauth2.repository.RoleRepository;
import in.auth_oauth2.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository userRoleRepository;

	@Override
	public RoleCreateResponse presistRole(RoleCreateRequest request) {
		Role role = new Role();
		role.setActive(true);
		role.setDescription(request.getDescription());
		role.setName(request.getName());
		userRoleRepository.save(role);
		RoleCreateResponse response = new RoleCreateResponse();
		response.setId(role.getId());
		response.setMsg("Successfully created userRole");
		response.setName(role.getName());
		return response;
	}

}
