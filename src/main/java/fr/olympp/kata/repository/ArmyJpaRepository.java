package fr.olympp.kata.repository;

import fr.olympp.kata.repository.entities.Army;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmyJpaRepository extends JpaRepository<Army, String> {
    Army findByClanIdAndName(String cleanId, String armyName);
}
