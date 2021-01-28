package in.auth_oauth2.entity;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@lombok.Data
@Document(collection = "user_details_entity")
public class UserDetailsEntity {

	@Id
	private String id;
	/**
	 * Returns the authorities granted to the user. Cannot return <code>null</code>.
	 *
	 * @return the authorities, sorted by natural key (never <code>null</code>)
	 */
	private Collection<? extends GrantedAuthority> authorities;

	/**
	 * Returns the password used to authenticate the user.
	 *
	 * @return the password
	 */
	private String password;

	/**
	 * Returns the username used to authenticate the user. Cannot return
	 * <code>null</code>.
	 *
	 * @return the username (never <code>null</code>)
	 */
	private String username;

	/**
	 * Indicates whether the user's account has expired. An expired account cannot
	 * be authenticated.
	 *
	 * @return <code>true</code> if the user's account is valid (ie non-expired),
	 *         <code>false</code> if no longer valid (ie expired)
	 */
	private boolean accountNonExpired;

	/**
	 * Indicates whether the user is locked or unlocked. A locked user cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user is not locked, <code>false</code>
	 *         otherwise
	 */
	private boolean accountNonLocked;

	/**
	 * Indicates whether the user's credentials (password) has expired. Expired
	 * credentials prevent authentication.
	 *
	 * @return <code>true</code> if the user's credentials are valid (ie
	 *         non-expired), <code>false</code> if no longer valid (ie expired)
	 */
	private boolean credentialsNonExpired;

	/**
	 * Indicates whether the user is enabled or disabled. A disabled user cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user is enabled, <code>false</code>
	 *         otherwise
	 */
	private boolean enabled;

	private String email;
	private String phone;
	private String firstName;
	private String lastName;
	private String middleName;
	private String address;

}
