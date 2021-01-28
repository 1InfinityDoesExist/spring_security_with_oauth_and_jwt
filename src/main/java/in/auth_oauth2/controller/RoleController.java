package in.auth_oauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.auth_oauth2.exception.InvalidInputException;
import in.auth_oauth2.exception.ProductNotFoundException;
import in.auth_oauth2.exception.RoleAlreadyExistException;
import in.auth_oauth2.model.request.RoleCreateRequest;
import in.auth_oauth2.model.response.RoleCreateResponse;
import in.auth_oauth2.service.RoleService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/userRole")
public class RoleController {

	@Autowired
	private RoleService userRoleService;

	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> createUserRole(@RequestBody RoleCreateRequest request) throws Exception {
		log.info("------UserRole Controller Class, create UserRole method-----");
		try {
			RoleCreateResponse response = userRoleService.presistRole(request);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ModelMap().addAttribute("msg", response.getMsg()));
		} catch (final ProductNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ModelMap().addAttribute("error_msg", ex.getMessage()));
		} catch (final InvalidInputException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ModelMap().addAttribute("error_msg", ex.getMessage()));
		} catch (final RoleAlreadyExistException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ModelMap().addAttribute("error_msg", ex.getMessage()));
		}
	}
}
