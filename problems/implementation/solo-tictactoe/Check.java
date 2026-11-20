import java.util.HashSet;
import java.util.Set;

// 기본 케이스와, 가능한 모든 판(3^9)을 "실제로 번갈아 두어 만들 수 있는 판" 목록과 비교한다.
public class Check {

    private static final int SIZE = 3;
    private static final char[] SYMBOLS = {'.', 'O', 'X'};
    private static final int[][][] LINES = {
            {{0, 0}, {0, 1}, {0, 2}}, {{1, 0}, {1, 1}, {1, 2}}, {{2, 0}, {2, 1}, {2, 2}},
            {{0, 0}, {1, 0}, {2, 0}}, {{0, 1}, {1, 1}, {2, 1}}, {{0, 2}, {1, 2}, {2, 2}},
            {{0, 0}, {1, 1}, {2, 2}}, {{0, 2}, {1, 1}, {2, 0}}
    };

    public static void main(String[] args) {
        Expect.equal("O와 X가 두 개씩, 승자 없음", 1, () -> new Solution().solution(new String[]{"O.X", ".O.", "..X"}));
        Expect.equal("둘 다 이김", 0, () -> new Solution().solution(new String[]{"OOO", "...", "XXX"}));
        Expect.equal("X가 먼저 둠", 0, () -> new Solution().solution(new String[]{"...", ".X.", "..."}));
        Expect.equal("빈 판", 1, () -> new Solution().solution(new String[]{"...", "...", "..."}));

        compareAllBoards();
        Expect.summary();
    }

    private static void compareAllBoards() {
        Set<String> reachable = new HashSet<>();
        char[][] board = new char[SIZE][SIZE];
        for (char[] row : board) {
            java.util.Arrays.fill(row, '.');
        }
        play(board, 'O', reachable);

        int boardCount = (int) Math.pow(SYMBOLS.length, SIZE * SIZE);
        for (int code = 0; code < boardCount; code++) {
            String cells = decode(code);
            String[] rows = {cells.substring(0, 3), cells.substring(3, 6), cells.substring(6, 9)};
            Expect.equal("판 " + cells, reachable.contains(cells) ? 1 : 0, () -> new Solution().solution(rows));
        }
    }

    // 빈 판에서 O, X가 번갈아 두며 만들 수 있는 판을 모두 모은다. 누가 이기면 그 뒤로는 두지 않는다.
    private static void play(char[][] board, char turn, Set<String> reachable) {
        reachable.add(encode(board));
        if (wins(board, 'O') || wins(board, 'X')) {
            return;
        }
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] != '.') {
                    continue;
                }
                board[row][col] = turn;
                play(board, turn == 'O' ? 'X' : 'O', reachable);
                board[row][col] = '.';
            }
        }
    }

    private static boolean wins(char[][] board, char player) {
        for (int[][] line : LINES) {
            boolean allMine = true;
            for (int[] cell : line) {
                if (board[cell[0]][cell[1]] != player) {
                    allMine = false;
                    break;
                }
            }
            if (allMine) {
                return true;
            }
        }
        return false;
    }

    private static String encode(char[][] board) {
        StringBuilder builder = new StringBuilder();
        for (char[] row : board) {
            builder.append(row);
        }
        return builder.toString();
    }

    private static String decode(int code) {
        char[] cells = new char[SIZE * SIZE];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = SYMBOLS[code % SYMBOLS.length];
            code /= SYMBOLS.length;
        }
        return new String(cells);
    }
}
