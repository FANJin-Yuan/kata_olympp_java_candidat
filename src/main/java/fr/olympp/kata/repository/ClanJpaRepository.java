package fr.olympp.kata.repository;

import fr.olympp.kata.repository.entities.Clan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClanJpaRepository extends JpaRepository<Clan, String> {
    Clan findByName(String name);
}
