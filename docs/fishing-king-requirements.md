# 🎣 낚시왕 콘솔 낚시 게임 - 요구사항 & 클래스 설계

> 자동차 예제(`d_abstraction/run`)와 같은 흐름으로 설계했습니다.
> **요구사항 → 클래스 후보 → 메시지 → 상태(데이터)** 순서입니다.
> 상속 없이 **클래스, 생성자, 캡슐화, 배열, Scanner, Math.random()**만으로 구현할 수 있습니다.

- 패키지 : `com.solo.fishing`
- 1인 개발 / 최소 규모 (클래스 6개 : `Application`, `Fisher`, `Rod`, `Bucket`, `Pond`, `Fish`)

---

## 1. 프로그램 요구사항

**주제 : 낚시꾼이 물고기를 낚아 팔고, 낚싯대를 강화해서 낚시왕이 되는 게임**

1. 낚시꾼은 처음에 **소지금 0원, 낚싯대 Lv.1, 빈 양동이(최대 5마리)**를 가지고 시작한다.
2. 낚시꾼은 찌를 던질 수 있다. 양동이가 가득 찼으면 찌를 던질 수 없다.
3. 찌를 던지면 연못이 사는 물고기 중 하나를 **랜덤으로** 골라 입질이 온다. 이때 물고기의 **힘 범위 힌트**(힘 ±3)를 보여준다.
4. 입질이 오면 낚시꾼은 릴을 몇 번 감을지 1~10 중에서 입력한다.
   - `|물고기의 힘 - 입력값|`이 **낚싯대의 허용 오차 이내**면 성공 → 물고기를 양동이에 담는다.
   - 허용 오차를 넘으면 실패 → "물고기가 도망갔다..."
   - 입질이 오지 않았는데 릴을 감을 수는 없다.
5. 낚시꾼은 시장에서 양동이의 물고기를 전부 팔 수 있다. 양동이가 비어 있으면 팔 수 없다. 팔면 총 가격만큼 소지금이 늘고 양동이는 비워진다.
6. 낚시꾼은 돈을 내고 낚싯대를 강화할 수 있다. 돈이 부족하거나 이미 최고 레벨(Lv.3)이면 강화할 수 없다. 강화하면 허용 오차가 넓어진다.
7. 소지금이 **10,000원 이상**이 되면 🏆 "낚시왕" 칭호를 주고 게임을 끝낸다.

---

## 2. 클래스 후보 도출

> "은/는, 이/가" 앞 단어가 대부분 클래스 후보이다.

뽑은 명사 : **낚시꾼, 낚싯대, 양동이, 연못, 물고기**

```
Application ──> Fisher ──┬──> Rod
                         ├──> Bucket ──> Fish[5]   (잡은 물고기)
                         └──> Pond   ──> Fish[5]   (연못에 사는 물고기 후보)
```

- **Application은 Fisher에만 메시지를 보낸다.** (Application → CarRacer 구조와 동일)
- 나머지 객체는 Fisher가 `private` 필드로 가진다. → **캡슐화**
- "소지금", "힘", "가격"은 명사지만 혼자서 하는 행동이 없으므로 클래스가 아니라 **필드(상태)**가 된다.

---

## 3. 클래스별 메시지 & 상태

### 🧑 Fisher (낚시꾼) - CarRacer 역할

**수신할 수 있는 메시지 (Application → Fisher)**

| 메시지 | 메서드 |
|---|---|
| 지금 상태를 보여줘라 | `showStatus()` (소지금, 낚싯대 레벨, 양동이 칸 수 출력) |
| 찌를 던져라 | `boolean cast()` (입질이 오면 힌트 출력 후 true, 양동이가 가득이면 안내 후 false) |
| 릴을 감아라 | `reel(int input)` (성공/실패 판정 후 걸린 물고기를 비운다) |
| 양동이를 보여줘라 | `showBucket()` |
| 물고기를 전부 팔아라 | `sellAll()` |
| 낚싯대를 강화해라 | `upgradeRod()` |
| 낚시왕이 되었니? | `boolean isFishingKing()` (소지금 10,000원 이상이면 true) |

**상태 후보**

