package ru.gbank.pabw.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Модель запроса JWT токена")
public class JwtRequest {
    @Schema(description = "Имя пользовтеля", required = true, example = "Bob")
    private String username;
    @Schema(description = "Пароль пользовтеля", required = true, example = "1")
    private String password;
}
