package ru.gbank.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gbank.entity.Accrual;
import ru.gbank.repositories.accrual.AccrualRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AccrualService {
    private final AccrualRepository accrualRepository;

    public void saveAll(List<Accrual> accruals){
        accrualRepository.saveAll(accruals);
    }
}
