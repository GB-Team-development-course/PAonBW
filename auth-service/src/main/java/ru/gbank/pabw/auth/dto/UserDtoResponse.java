package ru.gbank.pabw.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Модель пользовател")
public class UserDtoResponse {

	@Schema(description = "Имя пользовтеля", required = true, example = "1")
	private String username;
	@Schema(description = "Email пользовтеля", required = true, example = "1@1.ru")
	private String email;
}
