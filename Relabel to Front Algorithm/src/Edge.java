public class Edge {
    public int flow;
    public int capacity;
    Vertex to;
    Vertex from;

    public Edge(Vertex to, Vertex from, int flow, int capacity){
        this.to = to;
        this.flow = flow;
        this.capacity = capacity;
        this.from = from;
    }

    public int getDifference(){
        return this.capacity-this.flow;
    }
}
