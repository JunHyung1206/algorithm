import java.util.Arrays;
import java.util.Objects;

/**
 * Check.java에서 쓰는 검증 도구.
 * 틀린 경우만 출력하고(최대 MAX_FAILURE_LINES줄), summary()가 개수를 출력한다. 실패가 있으면 종료 코드 1로 끝난다.
 */
public final class Expect {

    private static final int MAX_FAILURE_LINES = 10;

    private static int passed = 0;
    private static int failed = 0;

    private Expect() {
    }

    public static void equal(String name, Object expected, Object actual) {
        if (Objects.deepEquals(expected, actual)) {
            passed++;
            return;
        }
        failed++;
        if (failed <= MAX_FAILURE_LINES) {
            System.out.println("FAIL " + name + " -> 기대 " + show(expected) + ", 실제 " + show(actual));
        }
    }

    public static void summary() {
        System.out.println("통과 " + passed + ", 실패 " + failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static String show(Object value) {
        if (value instanceof int[]) {
            return Arrays.toString((int[]) value);
        }
        if (value instanceof Object[]) {
            return Arrays.deepToString((Object[]) value);
        }
        return String.valueOf(value);
    }
}
