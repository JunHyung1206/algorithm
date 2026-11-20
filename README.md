# Algorithm

코딩테스트 풀이(Java)와, 풀이가 맞는지 확인하는 작은 하네스를 모아 둔 레포.

## 구조

```text
bin/new        새 문제 폴더를 템플릿에서 만든다
bin/run        문제의 Check.java를 컴파일해서 실행한다
harness/       모든 문제가 공유하는 검증 도구 (Expect.java)
problems/
  _template/   새 문제의 시작점
  <유형>/<slug>/      주 유형 폴더 아래에 문제 하나 (유형 폴더 이름은 아래 표)
    Solution.java   제출하는 코드 (프로그래머스 형식)
    Check.java      예제, 반례, 기준 구현과의 비교
    README.md       출처, 유형 태그, 핵심 제약, 막힌 점과 배운 점
build/         컴파일 결과 (git에 올리지 않는다)
```

## 사용법

```bash
bin/new brute-force/virus-pipe     # problems/brute-force/virus-pipe 생성
bin/run brute-force/virus-pipe     # 컴파일하고 Check 실행 (실패가 있으면 종료 코드 1)
```

JDK(`javac`, `java`)만 있으면 돼요. 외부 라이브러리는 쓰지 않아요.

## Check.java에 넣을 것

1. 문제의 입출력 예
2. 직접 만든 작은 반례
3. 확신이 없을 때: 단순한 기준 구현(브루트포스)과 전수 또는 무작위 입력으로 비교

`Expect.equal(이름, 기대값, 실제값)`은 틀린 경우만 출력하고, `Expect.summary()`가 통과/실패 개수를 출력해요.
실제값을 `() -> new Solution().solution(...)`처럼 람다로 넘기면, 풀이가 예외를 던져도 그 케이스만 실패로 세고 다음 케이스를 계속 확인해요.

## 유형별 정리

유형을 묶어서 복습하려고 폴더를 유형별로 나눠요. 한 문제가 여러 유형에 걸치면 풀이의 중심이 된 유형 하나를 폴더로 고르고, 나머지는 README의 `- 유형:` 줄에 태그로 함께 적어요(첫 태그가 폴더 유형).
유형은 풀고 나서 정해요. 풀기 전에 모르면 `implementation`에 만들고, 다 풀고 나서 `git mv`로 옮겨요.

| 태그 | 폴더 |
|---|---|
| 구현 | `implementation` |
| 완전탐색 | `brute-force` |
| 백트래킹 | `backtracking` |
| 이분탐색 | `binary-search` |
| 그리디 | `greedy` |
| DP | `dp` |
| BFS / DFS | `graph-search` |
| 최단경로 | `shortest-path` |
| 자료구조 | `data-structure` |
| 정렬 | `sort` |
| 투포인터 | `two-pointer` |
| 누적합 | `prefix-sum` |
| 문자열 | `string` |
| 유니온파인드 | `union-find` |
| 비트마스크 | `bitmask` |
| 수학 | `math` |

표에 없는 유형이 필요하면 여기에 먼저 추가하고 써요. 태그로 걸친 문제까지 모아 보려면:

```bash
grep -l "유형:.*BFS" problems/*/*/README.md
```
