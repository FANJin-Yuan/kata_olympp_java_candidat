package fr.olympp.kata.services;

import fr.olympp.kata.repository.ArmyRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClanServiceImplTest {
    @InjectMocks
    private ClanServiceImpl service;

    @Mock
    private ClanRepository clanRepository;

    @Mock
    private ArmyRepository armyRepository;

    @Test
    void shouldGetClan() {
        // GIVEN
        String clanName = "grec";

        // WHEN
        service.getClan(clanName);

        // THEN
        verify(clanRepository).findClanByName(clanName);
    }

    @Test
    void shouldGetClans() {
        // GIVEN - WHEN
        service.getClans();

        // THEN
        verify(clanRepository).findClans();
    }

    @Test
    void shouldAddArmy() {
        // GIVEN
        Army army = Army.builder()
            .name("army1")
            .regiment(Regiment.builder()
                .soldiersCount(100)
                .health(10)
                .attack(5)
                .defense(8)
                .build()
            )
            .build();

        Clan clan = Clan.builder()
            .name("troyen")
            .armies(new ArrayList<>())
            .build();
        when(clanRepository.findClanByName("troyen")).thenReturn(clan);

        // WHEN
        service.addArmy("troyen", army);

        // THEN
        assertThat(clan.getArmies()).hasSize(1)
            .containsExactly(army);
        verify(clanRepository).saveClan(clan);
    }

    @Test
    void shouldRemoveArmy() {
        // GIVEN
        Army army = Army.builder()
            .id("army2-id")
            .name("army2")
            .build();
        List<Army> armies = new ArrayList<>();
        armies.add(army);

        Clan clan = Clan.builder()
            .id("troyen-id")
            .name("troyen")
            .armies(armies)
            .build();
        when(clanRepository.findClanByName("troyen")).thenReturn(clan);
        when(armyRepository.findByClanIdAndArmyName("troyen-id", "army2")).thenReturn(army);

        // WHEN
        service.removeArmy("troyen", "army2");

        // THEN
        verify(clanRepository).saveClan(clan);
        assertThat(clan.getArmies()).isEmpty();
    }
}
