package in.auth_oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Public Key 1st : openssl rsa -in jwt.pem -pubout 
@SpringBootApplication
public class AuthOauth2Application {

	public static void main(String[] args) {
		SpringApplication.run(AuthOauth2Application.class, args);
	}

}

/*
 * Private Key 1st : openssl genrsa -out jwt.pem 2048 2nd : openssl rsa -in
 * jwt.pem
 * 
 */
