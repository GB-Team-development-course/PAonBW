package ru.gb.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.auth.dto.UserDtoRequest;
import ru.gb.auth.dto.UserDtoResponse;
import ru.gb.auth.services.UserService;

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
