// bin/run <유형>/<slug> 로 실행한다. 아래는 템플릿 예시이니 문제에 맞게 바꾼다.
// 실제값은 람다로 넘기면, 풀이가 예외를 던져도 그 케이스만 실패로 세고 나머지를 계속 확인한다.
public class Check {

    public static void main(String[] args) {
        // 1. 문제의 입출력 예
        Expect.equal("예제 1", 3, () -> new Solution().solution(1, 2));

        // 2. 직접 만든 작은 반례

        // 3. 확신이 없을 때: 단순한 기준 구현(브루트포스)과 전수 또는 무작위 입력으로 비교

        Expect.summary();
    }
}
