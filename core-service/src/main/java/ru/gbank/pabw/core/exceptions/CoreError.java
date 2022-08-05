package ru.gbank.pabw.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoreError {
    private String code;
    private String message;

}
