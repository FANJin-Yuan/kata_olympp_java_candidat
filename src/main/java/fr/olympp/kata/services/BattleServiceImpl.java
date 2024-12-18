package fr.olympp.kata.services;

import fr.olympp.kata.models.*;
import fr.olympp.kata.repository.entities.Army;
import fr.olympp.kata.repository.entities.Clan;
import fr.olympp.kata.repository.ClanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static fr.olympp.kata.models.Status.DRAW;
import static fr.olympp.kata.models.Status.WIN;
import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final ClanRepository clanRepository;

    @Override
    public BattleReport battle(Clan clan1, Clan clan2) {
        BattleReport.BattleReportBuilder reportBuilder = BattleReport.builder();
        if (clan1 == null || clan2 == null) {
            return reportBuilder.build();
        }
        reportBuilder.initialClans(asList(clan1, clan2));
        addHistory(reportBuilder, clan1, clan2);
        addStatusAndWinner(reportBuilder, clan1, clan2);

        clanRepository.saveClans(List.of(clan1, clan2));
        return reportBuilder.build();
    }

    private void addHistory(BattleReport.BattleReportBuilder builder, Clan clan1, Clan clan2) {
        List<Round> history = new ArrayList<>();
        while (!clan1.getArmies().isEmpty() && !clan2.getArmies().isEmpty()) {
            startRound(clan1, clan2, history);
        }
        builder.history(history);
    }

    private void startRound(Clan clan1, Clan clan2, List<Round> history) {
        Army army1 = clan1.getArmies().getFirst();
        Army army2 = clan2.getArmies().getFirst();

        int damageToArmy1 = army2.getArmyAttack() - army1.getArmyDefense();
        int damageToArmy2 = army1.getArmyAttack() - army2.getArmyDefense();

        int soldiersLostArmy1 = damageToArmy1 / army1.getRegiment().getHealth();
        if (soldiersLostArmy1 == 0) {
            army1.getRegiment().setHealth(army1.getRegiment().getHealth() - damageToArmy1);
        }
        int soldiersLostArmy2 = damageToArmy2 / army2.getRegiment().getHealth();
        if (soldiersLostArmy2 == 0) {
            army2.getRegiment().setHealth(army2.getRegiment().getHealth() - damageToArmy2);
        }

        int survivalSoldiersArmy1 = Math.max(0, army1.getArmySoldiersCount() - soldiersLostArmy1);
        int survivalSoldiersArmy2 = Math.max(0, army2.getArmySoldiersCount() - soldiersLostArmy2);

        if (survivalSoldiersArmy1 == 0) {
            clan1.getArmies().remove(army1);
        } else {
            army1.getRegiment().setSoldiersCount(survivalSoldiersArmy1);
        }

        if (survivalSoldiersArmy2 == 0) {
            clan2.getArmies().remove(army2);
        } else {
            army2.getRegiment().setSoldiersCount(survivalSoldiersArmy2);
        }

        history.add(
            new Round(
                army1.getName(),
                army2.getName(),
                damageToArmy1,
                damageToArmy2,
                survivalSoldiersArmy1,
                survivalSoldiersArmy2
        ));
    }

    private void addStatusAndWinner(BattleReport.BattleReportBuilder builder, Clan clan1, Clan clan2) {
        if (clan1.getArmies().isEmpty() && clan2.getArmies().isEmpty()) {
            builder.status(DRAW);
        } else if (!clan1.getArmies().isEmpty()) {
            builder.status(WIN);
            builder.winner(clan1.getName());
        } else {
            builder.status(WIN);
            builder.winner(clan2.getName());
        }
    }
}
