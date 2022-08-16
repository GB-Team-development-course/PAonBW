package ru.gbank.pabw.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gbank.pabw.model.enums.AccountType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveAccountRequest {

    private LocalDate currentDate;

    private AccountType accountType;


}
