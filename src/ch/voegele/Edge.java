package ch.voegele;

public class Edge {

    public final Vertex start;
    public final Vertex end;
    public final int weigth;

    public Edge(Vertex start, Vertex end, int weigth) {
        this.start = start;
        this.end = end;
        this.weigth = weigth;
    }
}
