package com.solo.fishing;

public class Bucket {
    private Fish[] fishes = new Fish[5];  // 최대 5마리
    private int count;                    // 지금 몇 마리 들어 있는지 (= 다음에 넣을 칸 번호)

    public boolean isFull() { // count == fishes.length
        return (count == fishes.length);
    }   

    public boolean isEmpty() {  // count == 0
        return (count == 0);
    }   

    public void add(Fish fish) { // fishes[count]에 넣고 count 증가
        fishes[count++] = fish;
    }   

    public int getTotalPrice() { // 0 ~ count-1 칸의 가격 합
        int sum = 0;
        for (int i = 0; i < count; i++) {
            sum += fishes[i].getPrice();
        }
        return sum;
    }   

    public void show() { // 담긴 물고기 목록 출력
        for(int i = 0; i < count; i++) {
            System.out.println(fishes[i].getName());
        }
    }  

    public void empty() { // 비우기 (count를 0으로)
        count = 0;
    }  
            
    public int getCount() { // 상태 줄에 "3/5" 표시용
        return count;
    }        
}
