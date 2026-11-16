package ru.dndbot.core;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;

    public Inventory(){
        this.items = new ArrayList<>();
    }

    public void addItem(Item item){
        items.add(item);
    }

    public List<Item> getItems(){
        return items;
    }
    public boolean isEmpty(){
        return items.isEmpty();
    }

    public boolean removeItem(String name){
        for (int i = 0; i < items.size(); i++){
            if(items.get(i).getName().equalsIgnoreCase(name)){
                items.remove(i);
                return true;
            }
        }
        return false;
    }
}
