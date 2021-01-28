package in.auth_oauth2.service.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.auth_oauth2.entity.ClientDetailsEntity;
import in.auth_oauth2.exception.ClientDetailsEntityNotFoundException;
import in.auth_oauth2.model.request.ClientDetailsEntityCreateRequest;
import in.auth_oauth2.model.request.ClientEntityUpdateRequest;
import in.auth_oauth2.model.response.ClientDetailsEntityCreateResponse;
import in.auth_oauth2.model.response.ClientDetailsEntityUpdateResponse;
import in.auth_oauth2.repository.ClientDetailsEntityRepository;
import in.auth_oauth2.service.ClientDetailsEntityService;

@Component
public class ClientDetailsEntityServiceImpl implements ClientDetailsEntityService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private final ClientDetailsEntityRepository clientDetailsEntityRepository;

	public ClientDetailsEntityServiceImpl(ClientDetailsEntityRepository clientDetailsEntityRepository) {
		this.clientDetailsEntityRepository = clientDetailsEntityRepository;
	}

	@Override
	public ClientDetailsEntityCreateResponse persistClientEntityDetaisInDB(ClientDetailsEntityCreateRequest request) {
		ClientDetailsEntity clientDetails = new ClientDetailsEntity();
		clientDetails.setAutoApprove(true);
		clientDetails.setClientId(request.getClientId());
		clientDetails.setAccessTokenValiditySeconds(request.getAccessTokenValiditySeconds());
		clientDetails.setAdditionalInformation(request.getAdditionalInformation());
		clientDetails.setAuthorities(request.getAuthorities());
		clientDetails.setAuthorizedGrantTypes(request.getAuthorizedGrantTypes());
		clientDetails.setClientSecret(passwordEncoder.encode(request.getClientSecret()));
		clientDetails.setRefreshTokenValiditySeconds(request.getRefreshTokenValiditySeconds());
		clientDetails.setRegisteredRedirectUri(request.getRegisteredRedirectUri());
		clientDetails.setResourceIds(request.getResourceIds());
		clientDetails.setScope(request.getScope());
		clientDetails.setScoped(true);
		clientDetails.setSecretRequired(true);
		clientDetailsEntityRepository.save(clientDetails);
		ClientDetailsEntityCreateResponse response = new ClientDetailsEntityCreateResponse();
		response.setId(clientDetails.getId());
		response.setMsg("Successfully created");
		return response;
	}

	@Override
	public ClientDetailsEntity retrieveClientDetails(String id) throws Exception {
		ClientDetailsEntity clientDetailsEntity = clientDetailsEntityRepository.findClientDetailsEntityById(id);
		if (ObjectUtils.isEmpty(clientDetailsEntity)) {
			throw new ClientDetailsEntityNotFoundException(String.format("No clientDetailsEntity found for id %s", id));
		}
		return clientDetailsEntity;
	}

	@Override
	public List<ClientDetailsEntity> retrieveAllClientDetails() {
		List<ClientDetailsEntity> listOfClientDetailsEntity = clientDetailsEntityRepository.findAll();
		return listOfClientDetailsEntity;
	}

	@Override
	public void deleteClientDetails(String id) throws Exception {
		ClientDetailsEntity clientDetailsEntity = clientDetailsEntityRepository.findClientDetailsEntityById(id);
		if (ObjectUtils.isEmpty(clientDetailsEntity)) {
			throw new ClientDetailsEntityNotFoundException(String.format("No clientDetailsEntity found for id %s", id));
		}
		clientDetailsEntityRepository.delete(clientDetailsEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ClientDetailsEntityUpdateResponse updateClientDetailsEntity(ClientEntityUpdateRequest request, String id)
			throws Exception {
		ClientDetailsEntity clientDetailsEntity = clientDetailsEntityRepository.findClientDetailsEntityById(id);
		if (ObjectUtils.isEmpty(clientDetailsEntity)) {
			throw new ClientDetailsEntityNotFoundException(String.format("No clientDetailsEntity found for id %s", id));
		}
		JSONObject payloadJsonObject = (JSONObject) new JSONParser()
				.parse(new ObjectMapper().writeValueAsString(request));
		JSONObject dbJsonObject = (JSONObject) new JSONParser()
				.parse(new ObjectMapper().writeValueAsString(clientDetailsEntity));
		for (Object obj : payloadJsonObject.keySet()) {
			String param = (String) obj;
			dbJsonObject.put(param, payloadJsonObject.get(param));
		}
		clientDetailsEntityRepository
				.save(new ObjectMapper().readValue(dbJsonObject.toJSONString(), ClientDetailsEntity.class));
		ClientDetailsEntityUpdateResponse response = new ClientDetailsEntityUpdateResponse();
		response.setId(clientDetailsEntity.getId());
		response.setMsg("Successfully updated");
		return response;
	}

}
