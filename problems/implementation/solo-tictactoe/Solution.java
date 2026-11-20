class Solution {

    private static final int SIZE = 3;
    private static final char FIRST_PLAYER = 'O';  // 먼저 두는 쪽
    private static final char SECOND_PLAYER = 'X';

    public int solution(String[] board) {
        return isReachable(board) ? 1 : 0;
    }

    // 규칙대로 번갈아 두다가 만들어질 수 있는 판인지 확인한다.
    private boolean isReachable(String[] board) {
        int firstCount = countMarks(board, FIRST_PLAYER);
        int secondCount = countMarks(board, SECOND_PLAYER);

        boolean firstTurnedLast = firstCount == secondCount + 1;
        boolean secondTurnedLast = firstCount == secondCount;
        if (!firstTurnedLast && !secondTurnedLast) {
            return false;
        }

        // 이긴 쪽이 마지막에 둔 것이어야 한다. (둘 다 이긴 판은 두 조건을 동시에 만족할 수 없어 여기서 걸러진다.)
        if (isWinning(board, FIRST_PLAYER) && !firstTurnedLast) {
            return false;
        }
        if (isWinning(board, SECOND_PLAYER) && !secondTurnedLast) {
            return false;
        }
        return true;
    }

    private int countMarks(String[] board, char player) {
        int count = 0;
        for (String row : board) {
            for (char cell : row.toCharArray()) {
                if (cell == player) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isWinning(String[] board, char player) {
        for (int i = 0; i < SIZE; i++) {
            if (isLineFilled(board, player, i, 0, 0, 1)) {  // i번째 가로줄
                return true;
            }
            if (isLineFilled(board, player, 0, i, 1, 0)) {  // i번째 세로줄
                return true;
            }
        }
        return isLineFilled(board, player, 0, 0, 1, 1)             // 왼쪽 위 -> 오른쪽 아래 대각선
                || isLineFilled(board, player, 0, SIZE - 1, 1, -1); // 오른쪽 위 -> 왼쪽 아래 대각선
    }

    // (startRow, startCol)에서 (rowStep, colStep) 방향으로 SIZE칸이 모두 player인지 확인한다.
    private boolean isLineFilled(String[] board, char player, int startRow, int startCol, int rowStep, int colStep) {
        for (int i = 0; i < SIZE; i++) {
            int row = startRow + i * rowStep;
            int col = startCol + i * colStep;
            if (board[row].charAt(col) != player) {
                return false;
            }
        }
        return true;
    }
}
