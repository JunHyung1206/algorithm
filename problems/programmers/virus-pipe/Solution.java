import java.util.*;

class Solution {

    private static final int PIPE_TYPE_COUNT = 3;
    private static final int NO_PIPE = 0;       // "직전에 연 종류가 없다"
    private static final int NOT_CONNECTED = 0; // 연결 없음

    private int nodeCount;
    private int[][] pipeTypeBetween;

    public int solution(int n, int infection, int[][] edges, int k) {
        nodeCount = n;
        pipeTypeBetween = buildPipeTypeMatrix(n, edges);

        List<int[]> orders = new ArrayList<>();
        generateOrders(orders, new int[k], 0, NO_PIPE);

        int answer = 0;
        for (int[] order : orders) {
            answer = Math.max(answer, countInfectedAfter(order, infection));
        }
        return answer;
    }

    private int[][] buildPipeTypeMatrix(int n, int[][] edges) {
        int[][] matrix = new int[n + 1][n + 1];
        for (int[] edge : edges) {
            matrix[edge[0]][edge[1]] = edge[2];
            matrix[edge[1]][edge[0]] = edge[2];
        }
        return matrix;
    }

    // 같은 종류를 연달아 여는 건 의미가 없으므로 건너뛰고, 길이 k인 모든 순서를 만든다.
    private void generateOrders(List<int[]> orders, int[] order, int depth, int previousType) {
        if (depth == order.length) {
            orders.add(order.clone());
            return;
        }
        for (int type = 1; type <= PIPE_TYPE_COUNT; type++) {
            if (type == previousType) {
                continue;
            }
            order[depth] = type;
            generateOrders(orders, order, depth + 1, type);
        }
    }

    private int countInfectedAfter(int[] order, int infection) {
        boolean[] infected = new boolean[nodeCount + 1];
        infected[infection] = true;

        for (int type : order) {
            spread(infected, type);
        }
        return countInfected(infected);
    }

    // 이미 감염된 모든 노드에서 출발해서, 해당 종류 파이프로 닿는 노드를 전부 감염시킨다. (infected를 직접 바꾼다)
    private void spread(boolean[] infected, int pipeType) {
        Queue<Integer> queue = new ArrayDeque<>();
        for (int node = 1; node <= nodeCount; node++) {
            if (infected[node]) {
                queue.offer(node);
            }
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int next = 1; next <= nodeCount; next++) {
                if (pipeTypeBetween[current][next] == pipeType && !infected[next]) {
                    infected[next] = true;
                    queue.offer(next);
                }
            }
        }
    }

    private int countInfected(boolean[] infected) {
        int count = 0;
        for (boolean isInfected : infected) {
            if (isInfected) {
                count++;
            }
        }
        return count;
    }
}
