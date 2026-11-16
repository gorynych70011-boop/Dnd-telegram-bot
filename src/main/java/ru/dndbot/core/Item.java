package ru.dndbot.core;

public class Item {
    private String name;
    private String rarity;

    private int bonusStr;
    private int bonusDex;
    private int bonusHp;

    public Item(String name, String rarity, int bonusStr, int bonusDex, int bonusHp){
        this.name = name;
        this.rarity = rarity;
        this.bonusStr = bonusStr;
        this.bonusDex = bonusDex;
        this.bonusHp = bonusHp;
    }

    public String getName(){return name; }
    public String getRarity(){return rarity; }
    public int getBonusStr(){return bonusStr; }
    public int getBonusDex(){return bonusDex; }
    public int getBonusHp(){return bonusHp; }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder( "[" + rarity + "]" + name);
        if (bonusStr != 0) sb.append(" (STR +").append(bonusStr).append(")");
        if (bonusDex != 0) sb.append(" (DEX +").append(bonusDex).append(")");
        if (bonusHp != 0) sb.append(" (HP +").append(bonusHp).append(")");
        return sb.toString();
    }
}
