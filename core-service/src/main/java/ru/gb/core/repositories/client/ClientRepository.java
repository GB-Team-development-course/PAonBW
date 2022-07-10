package ru.gb.core.repositories.client;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.core.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}