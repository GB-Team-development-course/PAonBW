package ru.gb.core.repositories.balance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.core.entities.Balance;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {


    Optional<Balance> findByAccountId(Long accountId);
}