package ru.dndbot.core;

public class Item {
    private String name;
    private String rarity;

    private int bonusStr;
    private int bonusDex;
    private int bonusHp;
    private int bonusArmor;

    private boolean equippable;
    private boolean isEquipped;
    private boolean usableInBattle;

    private EquipmentSlot slot;
    //для мусора
    public Item(String name, String rarity){
        this(name, rarity, 0, 0, 0, 0, false, EquipmentSlot.ARMOR, false);
    }
    //Для не надевается
    public Item(String name, String rarity, int bonusStr, int bonusDex, int bonusHp, boolean usableInBattle){
        this(name, rarity, bonusStr, bonusDex, bonusHp, 0, false, null, usableInBattle);
    }

    public Item(String name, String rarity, int bonusStr, int bonusDex, int bonusHp, int bonusArmor,
                boolean equippable, EquipmentSlot slot){
        this(name, rarity, bonusStr, bonusDex, bonusHp, bonusArmor, equippable, slot, false);
    }

    public Item(String name, String rarity, int bonusStr, int bonusDex, int bonusHp, int bonusArmor, boolean equippable, EquipmentSlot slot, boolean usableInBattle){
        this.name = name;
        this.rarity = rarity;
        this.bonusStr = bonusStr;
        this.bonusDex = bonusDex;
        this.bonusHp = bonusHp;
        this.bonusArmor = bonusArmor;
        this.equippable = equippable;
        this.slot = slot;
        this.usableInBattle = usableInBattle;
        this.isEquipped = false;


    }

    public String getName(){return name; }
    public String getRarity(){return rarity; }
    public int getBonusStr(){return bonusStr; }
    public int getBonusDex(){return bonusDex; }
    public int getBonusHp(){return bonusHp; }
    public int getBonusArmor(){return bonusArmor; }
    public boolean isEquippable(){return equippable; }
    public void setEquipped(boolean equipped) {isEquipped = equipped; }

    public EquipmentSlot getSlot(){
        return slot;
    }

    public boolean isUsableInBattle(){return usableInBattle; }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder( "[" + rarity + "]" + name);
        if (bonusStr != 0) sb.append(" (STR +").append(bonusStr).append(")");
        if (bonusDex != 0) sb.append(" (DEX +").append(bonusDex).append(")");
        if (bonusHp != 0) sb.append(" (HP +").append(bonusHp).append(")");
        if (bonusArmor != 0) sb.append("(AC +").append(bonusArmor).append(")");

        if(equippable){
            if(isEquipped){
                sb.append(" [НАДЕТО] ");
            }else{
                sb.append(" [в инвентаре ]");
            }
        }

        if(usableInBattle && !equippable){
            sb.append(" [можно в бою]");
        }

        return sb.toString();
    }
}
