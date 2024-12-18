package fr.olympp.kata.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Table(name = "Regiment")
@AllArgsConstructor
@Getter
@Builder
public class Regiment implements Serializable {
    @Id
    @GeneratedValue(strategy = UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "soldiers_count")
    @Setter
    private int soldiersCount;

    @Column(name = "health")
    @Setter
    private int health;

    @Column(name = "attack")
    private int attack;

    @Column(name = "defense")
    private int defense;

    public int getRegimentAttack() {
        return soldiersCount * attack;
    }

    public int getRegimentDefense() {
        return soldiersCount * defense;
    }
}
