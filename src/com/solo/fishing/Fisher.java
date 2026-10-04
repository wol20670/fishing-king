package com.solo.fishing;

public class Fisher {
    private int money;                      // 소지금 (처음엔 0)
    private Rod rod = new Rod();            // 낚싯대
    private Bucket bucket = new Bucket();   // 양동이
    private Pond pond = new Pond();         // 연못
    private Fish hookedFish;                // 지금 걸려 있는 물고기 (입질이 없으면 null)

    public void showStatus() {
        System.out.printf("소지금: %d원 | 낚싯대 Lv.%d | 양동이 %d/5\n", money,
        rod.getLevel(), bucket.getCount());
        
    }

    public boolean cast() {
        if(bucket.isFull()) {
            System.out.println("양동이가 가득 찼습니다.");
            return false;
        } else {
            System.out.println("... 찌가 흔들린다 ...");
            hookedFish = pond.randomFish();
            System.out.printf("!!! 입질이다 !!! (힘 %d ~ %d 사이)\n\n", hookedFish.getHintMin(), hookedFish.getHintMax());
            return true;
        }
    }

    public void reel(int input) {
        if(hookedFish != null) {
            int diff = Math.abs(hookedFish.getStrength() - input);
            if(diff <= rod.getTolerance()) {
                bucket.add(hookedFish);
                System.out.printf("\n🎉 %s를 낚았다! 양동이에 담았어요 (%d/5)\n\n",
                hookedFish.getName(), bucket.getCount());
                hookedFish = null;
            } else {
                System.out.println("실패");
                hookedFish = null;
            }
        } else {
            System.out.println("입질 없음.");
        }
    }

    public void showBucket() {
        bucket.show();
    }

    public void sellAll() {
        if(bucket.isEmpty()) {
            System.out.println("양동이가 비어있습니다.");
        } else {
            int sell = bucket.getTotalPrice();
            System.out.printf("🐟 물고기 %d마리를 %d원에 팔았어요!", bucket.getCount(), sell);
            money += sell;
            bucket.empty();
        }
    }

    public void upgradeRod() {
        if(rod.isMaxLevel()) {
            System.out.println("이미 최고 레벨입니다.");
        }
            else {
                int cost = rod.getUpgradeCost();
                if (money >= cost) {
                    money -= cost;
                    rod.upgrade();
                } else {
                    System.out.printf("%d원이 부족합니다.", cost - money);
                }
            }
    }

    public boolean isFishingKing() {
        return (money >= 10000);
    }   

}
