package ru.gbank.pabw.core.repositories.balance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gbank.pabw.core.entities.Balance;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findByAccountId(Long accountId);


    @Query("""
            SELECT b FROM Balance b
            JOIN Account a ON a.id = b.account.id
            WHERE a.accountNumber = ?1
            """)
    Optional<Balance> findByAccountNumber(String accountNumber);
}