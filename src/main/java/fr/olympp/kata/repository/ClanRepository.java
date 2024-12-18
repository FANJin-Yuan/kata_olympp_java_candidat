package fr.olympp.kata.repository;

import fr.olympp.kata.repository.entities.Clan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClanRepository {
    private final ClanJpaRepository clanJpaRepository;

    public Clan findClanByName(String clanName) {
        return clanJpaRepository.findByName(clanName);
    }

    public List<Clan> findClans() {
        return clanJpaRepository.findAll();
    }

    public void saveClan(Clan clan) {
        clanJpaRepository.save(clan);
    }

    public void saveClans(List<Clan> clans) {
        clanJpaRepository.saveAll(clans);
    }
}
