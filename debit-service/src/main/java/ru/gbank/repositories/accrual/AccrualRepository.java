package ru.gbank.repositories.accrual;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gbank.entity.Accrual;

@Repository
public interface AccrualRepository extends JpaRepository<Accrual, Long> {
}
