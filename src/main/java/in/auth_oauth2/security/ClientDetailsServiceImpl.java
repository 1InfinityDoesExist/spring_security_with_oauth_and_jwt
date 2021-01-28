package in.auth_oauth2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import in.auth_oauth2.entity.ClientDetailsEntity;
import in.auth_oauth2.repository.ClientDetailsEntityRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientDetailsServiceImpl implements ClientDetailsService {

	@Autowired
	private ClientDetailsEntityRepository repo;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		log.info(":::::::LoadClientByClientId ::::::");
		ClientDetailsEntity clientDetailsEntity = repo.findClientDetailsEntityByClientId(clientId);
		log.info(":::::clientDetailsEntity {}", clientDetailsEntity);
		return new CustomClientDetails(clientDetailsEntity);
	}
}
