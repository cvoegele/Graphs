package ch.voegele;

public class Main {

    public static void main(String[] args) {

        var graph = new ListGraph();

        graph.addDirectedEdge(0,1);
        graph.addDirectedEdge(1,2);
        graph.addDirectedEdge(1,3);
        graph.addDirectedEdge(3,4);
        graph.addDirectedEdge(4,0);
        graph.addDirectedEdge(6,5);
        graph.addDirectedEdge(5,3);

        //graph.dfs(0);
        var shortestCycle = graph.findCycle(6);
        System.out.println(shortestCycle.lambda);
        System.out.println(shortestCycle.isCyclic);
        shortestCycle.printPath();

    }
}
