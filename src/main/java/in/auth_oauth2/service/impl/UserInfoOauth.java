package in.auth_oauth2.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import in.auth_oauth2.entity.Role;
import in.auth_oauth2.entity.UserInfo;
import in.auth_oauth2.repository.RoleRepository;
import in.auth_oauth2.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userInfoOauthService")
public class UserInfoOauth implements UserDetailsService {

	private final UserInfoRepository userInfoRepository;

	private final RoleRepository roleRepository;

	@Autowired
	public UserInfoOauth(UserInfoRepository userInfoRepository, RoleRepository roleRepository) {
		this.userInfoRepository = userInfoRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String grantType = request.getParameter("grant_type");
		UserDetails userDetails = getUserInfoUserDetails(userName, password, grantType);
		return userDetails;
	}

	private UserDetails getUserInfoUserDetails(String userName, String password, String grantType) {
		UserInfo userInfo = userInfoRepository.findByUserName(userName);
		UserDetails userDetails = new User(userName, new BCryptPasswordEncoder().encode(password),
				getUserInfoGrantedAuthority(userInfo));
		return userDetails;
	}

	private Collection<? extends GrantedAuthority> getUserInfoGrantedAuthority(UserInfo userInfo) {
		List<SimpleGrantedAuthority> grantedAuthority = new ArrayList<>();
		for (String roleId : userInfo.getRoles()) {
			Optional<Role> role = roleRepository.findById(roleId);
			if (role.isPresent()) {
				grantedAuthority.add(new SimpleGrantedAuthority("ROLE_" + role.get().getName()));
			}
		}
		return grantedAuthority;
	}

}
