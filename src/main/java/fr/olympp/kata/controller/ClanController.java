package fr.olympp.kata.controller;

import fr.olympp.kata.repository.entities.Army;
import fr.olympp.kata.repository.entities.Clan;
import fr.olympp.kata.services.ClanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clans")
public class ClanController {
  private final ClanService clanService;

  @GetMapping
  public List<Clan> getClans() {
    return clanService.getClans();
  }

  @GetMapping("/{clanName}")
  public Clan getClan(@PathVariable String clanName) {
    return clanService.getClan(clanName);
  }

  @PostMapping("/{clanName}/armies")
  public void addArmy(@PathVariable String clanName, @RequestBody Army army) {
    clanService.addArmy(clanName, army);
  }

  @DeleteMapping("/{clanName}/armies/{armyName}")
  public void removeArmy(@PathVariable String clanName, @PathVariable String armyName) {
    clanService.removeArmy(clanName, armyName);
  }

}
