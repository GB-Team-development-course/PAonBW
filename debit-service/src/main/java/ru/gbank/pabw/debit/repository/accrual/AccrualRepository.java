package ru.gbank.pabw.debit.repository.accrual;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gbank.pabw.debit.entity.Accrual;

@Repository
public interface AccrualRepository extends JpaRepository<Accrual, Long> {
}
