package ru.gb.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель клиента")
public class ClientDto {

    @Schema(description = "Имя клиента", required = true, example = "bob")
    private String username;

}