```java
private int money;                      // 소지금 (처음엔 0)
private Rod rod = new Rod();            // 낚싯대
private Bucket bucket = new Bucket();   // 양동이
private Pond pond = new Pond();         // 연못
private Fish hookedFish;                // 지금 걸려 있는 물고기 (입질이 없으면 null)
```

> `hookedFish`가 `null`인지 아닌지로 "입질이 왔는지"를 판단한다.
> `cast()`에서 채우고, `reel()`에서 성공하든 실패하든 다시 `null`로 비운다.

---

### 🎣 Rod (낚싯대)

**수신할 수 있는 메시지** : 허용 오차 알려줘, 강화 비용 알려줘, 최고 레벨이니?, 강화해라

```java
private int level;   // 처음엔 1

public Rod()                   // level을 1로 시작
public int getLevel()
public int getTolerance()      // 허용 오차 (레벨과 같음 : Lv.1 → 1)
public int getUpgradeCost()    // 다음 레벨로 가는 비용
public boolean isMaxLevel()    // level이 3이면 true
public void upgrade()          // level 1 증가
```

---

### 🪣 Bucket (양동이)

**수신할 수 있는 메시지** : 가득 찼니?, 비었니?, 물고기 담아라, 총 가격 알려줘, 안을 보여줘라, 비워라

```java
private Fish[] fishes = new Fish[5];  // 최대 5마리
private int count;                    // 지금 몇 마리 들어 있는지 (= 다음에 넣을 칸 번호)

public boolean isFull()        // count == fishes.length
public boolean isEmpty()       // count == 0
public void add(Fish fish)     // fishes[count]에 넣고 count 증가
public int getTotalPrice()     // 0 ~ count-1 칸의 가격 합
public void show()             // 담긴 물고기 목록 출력
public void empty()            // 비우기 (count를 0으로)
public int getCount()          // 상태 줄에 "3/5" 표시용
```

> 💡 **객체 배열을 처음부터 다 채워두지 않고, `count`로 몇 칸까지 찼는지 직접 관리하는 연습**입니다.
> 반복문은 `fishes.length`가 아니라 `count`까지만 돌아야 `null` 칸을 건드리지 않습니다.

---

### 🏞️ Pond (연못)

**수신할 수 있는 메시지** : 물고기 하나 골라줘

```java
private Fish[] fishes;   // 생성자에서 5종의 물고기로 채워두기

public Pond()
public Fish randomFish()   // Math.random()으로 0 ~ fishes.length-1 중 하나를 골라 반환
```

**랜덤 인덱스 만들기**

```java
int index = (int) (Math.random() * fishes.length);   // 0 ~ 4
```

---

### 🐟 Fish (물고기)

**수신할 수 있는 메시지** : 이름 / 힘 / 가격 알려줘, 힌트 범위 알려줘

```java
private String name;     // "잉어"
private int strength;    // 힘 (1~10, 플레이어에게는 숨김)
private int price;       // 가격

public Fish(String name, int strength, int price)
public String getName()
public int getStrength()
public int getPrice()
public int getHintMin()   // strength - 3 (1보다 작으면 1)
public int getHintMax()   // strength + 3 (10보다 크면 10)
```

> 상태가 생성 후 바뀌지 않는 객체입니다. setter가 필요 없습니다.
> 그래서 `Pond`가 가진 물고기 객체를 그대로 `Bucket`에 담아도 괜찮습니다.

---

## 4. 게임 데이터 (생성자 연습용)

### 연못의 물고기 5종

| 물고기 | 힘 | 가격 | 힌트 범위 |
|---|---|---|---|
| 피라미 | 2 | 300원 | 1 ~ 5 |
| 붕어 | 4 | 800원 | 1 ~ 7 |
| 잉어 | 6 | 1,500원 | 3 ~ 9 |
| 메기 | 8 | 2,500원 | 5 ~ 10 |
| 황금잉어 | 10 | 5,000원 | 7 ~ 10 |

**생성 예시**

```java
fishes = new Fish[]{
        new Fish("피라미", 2, 300),
        new Fish("붕어", 4, 800),
        ...
};
```

### 낚싯대 레벨

| 레벨 | 허용 오차 | 다음 강화 비용 | 성공률 (힌트 7칸 기준) |
|---|---|---|---|
| Lv.1 | ±1 | 2,000원 | 약 43% (3/7) |
| Lv.2 | ±2 | 5,000원 | 약 71% (5/7) |
| Lv.3 | ±3 | 최고 레벨 | 힌트 범위 안이면 무조건 성공 |

