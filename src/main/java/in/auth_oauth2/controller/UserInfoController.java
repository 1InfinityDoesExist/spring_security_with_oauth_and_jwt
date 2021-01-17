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
import in.auth_oauth2.model.request.UserInfoCreateRequest;
import in.auth_oauth2.model.response.UserInfoCreateResponse;
import in.auth_oauth2.service.UserInfoService;

@RestController
@RequestMapping(path = "/userInfo")
public class UserInfoController {
	@Autowired
	private UserInfoService userInfoService;

	@PostMapping(path = "/create")
	public ResponseEntity<?> createUserInfo(@RequestBody(required = true) UserInfoCreateRequest request)
			throws Exception {
		try {
			UserInfoCreateResponse response = userInfoService.createUserInfo(request);
			return ResponseEntity.status(HttpStatus.CREATED).body(
					new ModelMap().addAttribute("msg", "Successfully created").addAttribute("response", response));
		} catch (final InvalidInputException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ModelMap().addAttribute("msg", ex.getMessage()));
		}
	}
}
