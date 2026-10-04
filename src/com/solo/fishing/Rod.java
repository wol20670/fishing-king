package com.solo.fishing;

public class Rod {
    private int level;   // 처음엔 1

    public Rod() { // level을 1로 시작
        this.level = 1;
    }
                     
    public int getLevel() {
        return level;
    }

    public int getTolerance() { // 허용 오차 (레벨과 같음 : Lv.1 → 1)
        return level;
    }  

    public int getUpgradeCost() { // 다음 레벨로 가는 비용
            if (level == 1) {
                return 2000;
            } else if (level == 2) {
                return 5000;
            } else {
                return 0;
            }
    }   

    public boolean isMaxLevel() { // level이 3이면 true
        return (level == 3) ? true : false;
    } 
 
    public void upgrade() {  // level 1 증가
        level++;
    }        
}
