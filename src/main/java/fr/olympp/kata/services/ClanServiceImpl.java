package fr.olympp.kata.services;

import fr.olympp.kata.repository.entities.Army;
import fr.olympp.kata.repository.entities.Clan;
import fr.olympp.kata.repository.ArmyRepository;
import fr.olympp.kata.repository.ClanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClanServiceImpl implements ClanService {
    private final ClanRepository clanRepository;
    private final ArmyRepository armyRepository;

    @Override
    public Clan getClan(String clanName) {
        return clanRepository.findClanByName(clanName);
    }

    @Override
    public List<Clan> getClans() {
        return clanRepository.findClans();
    }

    @Override
    public void addArmy(String clanName, Army army) {
        Clan clan = clanRepository.findClanByName(clanName);
        List<Army> armies = clan.getArmies();
        armies.add(army);
        clan.setArmies(armies);
        clanRepository.saveClan(clan);
    }

    @Override
    public void removeArmy(String clanName, String armyName) {
        Clan clan = clanRepository.findClanByName(clanName);
        Army army = armyRepository.findByClanIdAndArmyName(clan.getId(), armyName);
        List<Army> armies = clan.getArmies();
        armies.remove(army);
        clan.setArmies(armies);
        clanRepository.saveClan(clan);
    }
}
