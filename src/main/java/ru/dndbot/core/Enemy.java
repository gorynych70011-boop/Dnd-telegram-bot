package ru.dndbot.core;

public class Enemy {
    public String name;
    private int hp;
    private int max_hp;

    public Enemy(int max_hp){
        this.max_hp = max_hp;
        this.hp = hp;
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
