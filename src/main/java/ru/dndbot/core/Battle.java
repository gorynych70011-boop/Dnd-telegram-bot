package ru.dndbot.core;

public class Battle {
    private Character ch;
    private Enemy en;

    public Battle(Character ch, Enemy en) {
        this.ch = ch;
        this.en = en;
    }

    public String doRound() {
        StringBuilder log = new StringBuilder();

        // --- Ход персонажа ---
        int attackRoll = ch.RollAttack();
        if (attackRoll >= en.getArmor()) {
            int damage = ch.calculateDamage();
            en.takeDamage(damage);
            log.append("Вы атаковали ").append(en.getName())
                    .append(" и нанесли ").append(damage).append(" урона. ")
                    .append("(У него осталось ").append(en.getHp()).append(" HP)\n");
        } else {
            log.append("Вы промахнулись по ").append(en.getName()).append(".\n");
        }

        if (!en.isAlive()) {
            log.append("Вы победили! ");
            return log.toString();
        }

        // --- Ход врага ---
        int enAttackRoll = en.rollAttack();
        if (enAttackRoll >= ch.getArmor()) {
            int enDamage = en.calculateDamage();
            ch.takeDamage(enDamage);
            log.append(en.getName()).append(" ударил вас и нанес ")
                    .append(enDamage).append(" урона. ")
                    .append("(У вас осталось ").append(ch.getHp()).append(" HP)\n");
        } else {
            log.append(en.getName()).append(" промахнулся по вам.\n");
        }

        if (!ch.isAlive()) {
            log.append("Вы погибли.");
        }

        return log.toString();
    }

    public boolean isOver() {
        return !ch.isAlive() || !en.isAlive();
    }
}