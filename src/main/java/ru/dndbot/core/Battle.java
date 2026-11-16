package ru.dndbot.core;

public class Battle {
    private Character ch;
    private Enemy en;
    private Inventory inventory;

    public Battle(Character ch, Inventory inventory, Enemy en) {
        this.ch = ch;
        this.inventory = inventory;
        this.en = en;
    }

    public Enemy getEnemy(){
        return en;
    }

    public String doRound() {
        StringBuilder log = new StringBuilder();

        int dicePlayer = ch.rollD20();
        int playerMod = ch.getStrength();
        int playerTotal = dicePlayer + playerMod;
        int enemyArmor = en.getArmor();

        if (playerTotal >= enemyArmor) {
            int damage = ch.calculateDamage();
            en.takeDamage(damage);
            log.append("Вам выпало: ").append(dicePlayer).append(" \n");
            log.append("Вы атаковали! ").append(en.getName()).append(" ").append("(Кубик ").append(dicePlayer).append(" + сила ").append(playerMod)
                    .append(" = ").append(playerTotal).append(")")
                    .append(" и нанесли ").append(damage).append(" урона. ")
                    .append("(У него осталось ").append(en.getHp()).append(" HP)\n");
        } else {
            log.append("Вам выпало: ").append(dicePlayer).append(" \n");
            log.append("Вы промахнулись по ").append(en.getName()).append("(Кубик ").append(dicePlayer).append(" + сила ").append(playerMod)
                    .append(" = ").append(playerTotal).append(".\n");
        }

        if (!en.isAlive()) {
            log.append("Вы победили! ");
            return log.toString();
        }
        log.append(enemyTurn());
        return log.toString();
    }

    public String enemyTurn(){
        StringBuilder log = new StringBuilder();
        int diceEnemy = en.rollD20();
        int enemyMod = en.getStrength();
        int enemyTotal = diceEnemy + enemyMod;
        int playerArmor = 10 + (ch.getTotalDexterity(inventory));

        if (enemyTotal >= playerArmor) {
            int enDamage = en.calculateDamage();
            ch.takeDamage(enDamage);

            log.append(en.getName()).append(" выбросил: ").append(diceEnemy).append("\n");
            log.append("Противник попал по вам! ").append("(Кубик ").append(diceEnemy).append(" + сила ").append(enemyMod)
                    .append(" = ").append(enemyTotal)
                    .append(" и нанес ").append(enDamage).append(" урона. ")
                    .append("(У вас осталось ").append(ch.getHp()).append(" HP)\n");
        } else {
            log.append(en.getName()).append(" выбросил: ").append(diceEnemy).append("\n");
            log.append(en.getName()).append(" промахнулся по вам.\n").append("(Кубик ").append(diceEnemy).append(" + сила ").append(enemyMod)
                    .append(" = ").append(enemyTotal).append(".\n");
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