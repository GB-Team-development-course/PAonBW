package ru.gbank.pabw.debit.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gbank.pabw.debit.entity.Accrual;
import ru.gbank.pabw.debit.repository.accrual.AccrualRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AccrualService {
    private final AccrualRepository accrualRepository;

    public void saveAll(List<Accrual> accruals){
        accrualRepository.saveAll(accruals);
    }
}
