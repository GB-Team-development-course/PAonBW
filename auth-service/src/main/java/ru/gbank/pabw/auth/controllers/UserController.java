package ru.gbank.pabw.auth.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gbank.pabw.auth.dto.JwtResponse;
import ru.gbank.pabw.auth.dto.UserDtoRequest;
import ru.gbank.pabw.auth.dto.UserDtoResponse;
import ru.gbank.pabw.auth.services.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Методы работы с пользователями")
public class UserController {
	private final UserService userService;

	@Operation(summary = "Регистрация нового пользователя", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDtoResponse.class)))})
	@PostMapping
	public ResponseEntity<UserDtoResponse> registerUserAccount(@RequestBody UserDtoRequest userDto) {
		return userService.registerNewUserAccount(userDto)
				.map(user -> ResponseEntity.ok(new UserDtoResponse(user.getUsername(), user.getEmail()))).orElseThrow();

	}
}
