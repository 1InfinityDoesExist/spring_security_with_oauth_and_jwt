package in.auth_oauth2.service;

import org.springframework.stereotype.Component;

import in.auth_oauth2.model.request.RoleCreateRequest;
import in.auth_oauth2.model.response.RoleCreateResponse;

@Component
public interface RoleService {

	public RoleCreateResponse presistRole(RoleCreateRequest roleCreateRequest);
}
