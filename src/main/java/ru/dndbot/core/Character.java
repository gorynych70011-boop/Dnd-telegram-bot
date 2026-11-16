package ru.dndbot.core;
import java.util.Random;



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
    int armor;
    private Inventory inventory;

    public Character(String name, String surname, String character_class, String race, String role,
int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma, int hp, int armor){
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
        this.armor = armor;

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

    public void heal(int amount){
        this.hp += amount;
        if(this.hp > 100){
            this.hp = 100;
        }
    }

    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }

    public int rollD20(){
        Random rand = new Random();
        return rand.nextInt(20) + 1;
    }

    public int calculateDamage(){
        Random rand = new Random();
        int baseDamage = 5;
        int randomBonus = rand.nextInt(3) + 1;


        return baseDamage + getStrength() + randomBonus;
    }

    public String getName() { return name; }
    public String getSurname(){return surname; }

    public int getStrength() {
        int totalStrength = this.strength;

        if(this.inventory != null){
            for (Item item : this.inventory.getItems()){
                totalStrength += item.getBonusStr();
            }
        }
        return totalStrength;
    }

    public int getDexterity() { return dexterity; }
    public int getConstitution() { return constitution; }
    public int getIntelligence() { return intelligence; }
    public int getWisdom() { return wisdom; }
    public int getCharisma() { return charisma; }
    public int getHp() {return hp; }
    public int getArmor() {return armor; }
    public boolean isAlive(){
        return this.hp > 0;
    }

    public void takeDamage(int damage){
        if (damage <= 0){
            return;
        }
        this.hp -= damage;

        if (this.hp < 0){
            this.hp = 0;
        }
    }

    public int getTotalStrength(Inventory inventory){
        int total = this.strength;
        if (inventory != null){
            for (Item item : inventory.getItems()){
                total += item.getBonusStr();
            }
        }
        return total;
    }

    public int getTotalDexterity(Inventory inventory){
        int total = this.dexterity;
        if (inventory != null){
            for (Item item : inventory.getItems()){
                total += item.getBonusDex();
            }
        }
        return total;
    }

    public int getTotalHp(Inventory inventory){
        int total = this.hp;
        if (inventory != null){
            for (Item item : inventory.getItems()){
                total += item.getBonusHp();
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}