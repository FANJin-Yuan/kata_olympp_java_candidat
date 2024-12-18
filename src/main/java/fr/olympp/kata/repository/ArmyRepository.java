package fr.olympp.kata.repository;

import fr.olympp.kata.repository.entities.Army;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArmyRepository {
    private final ArmyJpaRepository armyJpaRepository;

    public Army findByClanIdAndArmyName(String clanId, String armyName) {
        return armyJpaRepository.findByClanIdAndName(clanId, armyName);
    }

    public void deleteArmy(Army army) {
        armyJpaRepository.delete(army);
    }
}
