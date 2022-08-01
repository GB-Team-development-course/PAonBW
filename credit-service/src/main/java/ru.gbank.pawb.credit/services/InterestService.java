package ru.gbank.pawb.credit.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gbank.pawb.credit.entity.Interest;
import ru.gbank.pawb.credit.repositories.accrual.InterestRepository;


import java.util.List;

@Service
@AllArgsConstructor
public class InterestService {
	private final InterestRepository interestRepository;

	public void saveAll(List<Interest> interests) {
		interestRepository.saveAll(interests);
	}
}
