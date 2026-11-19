import java.util.*;

class Solution {

    private static final int PIPE_TYPE_COUNT = 3;
    private static final int NO_PIPE = 0;

    private static class Pipe {
        final int to;
        final int type;

        Pipe(int to, int type) {
            this.to = to;
            this.type = type;
        }
    }

    private int nodeCount;
    private List<List<Pipe>> pipesOf;

    public int solution(int n, int infection, int[][] edges, int k) {
        nodeCount = n;
        pipesOf = buildPipes(n, edges);

        List<int[]> orders = new ArrayList<>();
        generateOrders(orders, new int[k], 0, NO_PIPE);

        int answer = 0;
        for (int[] order : orders) {
            answer = Math.max(answer, countInfectedAfter(order, infection));
        }
        return answer;
    }

    private List<List<Pipe>> buildPipes(int n, int[][] edges) {
        List<List<Pipe>> pipes = new ArrayList<>();
        for (int node = 0; node <= n; node++) {
            pipes.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            pipes.get(edge[0]).add(new Pipe(edge[1], edge[2]));
            pipes.get(edge[1]).add(new Pipe(edge[0], edge[2]));
        }
        return pipes;
    }

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

    private void spread(boolean[] infected, int pipeType) {
        Queue<Integer> queue = new ArrayDeque<>();
        for (int node = 1; node <= nodeCount; node++) {
            if (infected[node]) {
                queue.offer(node);
            }
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (Pipe pipe : pipesOf.get(current)) {
                if (pipe.type == pipeType && !infected[pipe.to]) {
                    infected[pipe.to] = true;
                    queue.offer(pipe.to);
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
