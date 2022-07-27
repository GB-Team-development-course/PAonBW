package ru.gb.core.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.core.converters.BalanceConverter;
import ru.gb.core.dto.BalanceDto;
import ru.gb.core.services.BalanceService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/balance")
@AllArgsConstructor
public class BalanceController {
    private final BalanceService balanceService;
    private final BalanceConverter balanceConverter;

    @GetMapping("")
    public List<BalanceDto> findAll() {
        return balanceService.findAll().stream()
                .map(balanceConverter::entityToDto)
                .collect(Collectors.toList());
    }
}
