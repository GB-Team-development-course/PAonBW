package ru.gb.core.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.core.entities.Client;
import ru.gb.core.repositories.client.ClientRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository repository;

    public Optional<Client> findByUsername(String username) {
        return repository.findByUsername(username);
    }

}