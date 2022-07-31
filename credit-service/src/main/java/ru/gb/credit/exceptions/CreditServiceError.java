package ru.gb.credit.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditServiceError {
    private String code;
    private String message;

}
