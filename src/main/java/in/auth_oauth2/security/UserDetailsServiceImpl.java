package in.auth_oauth2.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import in.auth_oauth2.entity.UserDetailsEntity;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetailsEntity userDetailsEntity = null;
		return new CustomUserDetails(userDetailsEntity);
	}

}
