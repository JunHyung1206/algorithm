# Algorithm

코딩테스트 풀이(Java)와, 풀이가 맞는지 확인하는 작은 하네스를 모아 둔 레포.

## 구조

```text
bin/new        새 문제 폴더를 템플릿에서 만든다
bin/run        문제의 Check.java를 컴파일해서 실행한다
harness/       모든 문제가 공유하는 검증 도구 (Expect.java)
problems/
  _template/   새 문제의 시작점
  <사이트>/<slug>/
    Solution.java   제출하는 코드 (프로그래머스 형식)
    Check.java      예제, 반례, 기준 구현과의 비교
    README.md       유형, 핵심 제약, 막힌 점과 배운 점
build/         컴파일 결과 (git에 올리지 않는다)
```

## 사용법

```bash
bin/new programmers/virus-pipe     # problems/programmers/virus-pipe 생성
bin/run programmers/virus-pipe     # 컴파일하고 Check 실행 (실패가 있으면 종료 코드 1)
```

JDK(`javac`, `java`)만 있으면 돼요. 외부 라이브러리는 쓰지 않아요.

## Check.java에 넣을 것

1. 문제의 입출력 예
2. 직접 만든 작은 반례
3. 확신이 없을 때: 단순한 기준 구현(브루트포스)과 전수 또는 무작위 입력으로 비교

`Expect.equal(이름, 기대값, 실제값)`은 틀린 경우만 출력하고, `Expect.summary()`가 통과/실패 개수를 출력해요.
