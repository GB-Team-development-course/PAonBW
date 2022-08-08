package ru.gbank.pabw.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "Модель запроса регистрации пользователя")
public class UserDtoRequest {

	@NotNull
	@NotEmpty
	@Schema(description = "Имя нового пользовтеля", required = true, example = "Bob")
	private String username;

	@NotNull
	@NotEmpty
	@Schema(description = "Пароль нового пользовтеля", required = true, example = "1")
	private String password;
	@NotNull
	@NotEmpty
	@Schema(description = "Повторенный пароль нового пользовтеля", required = true, example = "1")
	private String matchingPassword;

	@NotNull
	@NotEmpty
	@Email
	@Schema(description = "Email нового пользовтеля", required = true, example = "1@1.ru")
	private String email;
}
