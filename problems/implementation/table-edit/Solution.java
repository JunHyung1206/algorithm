import java.util.*;

class Solution {

    // 파란색 : 현재 선택된 행
    // 총 n개
    // 현재 선택된 행은 k

    private int current;
    private int size;
    private boolean[] isDeleted;
    private Deque<Integer> delete = new ArrayDeque<>();

    // U
    private void selectUp(int x) {

        int count = 0;
        while(count < x) {
            if(!isDeleted[current]) {
                count++;

            }
            current--;
        }
    }

    // D
    private void selectDown(int x) {
        int count = 0;
        while(count < x) {
            if(!isDeleted[current]) {
                count++;
            }
            current++;
        }
    }

    // C
    private void delete() {
        delete.push(current);
        isDeleted[current] = true;
        size--;

        if(current == size) {
            current--;
        } else {
            current++;
        }

    }

    // Z
    private void reset() {
        int value = delete.pop();
        isDeleted[value] = false;
        size++;
    }

    public String solution(int n, int k, String[] cmd) {
        String answer = "";

        current = k;
        isDeleted = new boolean[n];

        for(int i = 0 ; i < cmd.length; i++) {
            String command = cmd[i].split(" ")[0];

            if(command.equals("U")) {
                int value = Integer.valueOf( cmd[i].split(" ")[1]);
                selectUp(value);
            } else if(command.equals("D")) {
                int value = Integer.valueOf( cmd[i].split(" ")[1]);
                selectDown(value);
            } else if(command.equals("C")) {
                delete();
            } else if(command.equals("Z")) {
                reset();
            }
            System.out.println(cmd[i]);
        }
        return answer;
    }
}
