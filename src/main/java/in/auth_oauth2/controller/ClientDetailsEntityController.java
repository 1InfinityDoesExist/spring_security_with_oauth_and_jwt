package in.auth_oauth2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.auth_oauth2.model.request.ClientDetailsEntityCreateRequest;
import in.auth_oauth2.model.response.ClientDetailsEntityCreateResponse;
import in.auth_oauth2.service.ClientDetailsEntityService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/clientDetails")
public class ClientDetailsEntityController {

	private final ClientDetailsEntityService clientDetailsEntityService;

	public ClientDetailsEntityController(ClientDetailsEntityService clientDetailsEntityService) {
		this.clientDetailsEntityService = clientDetailsEntityService;
	}

	@PostMapping("/persist")
	public ResponseEntity<?> persistClientDetailsEntity(@RequestBody ClientDetailsEntityCreateRequest request) {
		log.info(":::::ClientDetailsEntityService Class, persistClientDetailsEntity method:::::");
		ClientDetailsEntityCreateResponse response = clientDetailsEntityService.persistClientEntityDetaisInDB(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ModelMap().addAttribute("response", response));
	}
}
