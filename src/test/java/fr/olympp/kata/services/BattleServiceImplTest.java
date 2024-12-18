package fr.olympp.kata.services;

import fr.olympp.kata.models.BattleReport;
import fr.olympp.kata.models.Round;
import fr.olympp.kata.repository.ClanRepository;
import fr.olympp.kata.repository.entities.Army;
import fr.olympp.kata.repository.entities.Clan;
import fr.olympp.kata.repository.entities.Regiment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static fr.olympp.kata.models.Status.DRAW;
import static fr.olympp.kata.models.Status.WIN;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BattleServiceImplTest {
    @InjectMocks
    private BattleServiceImpl battleService;

    @Mock
    private ClanRepository clanRepository;

    @Test
    void shouldReturnDraw() {
        // GIVEN
        Army army11 = Army.builder()
                .id("army11-id")
                .name("army11")
                .regiment(new Regiment("regiment11-id", 100, 1, 5, 0))
                .build();
        Army army12 = Army.builder()
                .id("army12-id")
                .name("army12")
                .regiment(new Regiment("regiment12-id", 200, 1, 6, 0))
                .build();
        List<Army> armies1 = new ArrayList<>();
        armies1.add(army11);
        armies1.add(army12);
        Clan clan1 = Clan.builder()
                .id("clan1-id")
                .name("clan1")
                .armies(armies1)
                .build();

        Army army21 = Army.builder()
                .id("army21-id")
                .name("army21")
                .regiment(new Regiment("regiment21-id", 100, 1, 5, 0))
                .build();
        Army army22 = Army.builder()
                .id("army22-id")
                .name("army22")
                .regiment(new Regiment("regiment22-id", 200, 1, 6, 0))
                .build();
        List<Army> armies2 = new ArrayList<>();
        armies2.add(army21);
        armies2.add(army22);
        Clan clan2 = Clan.builder()
                .id("clan2-id")
                .name("clan2")
                .armies(armies2)
                .build();

        // WHEN
        BattleReport report = battleService.battle(clan1, clan2);

        // THEN
        assertThat(report.getStatus()).isEqualTo(DRAW);
        assertThat(report.getWinner()).isNull();
        assertThat(report.getInitialClans().get(0)).isEqualTo(clan1);
        assertThat(report.getInitialClans().get(1)).isEqualTo(clan2);

        assertThat(report.getHistory()).hasSize(2)
            .extracting(
                Round::getNameArmy1,
                Round::getNameArmy2,
                Round::getDamageArmy1,
                Round::getDamageArmy2,
                Round::getRemainingSoldiersArmy1,
                Round::getRemainingSoldiersArmy2
            ).containsExactly(
                tuple("army11", "army21", 500, 500, 0, 0),
                tuple("army12", "army22", 1200, 1200, 0, 0)
            );

        verify(clanRepository).saveClans(asList(clan1, clan2));
    }

    @Test
    void shouldReturnWin() {
        // GIVEN
        Army army11 = Army.builder()
                .id("army11-id")
                .name("army11")
                .regiment(new Regiment("regiment11-id", 100, 1, 10, 0))
                .build();
        Army army12 = Army.builder()
                .id("army12-id")
                .name("army12")
                .regiment(new Regiment("regiment12-id", 200, 1, 10, 0))
                .build();
        List<Army> armies1 = new ArrayList<>();
        armies1.add(army11);
        armies1.add(army12);
        Clan clan1 = Clan.builder()
                .id("clan1-id")
                .name("clan1")
                .armies(armies1)
                .build();

        Army army21 = Army.builder()
                .id("army21-id")
                .name("army21")
                .regiment(new Regiment("regiment21-id", 1, 1, 5, 0))
                .build();
        Army army22 = Army.builder()
                .id("army22-id")
                .name("army22")
                .regiment(new Regiment("regiment22-id", 2, 1, 5, 0))
                .build();
        List<Army> armies2 = new ArrayList<>();
        armies2.add(army21);
        armies2.add(army22);
        Clan clan2 = Clan.builder()
                .id("clan2-id")
                .name("clan2")
                .armies(armies2)
                .build();

        // WHEN
        BattleReport report = battleService.battle(clan1, clan2);

        // THEN
        assertThat(report.getStatus()).isEqualTo(WIN);
        assertThat(report.getWinner()).isEqualTo("clan1");
        assertThat(report.getInitialClans().get(0)).isEqualTo(clan1);
        assertThat(report.getInitialClans().get(1)).isEqualTo(clan2);

        assertThat(report.getHistory()).hasSize(2)
                .extracting(
                        Round::getNameArmy1,
                        Round::getNameArmy2,
                        Round::getDamageArmy1,
                        Round::getDamageArmy2,
                        Round::getRemainingSoldiersArmy1,
                        Round::getRemainingSoldiersArmy2
                ).containsExactly(
                        tuple("army11", "army21", 5, 1000, 95, 0),
                        tuple("army11", "army22", 10, 950, 85, 0)
                );

        verify(clanRepository).saveClans(asList(clan1, clan2));
    }
}
