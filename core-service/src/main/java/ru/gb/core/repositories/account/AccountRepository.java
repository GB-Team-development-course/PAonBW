package ru.gb.core.repositories.account;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;
import ru.gb.core.entities.Account;
import ru.gb.core.enums.AccountType;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("""
            SELECT a FROM Account a
            JOIN Client c ON c.id = a.client.id
            WHERE c.id = ?1
            """)
    List<Account> findAllByClientId(Long clientId);

    @Query("""
            SELECT a FROM Account a
            JOIN Client c ON c.id = a.client.id
            WHERE c.id = ?1 AND a.accountNumber = ?2
            """)
    Optional<Account> findByClientIdAccountByAccountNumber(Long clientId, String AccountNumber);

    @NonNull
    Optional<Account> findFirstByAccountTypeOrderByDtCreatedDesc(
            @NonNull final AccountType accountType
    );

    @NonNull
    Optional<Account> findAccountByAccountNumber(@NonNull final String accountNum);
}
