import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Random;

// 예제와, 단순한 기준 구현(리스트에서 직접 지우고 끼워 넣기)과의 무작위 비교.
// 기준 구현은 느려서 효율성 테스트용이 아니다. 작은 입력의 정답을 확인하는 데만 쓴다.
public class Check {

    private static final int RANDOM_CASES = 500;

    public static void main(String[] args) {
        String[] example1 = {"D 2", "C", "U 3", "C", "D 4", "C", "U 2", "Z", "Z"};
        String[] example2 = {"D 2", "C", "U 3", "C", "D 4", "C", "U 2", "Z", "Z", "U 1", "C"};

        // 기준 구현이 예제와 맞는지 먼저 확인한다.
        Expect.equal("기준 구현: 예제 1", "OOOOXOOO", NaiveTable.run(8, 2, example1));
        Expect.equal("기준 구현: 예제 2", "OOXOXOOO", NaiveTable.run(8, 2, example2));

        Expect.equal("예제 1", "OOOOXOOO", () -> new Solution().solution(8, 2, example1));
        Expect.equal("예제 2", "OOXOXOOO", () -> new Solution().solution(8, 2, example2));

        compareWithNaive();
        Expect.summary();
    }

    private static void compareWithNaive() {
        Random random = new Random(3);
        for (int i = 0; i < RANDOM_CASES; i++) {
            int n = 5 + random.nextInt(10);
            int k = random.nextInt(n);
            NaiveTable table = new NaiveTable(n, k);
            List<String> commands = new ArrayList<>();
            int length = 1 + random.nextInt(30);
            for (int j = 0; j < length; j++) {
                String command = randomValidCommand(random, table);
                table.apply(command);
                commands.add(command);
            }
            String[] cmd = commands.toArray(new String[0]);
            Expect.equal("무작위 #" + i + " n=" + n + " k=" + k + " " + commands, table.result(),
                    () -> new Solution().solution(n, k, cmd));
        }
    }

    // 표 범위를 벗어나는 이동, 삭제된 행이 없을 때의 Z, 모든 행을 지우는 C는 만들지 않는다.
    private static String randomValidCommand(Random random, NaiveTable table) {
        List<String> options = new ArrayList<>();
        if (table.selected() > 0) {
            options.add("U " + (1 + random.nextInt(table.selected())));
        }
        if (table.selected() < table.size() - 1) {
            options.add("D " + (1 + random.nextInt(table.size() - 1 - table.selected())));
        }
        if (table.size() > 1) {
            options.add("C");
        }
        if (table.hasRemoved()) {
            options.add("Z");
        }
        return options.get(random.nextInt(options.size()));
    }

    private static class NaiveTable {
        private final int n;
        private final List<Integer> rows = new ArrayList<>();  // 살아 있는 행의 원래 번호
        private final Deque<int[]> removed = new ArrayDeque<>(); // {삭제 당시 위치, 원래 번호}
        private int selected;                                    // rows 안에서의 위치

        NaiveTable(int n, int k) {
            this.n = n;
            for (int row = 0; row < n; row++) {
                rows.add(row);
            }
            this.selected = k;
        }

        static String run(int n, int k, String[] commands) {
            NaiveTable table = new NaiveTable(n, k);
            for (String command : commands) {
                table.apply(command);
            }
            return table.result();
        }

        int size() {
            return rows.size();
        }

        int selected() {
            return selected;
        }

        boolean hasRemoved() {
            return !removed.isEmpty();
        }

        void apply(String command) {
            String[] parts = command.split(" ");
            switch (parts[0]) {
                case "U":
                    selected -= Integer.parseInt(parts[1]);
                    break;
                case "D":
                    selected += Integer.parseInt(parts[1]);
                    break;
                case "C":
                    removed.push(new int[]{selected, rows.remove(selected)});
                    if (selected == rows.size()) {
                        selected--;
                    }
                    break;
                case "Z":
                    int[] last = removed.pop();
                    rows.add(last[0], last[1]);
                    if (last[0] <= selected) {
                        selected++;
                    }
                    break;
                default:
                    throw new IllegalArgumentException(command);
            }
        }

        String result() {
            char[] state = new char[n];
            Arrays.fill(state, 'X');
            for (int row : rows) {
                state[row] = 'O';
            }
            return new String(state);
        }
    }
}
