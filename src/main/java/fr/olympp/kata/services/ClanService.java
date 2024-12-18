package fr.olympp.kata.services;

import fr.olympp.kata.repository.entities.Army;
import fr.olympp.kata.repository.entities.Clan;

import java.util.List;

public interface ClanService {
  Clan getClan(String clanName);

  List<Clan> getClans();

  void addArmy(String clanName, Army army);

  void removeArmy(String clanName, String armyName);
}
