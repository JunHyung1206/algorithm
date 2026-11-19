import java.util.Random;

// 예제, 직접 만든 반례, 기준 구현과의 무작위 비교
public class Check {

    private static final int RANDOM_CASES = 3000;

    public static void main(String[] args) {
        int[][] example1 = {{1, 2, 1}, {1, 3, 1}, {1, 4, 3}, {1, 5, 2}, {5, 6, 1}, {5, 7, 1}, {2, 8, 3}, {2, 9, 2}, {9, 10, 1}};
        int[][] example2 = {{1, 2, 3}, {1, 4, 3}, {4, 5, 1}, {5, 6, 1}, {3, 6, 2}, {3, 7, 2}};
        Expect.equal("예제 1", 6, () -> new Solution().solution(10, 1, example1, 2));
        Expect.equal("예제 2", 7, () -> new Solution().solution(7, 6, example2, 3));

        // 처음 여는 종류가 A(1)여야 최댓값이 나오는 경우
        int[][] aThenB = {{1, 2, 1}, {2, 3, 2}};
        int[][] onlyA = {{1, 2, 1}, {2, 3, 1}};
        Expect.equal("A 다음 B", 3, () -> new Solution().solution(3, 1, aThenB, 2));
        Expect.equal("A만 있고 k=1", 3, () -> new Solution().solution(3, 1, onlyA, 1));

        compareWithReference();
        Expect.summary();
    }

    private static void compareWithReference() {
        Random random = new Random(11);
        for (int i = 0; i < RANDOM_CASES; i++) {
            int n = 2 + random.nextInt(13);
            int[][] edges = randomTree(random, n, 1 + random.nextInt(3));
            int infection = 1 + random.nextInt(n);
            int k = 1 + random.nextInt(5);
            Expect.equal("무작위 #" + i, reference(n, infection, edges, k),
                    () -> new Solution().solution(n, infection, edges, k));
        }
    }

    // 노드 v의 부모를 앞선 노드 중에서 고르면 항상 트리가 된다.
    private static int[][] randomTree(Random random, int n, int typeCount) {
        int[][] edges = new int[n - 1][];
        for (int node = 2; node <= n; node++) {
            edges[node - 2] = new int[]{1 + random.nextInt(node - 1), node, 1 + random.nextInt(typeCount)};
        }
        return edges;
    }

    // 기준 구현: "같은 종류 연속 금지" 같은 가정 없이 반복을 포함한 모든 순서를 시도하고,
    // 번지는 것도 BFS 대신 "더 번질 게 없을 때까지 반복"으로 계산한다.
    private static int reference(int n, int infection, int[][] edges, int k) {
        boolean[] infected = new boolean[n + 1];
        infected[infection] = true;
        return bestAfter(edges, infected, k);
    }

    private static int bestAfter(int[][] edges, boolean[] infected, int remaining) {
        int count = 0;
        for (boolean isInfected : infected) {
            if (isInfected) {
                count++;
            }
        }
        if (remaining == 0) {
            return count;
        }
        int best = count;
        for (int type = 1; type <= 3; type++) {
            boolean[] next = infected.clone();
            boolean changed = true;
            while (changed) {
                changed = false;
                for (int[] edge : edges) {
                    if (edge[2] != type) {
                        continue;
                    }
                    if (next[edge[0]] && !next[edge[1]]) {
                        next[edge[1]] = true;
                        changed = true;
                    } else if (next[edge[1]] && !next[edge[0]]) {
                        next[edge[0]] = true;
                        changed = true;
                    }
                }
            }
            best = Math.max(best, bestAfter(edges, next, remaining - 1));
        }
        return best;
    }
}
