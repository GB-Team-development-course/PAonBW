package ru.gbank.pawb.credit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.Nullable;
import ru.gbank.pabw.model.enums.Currency;
import ru.gbank.pabw.model.enums.InterestStatus;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "interests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interest {

	/**
	 * Уникальный идентификатор начисления
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interest_gen")
	@SequenceGenerator(name = "interest_gen", sequenceName = "interests_id_seq", allocationSize = 1)
	private Long id;

	/**
	 * Имя клиента - получателя
	 */
	@NonNull
	@Column(name = "username")
	private String username;

	/**
	 * Номер счета начисления
	 */
	@NonNull
	@Column(name = "target_account")
	private String targetAccount;

	/**
	 * Сумма начисления
	 */
	@NonNull
	@Column(name = "amount")
	private BigDecimal amount;

	/**
	 * 3-х значный код валюты начисления
	 */
	@NonNull
	@Column(name = "currency_id")
	private Currency currency;

	/**
	 * Статус начисления
	 */
	@NonNull
	@Column(name = "interest_status_id")
	private InterestStatus interestStatus;

	/**
	 * Дата/время начала выполнения начисления
	 */
	@NonNull
	@Column(name = "execution_start")
	private LocalDateTime executionStart;

	/**
	 * Дата/время окончания выполнения начисления
	 */
	@Nullable
	@Column(name = "execution_end")
	private LocalDateTime executionEnd;
}
