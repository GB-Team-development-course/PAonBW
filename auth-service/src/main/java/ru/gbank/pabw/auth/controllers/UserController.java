package ru.gbank.pabw.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gbank.pabw.auth.dto.UserDtoRequest;
import ru.gbank.pabw.auth.dto.UserDtoResponse;
import ru.gbank.pabw.auth.services.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping
	public ResponseEntity<UserDtoResponse> registerUserAccount(@RequestBody UserDtoRequest userDto) {
		return userService.registerNewUserAccount(userDto)
				.map(user -> ResponseEntity.ok(new UserDtoResponse(user.getUsername(), user.getEmail()))).orElseThrow();

	}
}
