package ch.voegele;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    public final int number;
    public final List<Edge> incomingEdges;
    public final List<Edge> outGoingEdges;

    public Vertex(int number) {
        this.number = number;
        incomingEdges = new ArrayList<>();
        outGoingEdges = new ArrayList<>();
    }
}