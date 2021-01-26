package in.auth_oauth2.security;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import in.auth_oauth2.entity.ClientDetailsEntity;

public class ClientDetailsServiceImpl implements ClientDetailsService {

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		ClientDetailsEntity clientDetailsEntity = null;
		return new CustomClientDetails(clientDetailsEntity);
	}
}
