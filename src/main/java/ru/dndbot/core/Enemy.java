package ru.dndbot.core;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Enemy {
    public String name;
    private int hp;
    private int max_hp;
    private int armor;
    private int strength;
    private List<Item> lootTable;

    public Enemy(String name, int max_hp, int armor, int strength){
        this.name = name;
        this.max_hp = max_hp;
        this.hp = max_hp;
        this.armor = armor;
        this.strength = strength;
        this.lootTable = new ArrayList<>();
    }

    public void addLoot(Item item){
        lootTable.add(item);
    }

    public Item rollLoot(){
        if(lootTable.isEmpty()) return null;
        Random rand = new Random();
        return lootTable.get(rand.nextInt(lootTable.size()));
    }

    public int rollD20(){
        Random rand = new Random();
        return rand.nextInt(20) + 1;
    }


    public int calculateDamage(){
        Random rand = new Random();
        int baseDamage = 5;
        int randomBonus = rand.nextInt(3) + 1;

        return baseDamage + strength + randomBonus;
    }

    public String getName(){
        return name;
    }

    public int getHp(){
        return hp;
    }

    public int getMax_hp(){
        return max_hp;
    }

    public int getStrength(){
        return strength;
    }

    public int getArmor(){
        return this.armor;
    }

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
}
