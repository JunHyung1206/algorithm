import java.util.*;

class Solution {

    int N;
    int K;

    int[][] GRAPH;

    // ORDER 만드는 것.
    void generateOrders(List<int[]> orders, int depth, int[] arr, int current) {

        if(depth != -1) {
            arr[depth] = current;
        }
        if(depth == K - 1) {
            orders.add(arr.clone());
            return;
        }
        for(int i = 1; i <= 3 ; i++) {
            if(i == current) {
                continue;
            }
            generateOrders(orders, depth + 1, arr, i);
        }
    }

    public int simulation(int[] order, int infection) {
        int result = 0;
        boolean[] infected = new boolean[N + 1];  // 감염된 상태
        infected[infection] = true;

        for(int i = 0 ; i < order.length ; i++) {
            spread(infected, order[i]);
        }

        for(int i = 0 ; i < infected.length ; i++) {
            if(infected[i]) {
                result++;
            }
        }

        return result;
    }

    public void spread(boolean[] infected, int pipeType) {
        Queue<Integer> q = new ArrayDeque<>();

        for(int i = 0 ; i < infected.length ; i++) {
            if(infected[i]) {
                q.offer(i);
            }
        }

        while(!q.isEmpty()) {
            int current = q.poll();
            for(int i = 1; i < N + 1; i++) {
                if(GRAPH[current][i] == pipeType && !infected[i]) {
                    infected[i] = true;
                    q.offer(i);
                }
            }
        }
    }

    // 순서대로 처리하는 것.
    public int solution(int n, int infection, int[][] edges, int k) {
        N = n;
        K = k;
        GRAPH = new int[N + 1][N + 1];
        int answer = 0;
        // 배양체 노드
        for(int i = 0 ; i < edges.length ; i++) {
            GRAPH[edges[i][0]][edges[i][1]] = edges[i][2];
            GRAPH[edges[i][1]][edges[i][0]] = edges[i][2];
        }

        List<int[]> orders = new ArrayList<>();
        generateOrders(orders, -1, new int[K], 0);   // 0: "아직 아무 종류도 안 썼다"

        for(int i = 0 ; i < orders.size() ; i++) {
            answer = Math.max(answer, simulation(orders.get(i), infection));
        }

        return answer;
    }
}
