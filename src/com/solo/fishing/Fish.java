package com.solo.fishing;

public class Fish {

    private String name;     // "잉어"
    private int strength;    // 힘 (1~10, 플레이어에게는 숨김)
    private int price;       // 가격
    
    public Fish(String name, int strength, int price) {
            this.name = name;
            this. strength = strength;
            this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public int getPrice() {
        return price;
    }

    public int getHintMin() { // strength - 3 (1보다 작으면 1)
       return ((getStrength() - 3) < 1) ? 1 : (getStrength() - 3);
    } 

    public int getHintMax() { // strength + 3 (10보다 크면 10)
       return ((getStrength() + 3) > 10) ? 10 : (getStrength() + 3);
    }  
}
