package in.auth_oauth2.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import in.auth_oauth2.entity.UserDetailsEntity;

public class CustomUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final UserDetailsEntity userDetailsEntity;

	public CustomUserDetails(UserDetailsEntity userDetailsEntity) {
		this.userDetailsEntity = userDetailsEntity;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userDetailsEntity.getAuthorities();
	}

	@Override
	public String getPassword() {
		return userDetailsEntity.getPassword();
	}

	@Override
	public String getUsername() {
		return userDetailsEntity.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return userDetailsEntity.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return userDetailsEntity.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return userDetailsEntity.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return userDetailsEntity.isEnabled();
	}

}
