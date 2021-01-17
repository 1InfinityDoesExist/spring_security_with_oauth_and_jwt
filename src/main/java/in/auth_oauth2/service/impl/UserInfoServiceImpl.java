package in.auth_oauth2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import in.auth_oauth2.entity.UserInfo;
import in.auth_oauth2.model.request.UserInfoCreateRequest;
import in.auth_oauth2.model.response.UserInfoCreateResponse;
import in.auth_oauth2.repository.UserInfoRepository;
import in.auth_oauth2.service.UserInfoService;

@Component
public class UserInfoServiceImpl implements UserInfoService {

	private UserInfoRepository userInfoRepository;

	@Autowired
	public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
		this.userInfoRepository = userInfoRepository;
	}

	@Override
	public UserInfoCreateResponse createUserInfo(UserInfoCreateRequest request) throws Exception {
		UserInfo userInfo = new UserInfo();
		userInfo.setFirstName(request.getFirstName());
		userInfo.setLastName(request.getLastName());
		userInfo.setMobile(request.getMobile());
		userInfo.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
		userInfo.setUserName(request.getUserName());
		userInfo.setEmail(request.getEmail());
		userInfo.setRoles(request.getRoles());
		userInfoRepository.save(userInfo);
		UserInfoCreateResponse response = new UserInfoCreateResponse();
		response.setId(userInfo.getId());
		return response;
	}
}
