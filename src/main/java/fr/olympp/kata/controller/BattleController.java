package fr.olympp.kata.controller;

import fr.olympp.kata.models.BattleReport;
import fr.olympp.kata.repository.entities.Clan;
import fr.olympp.kata.services.BattleService;
import fr.olympp.kata.services.ClanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/battles")
public class BattleController {
  private final BattleService battleService;
  private final ClanService clanServices;

  @GetMapping
  public BattleReport battle() {
    List<Clan> clans = clanServices.getClans();
    return battleService.battle(clans.getFirst(), clans.getLast());
  }
}
