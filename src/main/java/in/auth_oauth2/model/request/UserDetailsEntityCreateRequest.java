package in.auth_oauth2.model.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailsEntityCreateRequest {
	// UserDetails
	private String password;
	private String username;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private List<String> rolesId;

	// Basic Details
	private String email;
	private String phone;
	private String firstName;
	private String lastName;
	private String middleName;
	private String address;
}
