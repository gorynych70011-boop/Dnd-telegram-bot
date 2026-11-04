package ru.dndbot.core;

public class Battle {
    private Character ch;
    private Enemy en;

    public Battle(Character ch, Enemy en) {
        this.ch = ch;
        this.en = en;
    }

    public boolean fight() {
        while (ch.isAlive()) {
            int attackRoll = ch.RollAttack();

            if (attackRoll >= en.getArmor()){
                int damage = ch.calculateDamage();
                en.takeDamage(damage);
            }else{
                //promah
            }
            if (!en.isAlive()){
                return true;
            }

            int enAttackRoll = en.rollAttack();

            if (enAttackRoll >= ch.getArmor()){
                int enDamage = en.calculateDamage();
                ch.takeDamage(enDamage);
            }else{
             // promah
            }
            if(!ch.isAlive()){
                return false;
            }
        }
        return false;
    }
}
