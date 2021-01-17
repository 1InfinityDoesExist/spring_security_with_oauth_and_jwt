package in.auth_oauth2.service;

import org.springframework.stereotype.Component;

import in.auth_oauth2.model.request.UserInfoCreateRequest;
import in.auth_oauth2.model.response.UserInfoCreateResponse;

@Component
public interface UserInfoService {
	public UserInfoCreateResponse createUserInfo(UserInfoCreateRequest request) throws Exception;
}
