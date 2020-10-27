package ch.voegele;

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

}
