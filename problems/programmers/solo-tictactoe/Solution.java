class Solution {
    public int solution(String[] board) {

        // O가 이기는 경우 : O_count == X_count + 1
        // X가 이기는 경우 : O_count == X_count

        // 그외 : O_count == X_count or O_count == X_count + 1

        int oCount = 0;
        int xCount = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length(); j++) {
                if (board[i].charAt(j) == 'O') {
                    oCount++;
                }
                if (board[i].charAt(j) == 'X') {
                    xCount++;
                }
            }
        }

        if (!((oCount == xCount) || (oCount == xCount + 1))) {
            return 0;
        }

        boolean oWin = isWinning(board, 'O');
        boolean xWin = isWinning(board, 'X');

        if (oWin && xWin) {
            return 0;
        }

        if (oWin) {
            if ((oCount == xCount + 1)) {
                return 1;
            } else {
                return 0;
            }
        }

        if (xWin) {
            if ((oCount == xCount)) {
                return 1;
            } else {
                return 0;
            }
        }
        return 1;
    }

    public boolean isWinning(String[] board, char ch) {
        boolean cond;
        for (int i = 0; i < board.length; i++) {
            cond = true;
            for (int j = 0; j < board[i].length(); j++) {
                if (ch != board[i].charAt(j)) {
                    cond = false;
                    break;
                }
            }
            if (cond) {
                return cond;
            }
        }

        for (int i = 0; i < board.length; i++) {
            cond = true;
            for (int j = 0; j < board[i].length(); j++) {
                if (ch != board[j].charAt(i)) {
                    cond = false;
                    break;
                }
            }
            if (cond) {
                return cond;
            }
        }
        cond = true;
        for (int i = 0; i < board.length; i++) {
            if (ch != board[i].charAt(i)) {
                cond = false;
                break;
            }
        }
        if (cond) return true;

// 역대각선
        cond = true;
        for (int i = 0; i < board.length; i++) {
            if (ch != board[board.length - 1 - i].charAt(i)) {
                cond = false;
                break;
            }
        }
        if (cond)
            return true;
        return false;
    }
}
