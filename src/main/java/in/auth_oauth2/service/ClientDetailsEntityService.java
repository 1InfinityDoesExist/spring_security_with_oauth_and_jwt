package in.auth_oauth2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.auth_oauth2.entity.ClientDetailsEntity;
import in.auth_oauth2.model.request.ClientDetailsEntityCreateRequest;
import in.auth_oauth2.model.request.ClientEntityUpdateRequest;
import in.auth_oauth2.model.response.ClientDetailsEntityCreateResponse;
import in.auth_oauth2.model.response.ClientDetailsEntityUpdateResponse;

@Service
public interface ClientDetailsEntityService {

	public ClientDetailsEntityCreateResponse persistClientEntityDetaisInDB(ClientDetailsEntityCreateRequest request);

	public ClientDetailsEntity retrieveClientDetails(String id) throws Exception;

	public List<ClientDetailsEntity> retrieveAllClientDetails() throws Exception;

	public void deleteClientDetails(String id) throws Exception;

	public ClientDetailsEntityUpdateResponse updateClientDetailsEntity(ClientEntityUpdateRequest request, String id)
			throws Exception;

}
