package ru.gbank.pawb.credit.repositories.accrual;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gbank.pawb.credit.entity.Interest;


@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
}
