package in.auth_oauth2.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleCreateResponse {
	private String id;
	private String name;
	private String description;
	private boolean isActive;
	private String msg;
}
