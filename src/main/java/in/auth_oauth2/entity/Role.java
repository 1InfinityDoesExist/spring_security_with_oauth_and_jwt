package in.auth_oauth2.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

@lombok.Data
@Document(collection = "role")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role {

	@Id
	private String id;
	private String name;
	private String description;
	private boolean isActive;
}
