package com.solo.fishing;

public class Pond {
    private Fish[] fishes;

    public Pond() {
        this.fishes = new Fish[]{
        new Fish("피라미", 2, 300),
        new Fish("붕어", 4, 800),
        new Fish("잉어", 6, 1500),
        new Fish("메기", 8, 2500),
        new Fish("황금잉어", 10, 5000),
    };
    }

    public Fish randomFish() { // Math.random()으로 0 ~ fishes.length-1 중 하나를 골라 반환
        int ran = (int)(Math.random() * (fishes.length));
        return fishes[ran];
    }
    
}
