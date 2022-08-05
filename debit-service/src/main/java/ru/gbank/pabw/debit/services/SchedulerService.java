package ru.gbank.pabw.debit.services;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Сервис вызываемый для операций по расписанию с дебетовыми счетами
 */
@Service
@EnableScheduling
@AllArgsConstructor
public class SchedulerService {

    private final DebitOperationService debitOperationService;

    @Scheduled(cron = "${cron.expression}")
    public void startTransactions() {
        LocalDate currentDate = LocalDate.now();

        debitOperationService.calculateDailyAccruals(currentDate);
    }
}
