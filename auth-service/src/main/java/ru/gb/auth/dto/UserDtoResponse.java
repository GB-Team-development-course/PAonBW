package ru.gb.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDtoResponse {

	private String username;
	private String email;
}
