package com.solo.fishing;

public class Fisher {
    private int money;                      // 소지금 (처음엔 0)
    private Rod rod = new Rod();            // 낚싯대
    private Bucket bucket = new Bucket();   // 양동이
    private Pond pond = new Pond();         // 연못
    private Fish hookedFish;                // 지금 걸려 있는 물고기 (입질이 없으면 null)

    public void showStatus() {
        System.out.println("현재 돈은 " + money + "원, " + "낚싯대 레벨은 " + rod.getLevel() +
        "양동이 칸 수는 " + bucket.getCount() + "이다.");
    }

    public boolean cast() {
        (hookedFish.getHintMax()) ? 
    }

    public void reel(int input) {

    }

    public void showBucket() {

    }

    public void sellAll() {

    }

    public void upgradeRod() {

    }

    public boolean isFishingKing() {

    }

}
