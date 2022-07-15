package ru.gb.auth.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthError {
    private String code;
    private String message;

}
