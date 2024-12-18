package fr.olympp.kata.models;

import lombok.Value;

@Value
public class Round {
    String nameArmy1;
    String nameArmy2;
    int damageArmy1;
    int damageArmy2;
    int remainingSoldiersArmy1;
    int remainingSoldiersArmy2;
}
