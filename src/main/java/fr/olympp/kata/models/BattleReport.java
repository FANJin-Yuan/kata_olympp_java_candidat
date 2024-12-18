package fr.olympp.kata.models;

import fr.olympp.kata.repository.entities.Clan;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class BattleReport {
    Status status;

    String winner;

    List<Clan> initialClans;

    List<Round> history;
}
