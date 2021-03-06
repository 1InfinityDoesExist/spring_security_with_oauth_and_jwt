package in.auth_oauth2.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;

@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleCreateRequest {
	private String name;
	private String description;
	private String productId;
}
