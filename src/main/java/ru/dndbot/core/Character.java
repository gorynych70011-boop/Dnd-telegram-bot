package ru.dndbot.core;

public class Character{
    String name;
    String surname;
    String character_class;
    String race;
    String role;
    int strength;
    int dexterity;
    int constitution;
    int intelligence;
    int wisdom;
    int charisma;
    int hp;

    public Character(String name, String surname, String character_class, String race, String role,
int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma, int hp){
        this.name = name;
        this.surname = surname;
        this.character_class = character_class;
        this.race = race;
        this.role = role;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.hp = hp;

        if (race.equals("Elf")){
            this.dexterity += 1;
            this.intelligence += 1;
        }
        if (race.equals("Dwarf")){
            this.strength += 1;
            this.hp += 1;
        }
        if (race.equals("Human")){
            this.charisma += 1;
            this.hp += 1;
        }
        if (character_class.equals("Barbarian")){
            this.hp += 1;
            this.wisdom += 1;
        }
        if (character_class.equals("Fighter")){
            this.strength += 1;
            this.hp += 1;
        }
        if (character_class.equals("Sorcerer")){
            this.charisma += 1;
            this.dexterity +=1;
        }
    }
}