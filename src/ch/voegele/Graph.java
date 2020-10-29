package ch.voegele;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Graph {

    private final int[][] data;
    private final int length; //number of vertices

    public Graph(int length) {
        this.length = length;
        data = new int[length][length];
    }

    public void addUndirectedEdge(int start, int end, int weight) {
        assert (start >= 0 && start < length);
        assert (end >= 0 && end < length);

        data[start][end] = weight;
        data[end][start] = weight;
    }

    public void addDirectedEge(int start, int end, int weight) {
        assert (start >= 0 && start < length);
        assert (end >= 0 && end < length);

        data[start][end] = weight;
    }

    public void dfs(int startVertex) {

        List<Integer> visited = new ArrayList<>();
        Deque<Integer> s = new ArrayDeque<>();

        s.push(startVertex);

        while (!s.isEmpty()) {
            startVertex = s.poll();
            if (!visited.contains(startVertex)) {
                visited.add(startVertex);
                System.out.println("Visited Edge " + startVertex);

                for (int i = 0; i < length; i++) {
                    var edgeTo = data[startVertex][i];
                    if (edgeTo != 0) {
                        s.push(i);
                    }

                }
            }
        }

    }

    private int findNeighbouringNode(int currentNode) throws IndexOutOfBoundsException {


        for (int i = 0; i < length; i++) {
            if (data[currentNode][i] != 0) {
                return i;
            }
        }

        return currentNode;
    }

    /**
     * Floyd's Cycle-finding Algorithm
     *
     * This implementation does not work because of how findNeighbouringNode is implemented. It always takes the one with the lowest index first.
     * This can make that either the haare or the turtoise end up in a dead-end, even though there is a different path available.
     *
     * This could be prevented by only going into dead-end, if there are no other options available. This is easy in a adjacency list, but not in a adjacency matrix.
     * So refer to that implementation.
     *
     * @param start starting index of starting node
     * @return Result containing the lambda and mu of the algorithm run. Interpret these differently to check whether there is a cycle.
     */
    @Deprecated
    public TortoiseAndHareResult tortoiseAndHare(int start) {

        var tortoise = findNeighbouringNode(start);
        var hare = findNeighbouringNode(findNeighbouringNode(start));
        while (tortoise != hare) {
            tortoise = findNeighbouringNode(tortoise);
            hare = findNeighbouringNode(findNeighbouringNode(hare));
        }

        var mu = 0;
        tortoise = start;
        while (tortoise != hare) {
            tortoise = findNeighbouringNode(tortoise);
            hare = findNeighbouringNode(hare);
            mu += 1;
        }

        var lam = 1;
        hare = findNeighbouringNode(tortoise);
        while (tortoise != hare) {
            hare = findNeighbouringNode(hare);
            lam += 1;
        }

        return new TortoiseAndHareResult(lam, mu, true,start, new ArrayList<>());
    }

}
