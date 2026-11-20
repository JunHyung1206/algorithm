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
    private int maxOpenCount;
    private List<List<Pipe>> pipesOf;
    private int maxInfected;

    public int solution(int n, int infection, int[][] edges, int k) {
        nodeCount = n;
        maxOpenCount = k;
        pipesOf = buildPipes(n, edges);
        maxInfected = 0;

        boolean[] infected = new boolean[n + 1];
        infected[infection] = true;
        explore(infected, 0, NO_PIPE);

        return maxInfected;
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

    // openedCount번 연 뒤의 감염 상태에서, 다음에 열 종류를 하나씩 골라 이어간다.
    private void explore(boolean[] infected, int openedCount, int previousType) {
        int infectedCount = countInfected(infected);
        maxInfected = Math.max(maxInfected, infectedCount);

        if (openedCount == maxOpenCount || infectedCount == nodeCount) {
            return;
        }
        for (int type = 1; type <= PIPE_TYPE_COUNT; type++) {
            if (type == previousType) {
                continue;
            }
            boolean[] next = infected.clone();
            spread(next, type);
            explore(next, openedCount + 1, type);
        }
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
