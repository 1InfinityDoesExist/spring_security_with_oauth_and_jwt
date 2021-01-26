package in.auth_oauth2.security;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import in.auth_oauth2.entity.ClientDetailsEntity;

public class CustomClientDetails implements ClientDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ClientDetailsEntity clientDetailsEntity;

	public CustomClientDetails(ClientDetailsEntity clientDetailsEntity) {
		this.clientDetailsEntity = clientDetailsEntity;
	}

	@Override
	public String getClientId() {
		return clientDetailsEntity.getClientId();
	}

	@Override
	public Set<String> getResourceIds() {
		return clientDetailsEntity.getGetResourceIds();
	}

	@Override
	public boolean isSecretRequired() {
		return clientDetailsEntity.isSecretRequired();
	}

	@Override
	public String getClientSecret() {
		return clientDetailsEntity.getGetClientSecret();
	}

	@Override
	public boolean isScoped() {
		return clientDetailsEntity.isScoped();
	}

	@Override
	public Set<String> getScope() {
		return clientDetailsEntity.getGetScope();
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return clientDetailsEntity.getGetAuthorizedGrantTypes();
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return clientDetailsEntity.getGetRegisteredRedirectUri();
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return clientDetailsEntity.getGetAuthorities();
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return clientDetailsEntity.getGetAccessTokenValiditySeconds();
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return clientDetailsEntity.getGetRefreshTokenValiditySeconds();
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return clientDetailsEntity.isAutoApprove();
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return clientDetailsEntity.getGetAdditionalInformation();
	}

}
