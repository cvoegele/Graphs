package ch.voegele;

import java.util.*;

public class ListGraph {

    private List<Vertex> vertices;

    public ListGraph() {
        vertices = new ArrayList<>();
    }

    public void addUndirectedEdge(int start, int end) {

        Vertex sVertex = getVertex(start);
        if (sVertex == null) sVertex = addVertex(start);
        Vertex eVertex = getVertex(end);
        if (eVertex == null) eVertex = addVertex(end);

        assert sVertex != null;
        assert eVertex != null;

        //forward edges
        sVertex.outGoingEdges.add(new Edge(sVertex, eVertex, 1));
        eVertex.incomingEdges.add(new Edge(sVertex, eVertex, 1));

        //back edges
        sVertex.outGoingEdges.add(new Edge(eVertex, sVertex, 1));
        eVertex.incomingEdges.add(new Edge(eVertex, sVertex, 1));
    }

    public void addDirectedEdge(int start, int end) {

        //if vertices do not exist, add them
        Vertex sVertex = getVertex(start);
        if (sVertex == null) sVertex = addVertex(start);
        Vertex eVertex = getVertex(end);
        if (eVertex == null) eVertex = addVertex(end);

        assert sVertex != null;
        assert eVertex != null;

        //one way edges
        sVertex.outGoingEdges.add(new Edge(sVertex, eVertex, 1));
        eVertex.incomingEdges.add(new Edge(sVertex, eVertex, 1));
    }

    public Vertex addVertex(int number) {
        for (var vertex : vertices) {
            if (vertex.number == number) throw new IllegalArgumentException("This vertex already exists");
        }

        var newVertex = new Vertex(number);
        vertices.add(newVertex);
        return newVertex;
    }

    public void addVertex(Vertex vertex) {
        for (var vertex1 : vertices) {
            if (vertex1.equals(vertex)) throw new IllegalArgumentException("This vertex already exists");
        }

        vertices.add(vertex);
    }

    private Vertex findNeighbouringNode(Vertex from, Vertex start) {

        //check if initial starting point is neighbour of this node, if so go there
        for (var edge : from.outGoingEdges) {
            if (edge.end.number == start.number) return edge.end;
        }

        //find one that is not dead-end
        for (var edge : from.outGoingEdges) {
            var outEdges = edge.end.outGoingEdges;
            if (!outEdges.isEmpty()) {
                return edge.end;
            }
        }

        //if all have dead-end take dead-end
        var end = from.outGoingEdges.stream().findFirst();
        if (end.isPresent()) return end.get().end;
        return from;
    }

    private Vertex getVertex(int number) {
        for (var vertex : vertices) {
            if (vertex.number == number) return vertex;
        }
        return null;
    }

    /**
     * Implementation of Floyd's Cycle-Detection
     * Hare and Tortoise Algorithm
     * <p>
     * Returns a result containing if the graph is cyclic or not. If
     *
     * @param start vertex number to start at
     * @return result contating if the graph is cyclic or not
     */
    public TortoiseAndHareResult findCycle(int start) {

        var startVertex = getVertex(start);
        assert startVertex != null;

        //start vertex is either a dead-end or not part of the cyclic component.
        if (startVertex.outGoingEdges.size() == 0)
            return new TortoiseAndHareResult(-1, 0, false, start, new ArrayList<>());

        var tortoise = findNeighbouringNode(startVertex, startVertex);
        var hare = findNeighbouringNode(findNeighbouringNode(startVertex, startVertex), startVertex);
        while (tortoise.number != hare.number) {
            tortoise = findNeighbouringNode(tortoise, startVertex);
            hare = findNeighbouringNode(findNeighbouringNode(hare, startVertex), startVertex);
        }

        //end position of hare and tortoise are both dead-ends.
        if (hare.outGoingEdges.size() == 0 && tortoise.outGoingEdges.size() == 0)
            return new TortoiseAndHareResult(-1, 0, false, start, new ArrayList<>());

        var mu = 0;
        tortoise = startVertex;
        while (tortoise.number != hare.number) {
            tortoise = findNeighbouringNode(tortoise, startVertex);
            hare = findNeighbouringNode(hare, startVertex);
            mu += 1;
        }

        var path = new ArrayList<Vertex>();
        path.add(hare);

        var lam = 1;
        hare = findNeighbouringNode(tortoise, startVertex);
        while (tortoise != hare) {
            hare = findNeighbouringNode(hare, startVertex);
            lam += 1;
            path.add(hare);
        }

        return new TortoiseAndHareResult(lam, mu, true, start, path);
    }

    public Set<TortoiseAndHareResult> findAllCycles() {

        var result = new HashSet<TortoiseAndHareResult>();

        var allVertices = new ArrayDeque<>(vertices);
        while (!allVertices.isEmpty()) {
            var vertex = allVertices.pop();

            var singleResult = findCycle(vertex.number);
            if (singleResult.isCyclic) {
                result.add(singleResult);
                /*
                remove vertices, that are already in a path. since, these would only result in the same cycle again.
                This is not needed, but reduces complexity by lambda of the path each new path that is found.
                 */
                allVertices.removeAll(singleResult.path);
            } else {
                /*
                terminate early. As soon, as no cycle is found anymore, we can terminate.
                As long as there is one cycle. It should just find that one over and over again.
                If there are several. It can find the cycle, by using different start positions.
                 */
                return result;
            }
        }
        return result;
    }


}
