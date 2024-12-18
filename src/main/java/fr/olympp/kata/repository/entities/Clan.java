package fr.olympp.kata.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Table(name = "Clan")
@AllArgsConstructor
@Getter
@Builder
public class Clan implements Serializable {
    @Id
    @GeneratedValue(strategy = UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @OneToMany
    @Setter
    private List<Army> armies;
}
