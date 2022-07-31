package ru.gb.credit.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.credit.entity.Interest;
import ru.gb.credit.repositories.accrual.InterestRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class InterestService {
	private final InterestRepository interestRepository;

	public void saveAll(List<Interest> interests) {
		interestRepository.saveAll(interests);
	}
}
