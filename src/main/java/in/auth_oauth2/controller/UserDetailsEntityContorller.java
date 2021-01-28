package in.auth_oauth2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.auth_oauth2.model.request.UserDetailsEntityCreateRequest;
import in.auth_oauth2.model.response.UserDetailsEntityCreateResponse;
import in.auth_oauth2.service.UserDetailsEntityService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/userDetails")
public class UserDetailsEntityContorller {

	private final UserDetailsEntityService userDetailsEntityService;

	public UserDetailsEntityContorller(UserDetailsEntityService userDetailsEntityService) {
		this.userDetailsEntityService = userDetailsEntityService;
	}

	@PostMapping("/persist")
	public ResponseEntity<ModelMap> persistUserDetails(@RequestBody UserDetailsEntityCreateRequest request)
			throws Exception {
		log.info(":::::UserDetailsEntityController Class, persistUserDetails method:::::");
		UserDetailsEntityCreateResponse response = userDetailsEntityService.persistUserDetailsInDB(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ModelMap().addAttribute("response", response));
	}
}
