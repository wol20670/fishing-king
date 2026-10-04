package com.solo.fishing;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Fisher fisher = new Fisher();

        while(true) {
            System.out.println("============== 🎣 낚시왕 ==============");
            fisher.showStatus();
            System.out.println("=======================================");
            System.out.println("1. 찌 던지기");
            System.out.println("2. 양동이 보기");
            System.out.println("3. 시장에서 팔기");
            System.out.println("4. 낚싯대 강화");
            System.out.println("9. 프로그램 종료");
            System.out.println("=======================================");
            
            int menu = sc.nextInt();

            switch(menu) {
                case 1: {
                    if(fisher.cast()) {
                        System.out.print("릴을 몇 번 감을까? (1~10) : ");
                        int reel_count = sc.nextInt();
                        fisher.reel(reel_count);
                    }
                }
                
                case 2: {
                    fisher.showBucket();
                }

                case 3: {
                    fisher.sellAll();
                    if(fisher.isFishingKing()) {
                        System.out.println("🏆 축하합니다! 소지금 10,000원 달성! 당신은 이제 낚시왕입니다!");
                        break;
                    }
                }

                case 4: {
                    fisher.upgradeRod();
                }

                case 9: {
                    System.out.println("게임을 종료합니다.");
                    break;
                }
            }
        }
       
    }
}
