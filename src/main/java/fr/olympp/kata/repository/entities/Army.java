package fr.olympp.kata.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Table(name = "Army")
@AllArgsConstructor
@Getter
@Builder
public class Army implements Serializable {
    @Id
    @GeneratedValue(strategy = UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "clan_id")
    private Clan clan;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "regiment_id")
    private Regiment regiment;

    public int getArmyAttack() {
        if (regiment != null) {
            return regiment.getRegimentAttack();
        }
        return 0;
    }

    public Integer getArmyDefense() {
        if (regiment != null) {
            return regiment.getRegimentDefense();
        }
        return 0;
    }

    public Integer getArmySoldiersCount() {
        if (regiment != null) {
            return regiment.getSoldiersCount();
        }
        return 0;
    }
}
