package ch.voegele;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class TortoiseAndHareResult {
    protected final int lambda;
    protected final int mu;
    protected final int startVertex;
    protected final boolean isCyclic;
    protected final List<Vertex> path;

    public TortoiseAndHareResult(int lambda, int mu, boolean isCyclic,  int startVertex, List<Vertex> path) {
        this.lambda = lambda;
        this.mu = mu;
        this.startVertex = startVertex;
        this.isCyclic = isCyclic;
        this.path = path;
    }

    public void printPath() {
        for (int i = 0, pathSize = path.size(); i < pathSize; i++) {
            Vertex vertex = path.get(i);
            System.out.print(vertex.number);
            if (i != pathSize - 1) System.out.print(" -> ");
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TortoiseAndHareResult that = (TortoiseAndHareResult) o;
        return lambda == that.lambda &&
                mu == that.mu &&
                isCyclic == that.isCyclic;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lambda, mu, isCyclic);
    }
}
