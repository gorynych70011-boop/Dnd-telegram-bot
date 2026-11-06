package ru.dndbot.core;
import java.util.Random;

public class Enemy {
    public String name;
    private int hp;
    private int max_hp;
    private int armor;
    private int strength;

    public Enemy(String name, int max_hp, int armor, int strength){
        this.name = name;
        this.max_hp = max_hp;
        this.hp = max_hp;
        this.armor = armor;
        this.strength = strength;
    }

    public int rollAttack(){
        Random rand = new Random();
        int d20 = rand.nextInt(20) + 1;

        int modifier = this.strength;

        return d20 + modifier;
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
