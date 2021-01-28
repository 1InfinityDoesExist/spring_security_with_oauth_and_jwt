package in.auth_oauth2.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@lombok.Data
@Document(collection = "product")
public class Product {
	@Id
	private String id;
	private String name;
	private String description;
	private boolean active;
	private String domain;

}