**판정 예시** (낚싯대 Lv.1, 잉어 힘 6)

| 입력 | 차이 | 결과 |
|---|---|---|
| 6 | 0 | 성공 |
| 5 또는 7 | 1 | 성공 |
| 4 또는 8 | 2 | 실패 |

> 차이는 `Math.abs(strength - input)`로 구할 수 있습니다.

---

## 5. 메뉴 화면 (Application)

```
============== 🎣 낚시왕 ==============
 소지금: 0원 | 낚싯대 Lv.1 | 양동이 0/5
=======================================
1. 찌 던지기
2. 양동이 보기
3. 시장에서 팔기
4. 낚싯대 강화
9. 프로그램 종료
=======================================
```

- 맨 위 상태 줄은 매 반복마다 `fisher.showStatus()`로 출력한다.

**1번(찌 던지기) 흐름**

1. `fisher.cast()` → 입질이 오면 힌트 출력 후 true
2. true일 때만 Application에서 `sc.nextInt()`로 입력받기
3. `fisher.reel(input)` → 성공/실패 판정

```
... 찌가 흔들린다 ...
!!! 입질이다 !!!  (힘 3 ~ 9 사이)
릴을 몇 번 감을까? (1~10) : 6
🎉 잉어를 낚았다! 양동이에 담았어요 (1/5)
```

**3번(시장에서 팔기) 흐름**

1. `fisher.sellAll()` → 판매 금액 출력, 소지금 증가, 양동이 비우기
2. `fisher.isFishingKing()`이 true면 엔딩 메시지를 출력하고 반복문을 빠져나간다.

```
🐟 물고기 3마리를 4,300원에 팔았어요!
🏆 축하합니다! 소지금 10,000원 달성! 당신은 이제 낚시왕입니다!
```

> **입력은 Application이, 판단은 객체가!** (자동차 예제와 같은 패턴)

---

## 6. 상태별 예외 상황 (Car의 if/else처럼)

| 상황 | 판단하는 곳 | 안내 메시지 예시 |
|---|---|---|
| 양동이가 가득 찼는데 찌 던지기 | `Fisher.cast()` | "양동이가 가득 찼어요! 시장에 먼저 팔고 오세요" |
| 입질 없이 릴 감기 | `Fisher.reel()` | "아직 입질이 없어요~" |
| 빈 양동이로 팔기 | `Fisher.sellAll()` | "팔 물고기가 없어요!" |
| 돈이 부족한데 강화 | `Fisher.upgradeRod()` | "강화 비용이 1,200원 부족해요!" |
| 최고 레벨에서 강화 | `Fisher.upgradeRod()` | "이미 최고 레벨 낚싯대예요!" |
| 1~10 밖의 숫자 입력 | `Fisher.reel()` | 그냥 판정한다 (차이가 커서 자연스럽게 실패) |

> 부족한 금액은 `rod.getUpgradeCost() - money`로 계산합니다.

---

## 7. 구현 순서 추천

의존하는 쪽이 적은 클래스부터 만들면 하나씩 확인하면서 진행할 수 있습니다.

1. `Fish` → 생성자와 getter, 힌트 범위
2. `Rod` → 레벨, 허용 오차, 강화
3. `Bucket` → 객체 배열 + `count` 관리
4. `Pond` → 물고기 5종 채우기 + `randomFish()`
5. `Fisher` → 위 객체들을 필드로 가지고 상태 검증 if/else
6. `Application` → 메뉴, 입력, Fisher에 메시지 보내기

---

## 8. 확장 아이디어 (다음 챕터 이후)

- **낚시터 여러 곳** : `Pond`를 강 / 호수 / 바다 3개의 객체 배열로 두고, 낚시터마다 다른 물고기 후보 넣기
- **미끼** : 찌를 던질 때마다 미끼가 1개씩 줄고, 상점에서 사기
- **희귀도별 확률** : `Math.random() * 100` 값으로 일반 60% / 희귀 30% / 전설 10% 나누기
- **상속/다형성** : `Fish`를 부모로 두고 `CommonFish`, `RareFish` 자식 클래스로 나눠 판매 가격 계산을 다르게 구현
