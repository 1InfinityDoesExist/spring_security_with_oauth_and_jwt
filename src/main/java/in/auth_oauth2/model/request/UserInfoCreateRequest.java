package in.auth_oauth2.model.request;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoCreateRequest {

	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String email;
	private String mobile;
	private List<String> roles = new ArrayList<>();
}
