package ru.gb.core.repositories.account;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.core.entities.Account;
import ru.gb.core.enums.AccountType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("""
            SELECT a FROM Account a
            WHERE a.username = ?1
            """)
    List<Account> findAllByClientUsername(String username);

    @Query("""
            SELECT a FROM Account a
            WHERE a.username = ?1 AND a.accountNumber = ?2
            """)
    Optional<Account> findByClientUsernameAccountByAccountNumber(String username, String AccountNumber);

    @NonNull
    Optional<Account> findFirstByAccountTypeOrderByDtCreatedDesc(
            @NonNull final AccountType accountType
    );

    @NonNull
    Optional<Account> findAccountByAccountNumber(@NonNull final String accountNum);

    @Query("""
            SELECT a FROM Account a
            WHERE 
            (a.dtBlocked < ?1 OR a.dtBlocked IS NULL) 
            AND (a.dtClosed < ?1 OR a.dtClosed IS NULL) 
            AND a.accountType = ?2
            """)
    List<Account> findAllActiveByDate(@NonNull final LocalDateTime currentDate, @NonNull final AccountType accountType);

}
