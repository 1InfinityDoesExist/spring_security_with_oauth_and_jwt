package in.auth_oauth2.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> additionalInfo = new HashMap<>();
		String authDetails = authentication.getUserAuthentication().getDetails().toString();
		log.info("::::::authDetails {}", authDetails);
		String userName = authDetails.split("username=")[1].split(",")[0].replace("}", "");
		additionalInfo.put("UserName", userName);
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}

}
