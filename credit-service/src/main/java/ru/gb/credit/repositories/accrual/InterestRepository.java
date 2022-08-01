package ru.gb.credit.repositories.accrual;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.credit.entity.Interest;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
}
