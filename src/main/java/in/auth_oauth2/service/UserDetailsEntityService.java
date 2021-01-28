package in.auth_oauth2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.auth_oauth2.entity.UserDetailsEntity;
import in.auth_oauth2.model.request.UserDetailsEntityCreateRequest;
import in.auth_oauth2.model.request.UserDetailsEntityUpdateRequest;
import in.auth_oauth2.model.response.UserDetailsEntityCreateResponse;
import in.auth_oauth2.model.response.UserDetailsEntityUpdateResponse;

@Service
public interface UserDetailsEntityService {

	public UserDetailsEntityCreateResponse persistUserDetailsInDB(UserDetailsEntityCreateRequest request)
			throws Exception;

	public UserDetailsEntity retrieveUserDetailsFrUserDetailsEntityUpdateRequestomDB(String id) throws Exception;

	public List<UserDetailsEntity> retrieveAllUserDetails();

	public void deleteUserDetails(String id) throws Exception;

	public UserDetailsEntityUpdateResponse updateUserDetailsEntity(UserDetailsEntityUpdateRequest request, String id)
			throws Exception;
}
