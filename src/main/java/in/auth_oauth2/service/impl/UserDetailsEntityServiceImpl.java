package in.auth_oauth2.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.auth_oauth2.entity.Role;
import in.auth_oauth2.entity.UserDetailsEntity;
import in.auth_oauth2.exception.UserDetailsNotFoundException;
import in.auth_oauth2.model.request.UserDetailsEntityCreateRequest;
import in.auth_oauth2.model.request.UserDetailsEntityUpdateRequest;
import in.auth_oauth2.model.response.UserDetailsEntityCreateResponse;
import in.auth_oauth2.model.response.UserDetailsEntityUpdateResponse;
import in.auth_oauth2.repository.RoleRepository;
import in.auth_oauth2.repository.UserDetailsEntityRepository;
import in.auth_oauth2.service.UserDetailsEntityService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserDetailsEntityServiceImpl implements UserDetailsEntityService {

	@Autowired
	private BCryptPasswordEncoder passworEncoder;

	public final UserDetailsEntityRepository userDetailsEntityRepository;
	public final RoleRepository roleRepository;

	public UserDetailsEntityServiceImpl(UserDetailsEntityRepository userDetailsEntityRepository,
			RoleRepository roleRepository) {
		this.userDetailsEntityRepository = userDetailsEntityRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public UserDetailsEntityCreateResponse persistUserDetailsInDB(UserDetailsEntityCreateRequest request)
			throws Exception {
		log.info(":::::::::Inside UserDetailsEntityServiceImpl Class, persistUserDetailsInDB method:::::");
		UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
		userDetailsEntity.setPhone(request.getPhone());
		userDetailsEntity.setMiddleName(request.getMiddleName());
		userDetailsEntity.setLastName(request.getLastName());
		userDetailsEntity.setUsername(request.getUsername());
		userDetailsEntity.setPassword(passworEncoder.encode(request.getPassword()));
		List<GrantedAuthority> authorities = new ArrayList<>();
		if (request.getRolesId() instanceof List) {
			for (String role : request.getRolesId()) {
				Role roleFromDB = roleRepository.findRoleById(role);
				authorities.add(new SimpleGrantedAuthority("ROLE_" + roleFromDB.getName()));
			}
		}
		userDetailsEntity.setAuthorities(authorities);
		userDetailsEntity.setFirstName(request.getFirstName());
		userDetailsEntity.setEnabled(true);
		userDetailsEntity.setEmail(request.getEmail());
		userDetailsEntity.setCredentialsNonExpired(true);
		userDetailsEntity.setAddress(request.getAddress());
		userDetailsEntity.setAccountNonLocked(true);
		userDetailsEntity.setAccountNonExpired(true);
		userDetailsEntityRepository.save(userDetailsEntity);
		UserDetailsEntityCreateResponse response = new UserDetailsEntityCreateResponse();
		response.setId(userDetailsEntity.getId());
		response.setMsg("Successfully persisted userDetailsEntity record in db.");
		return response;

	}

	@Override
	public UserDetailsEntity retrieveUserDetailsFrUserDetailsEntityUpdateRequestomDB(String id) throws Exception {
		UserDetailsEntity userDetailsEntity = userDetailsEntityRepository.findUserDetailsEntityById(id);
		if (ObjectUtils.isEmpty(userDetailsEntity)) {
			throw new UserDetailsNotFoundException(String.format("No userDetailsEntity data exist for id %s", id));
		}
		return userDetailsEntity;

	}

	@Override
	public List<UserDetailsEntity> retrieveAllUserDetails() {
		List<UserDetailsEntity> listOfUserDetailsEntity = userDetailsEntityRepository.findAll();
		return listOfUserDetailsEntity;
	}

	@Override
	public void deleteUserDetails(String id) throws Exception {
		UserDetailsEntity userDetailsEntity = userDetailsEntityRepository.findUserDetailsEntityById(id);
		if (ObjectUtils.isEmpty(userDetailsEntity)) {
			throw new UserDetailsNotFoundException(String.format("No userDetailsEntity data exist for id %s", id));
		}
		userDetailsEntityRepository.delete(userDetailsEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserDetailsEntityUpdateResponse updateUserDetailsEntity(UserDetailsEntityUpdateRequest request, String id)
			throws Exception {
		UserDetailsEntity userDetailsEntity = userDetailsEntityRepository.findUserDetailsEntityById(id);
		if (ObjectUtils.isEmpty(userDetailsEntity)) {
			throw new UserDetailsNotFoundException(String.format("No userDetailsEntity data exist for id %s", id));
		}
		JSONObject payloadJsonObject = (JSONObject) new JSONParser()
				.parse(new ObjectMapper().writeValueAsString(request));
		JSONObject dbJsonObject = (JSONObject) new JSONParser()
				.parse(new ObjectMapper().writeValueAsString(userDetailsEntity));
		for (Object obj : payloadJsonObject.keySet()) {
			String param = (String) obj;
			dbJsonObject.put(param, payloadJsonObject.get(param));
		}
		userDetailsEntityRepository
				.save(new ObjectMapper().readValue(dbJsonObject.toJSONString(), UserDetailsEntity.class));

		UserDetailsEntityUpdateResponse response = new UserDetailsEntityUpdateResponse();
		response.setId(userDetailsEntity.getId());
		response.setMsg("Successfully updated");
		return response;
	}
}
