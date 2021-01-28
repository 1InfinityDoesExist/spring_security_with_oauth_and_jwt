package in.auth_oauth2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import in.auth_oauth2.entity.UserDetailsEntity;
import in.auth_oauth2.repository.UserDetailsEntityRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDetailsEntityRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info(":::::::::LoadUserByUsername::::::");
		UserDetailsEntity userDetailsEntity = repo.findUserDetailsEntityByUsername(username);
		log.info(":::::userDetailsEntity {}", userDetailsEntity);
		return new CustomUserDetails(userDetailsEntity);
	}

}
