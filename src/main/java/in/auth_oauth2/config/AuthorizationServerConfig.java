package in.auth_oauth2.config;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	/*
	 * 0oa71ctnSxLOSmSwE5d5
	 */
	@Value("${auth.clientId}")
	private String clientId;

	/*
	 * $2y$12$JeKuQ5yUQLuZ03cEsRgUm.JX6lm/C6qN3ALHWaWQmHQNrPsc3EKjG
	 */

	@Value("${auth.private.key}")
	private String privateKey;

	@Value("${auth.public.key}")
	private String publicKey;

	@Value("${auth.clientSecret}")
	private String clientSecret;

	@Value("${auth.grantTypePassword}")
	private String grantTypePassword;

	@Value("${auth.authorizationCode:authorization_code}")
	private String authorizationCode;

	@Value("${auth.refreshToken:refresh_token}")
	private String refreshToken;

	@Value("${auth.implicit:implicit}")
	private String implicit;

	@Value("${auth.scopeRead:read}")
	private String scopeRead;

	@Value("${auth.scopeWrite:write}")
	private String scopeWrite;

	@Value("${auth.trust:trust}")
	private String trust;

	@Value("${auth.accessTokenValiditySeconds:42000}")
	private int accessTokenValiditySeconds;

	@Value("${auth.refreshTokenValiditySeconds:216000}")
	private int refreshTokenValiditySeconds;

	@Value("${auth.jwt.signingKey}")
	private String signingKey;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * This is mainly for validation for resource server
	 *
	 * by default it denies all
	 * http://localhost:8080/oauth/check_token?token=839fcd96-5032-48f1-a0c0-
	 * 6aa4732d519d add .checkTokenAccess("isAuthenticated()") pass basic auth add
	 * .checkTokenAccess("permitAll()") disable basic auth bad approach
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").passwordEncoder(passwordEncoder);
	}

	/*
	 * 
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret))
				.authorizedGrantTypes(grantTypePassword, authorizationCode, refreshToken, implicit)
				.scopes(scopeRead, scopeWrite, trust).accessTokenValiditySeconds(accessTokenValiditySeconds)
				.refreshTokenValiditySeconds(refreshTokenValiditySeconds);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
				.accessTokenConverter(accessTokenConverter()).tokenEnhancer(tokenEnhancerChain);
	}

	/*
	 * Implement TokenEnhancer Interface to change the token Response. You can add
	 * some more attribute if you want
	 */
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(new String(Base64.getDecoder().decode(privateKey)));
		// converter.setVerifierKey(new String(Base64.getDecoder().decode(publicKey)));
		return converter;
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

}
