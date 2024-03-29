package ru.gbank.pabw.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.Nullable;
import ru.gbank.pabw.model.enums.AccountType;
import ru.gbank.pabw.model.enums.AccountStatus;
import ru.gbank.pabw.model.enums.Currency;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    /**
     * Уникальный ID счета на уровне системы. Используется как ключ для других сущностей системы.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_gen")
    @SequenceGenerator(name = "accounts_gen", sequenceName = "accounts_id_seq", allocationSize = 1)
    private Long id;

    /**
     * Уникальный ID клиента
     */
    @NonNull
    @Column(name = "username")
    private String username;

    /**
     * Тип счета:
     * <p>
     * С - кредитный счет
     * D - дебетовый счет
     * T - технический счёт
     */
    @NonNull
    @Column(name = "account_type_id")
    private AccountType accountType;

    /**
     * Номер счета.
     */
    @NonNull
    @Column(name = "account_number")
    private String accountNumber;

    /**
     * Продукт
     */
    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    /**
     * Статус счёта.
     */
    @NonNull
    @Column(name = "account_status_id")
    private AccountStatus accountStatus;

    /**
     * Валюта счета
     */
    @NonNull
    @Column(name = "currency_id")
    private Currency currency;

    /**
     * Дата и время открытия счета
     */
    @NonNull
    @Column(name = "dt_created")
    private LocalDateTime dtCreated;

    /**
     * Дата и время блокировки счета
     */
    @Nullable
    @Column(name = "dt_blocked")
    private LocalDateTime dtBlocked;

    /**
     * Дата и время закрытия счета
     */
    @Nullable
    @Column(name = "dt_closed")
    private LocalDateTime dtClosed;

    /**
     * Дата и время последнего обновления информации по счету
     */
    @Nullable
    @Column(name = "dt_last_update")
    private LocalDateTime dtLastUpdate;

}
