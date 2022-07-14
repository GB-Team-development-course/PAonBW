package ru.gb.core.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @SequenceGenerator(name = "client_gen", sequenceName = "clients_id_seq", allocationSize = 1)
    private Long id;

    /**
     * Уникальный логин клиента
     */
    @NonNull
    @Column(name = "username")
    private String username;

}
