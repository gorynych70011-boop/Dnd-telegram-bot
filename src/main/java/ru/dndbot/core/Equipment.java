package ru.dndbot.core;

import java.util.EnumMap;
import java.util.Map;


public class Equipment {
    private Map<EquipmentSlot, Item> slots;

    public Equipment(){
        this.slots = new EnumMap<>(EquipmentSlot.class);
    }

    public void equip(Item item){
        if (item == null || !item.isEquippable()) return;

        EquipmentSlot targetSlot = item.getSlot();

        Item oldItem = slots.get(targetSlot);
        if (oldItem != null){
            oldItem.setEquipped(false);
        }

        slots.put(targetSlot, item);
        item.setEquipped(true);
    }

    public Item getEquipped(){
        return  slots.get(EquipmentSlot.MAIN_HAND) != null
                ? slots.get(EquipmentSlot.MAIN_HAND)
                : slots.get(EquipmentSlot.ARMOR);
    }

    public int getBonusStr(){
        int total = 0;
        for(Item item : slots.values()){
            total += item.getBonusStr();
        }
        return total;
    }

    public int getBonusArmor(){
        int total = 0;
        for(Item item : slots.values()){
            total += item.getBonusArmor();
        }
        return total;
    }

    public int getBonusDex(){
        int total = 0;
        for(Item item : slots.values()){
            total += item.getBonusDex();
        }
        return total;
    }

    public int getBonusHp(){
        int total = 0;
        for(Item item : slots.values()){
            total += item.getBonusHp();
        }
        return total;
    }

    public Item unequip(EquipmentSlot slot){
        Item item = slots.remove(slot);
        if(item != null){
            item.setEquipped(false);
        }
        return item;
    }

    public Map<EquipmentSlot, Item> getAllEquipped(){
        return slots;
    }
}
