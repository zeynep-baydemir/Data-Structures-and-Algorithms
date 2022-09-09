import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class Vertex {
    public int height;
    public int excessFlow;
    public int capacity;
    public int level;
    public ArrayList<Vertex> neighbours;
    public int index = 0;

    public Vertex(int capacity, int level, int height) {
        this.capacity = capacity;
        this.excessFlow = 0;
        this.level = level;
        this.neighbours = new ArrayList<>();
        this.height = height;
    }
    public void vehicleSink(Vertex sink, HashMap<Vertex, HashMap<Vertex,Edge>> graph){  // creating edges between vehicle and sink
        HashMap<Vertex,Edge> edges;
        if (graph.get(sink)==null){
            edges = new HashMap<>();
        }
        else {
            edges = graph.get(sink);
        }
        Edge edge = new Edge(sink,this,0,this.capacity);
        edges.put(sink,edge);
        graph.put(this,edges);
        this.neighbours.add(sink);
    }

    public void bagVehicle (HashMap<Vertex, HashMap<Vertex,Edge>> graph, ArrayList<Vertex>vehicles){ // creating edges between bag and vehicle
        HashMap<Vertex,Edge> bagVeh;
        HashMap<Vertex,Edge> vehBag;
        if(graph.get(this)==null){
            bagVeh = new HashMap<>();
        }
        else {
            bagVeh = graph.get(this);
        }
        for (Vertex vehicle : vehicles){
            this.neighbours.add(vehicle);
            vehicle.neighbours.add(this);
            vehBag = graph.get(vehicle);
            int capacity = Math.min(this.capacity,vehicle.capacity);
            Edge edge1 = new Edge(vehicle, this, 0, capacity);
            Edge edge2 = new Edge(this, vehicle, capacity, capacity);
            bagVeh.put(vehicle,edge1);
            vehBag.put(this,edge2);
            graph.put(vehicle,vehBag);
        }
        graph.put(this,bagVeh);
    }
    public void aToVehicle(HashMap<Vertex, HashMap<Vertex,Edge>> graph, ArrayList<Vertex> vehicles){ // creating edges between vehicle and sink
        HashMap<Vertex,Edge> bagVeh;
        HashMap<Vertex,Edge> vehBag;
        if(graph.get(this)==null){
            bagVeh = new HashMap<>();
        }
        else {
            bagVeh = graph.get(this);
        }
        for (Vertex vehicle : vehicles){
            this.neighbours.add(vehicle);
            vehicle.neighbours.add(this);
            vehBag = graph.get(vehicle);
            Edge edge1 = new Edge(vehicle, this, 0, 1);
            Edge edge2 = new Edge(this, vehicle, 1, 1);
            bagVeh.put(vehicle,edge1);
            vehBag.put(this,edge2);
            graph.put(vehicle,vehBag);
        }
        graph.put(this,bagVeh);
    }

    public void sourceBag(Vertex source, HashMap<Vertex, HashMap<Vertex,Edge>> graph){  // creating edges between source and bag
        HashMap<Vertex,Edge> bagEdges = graph.get(this);
        HashMap<Vertex,Edge> sourceEdges;
        if (graph.get(source) == null){
            sourceEdges = new HashMap<>();
        }
        else {
            sourceEdges = graph.get(source);
        }
        Edge bagEdge = new Edge(source, this, 0,this.capacity);
        Edge sourceEdge = new Edge(this, source, this.capacity, this.capacity);
        bagEdges.put(source,bagEdge);
        sourceEdges.put(this,sourceEdge);
        graph.put(this,bagEdges);
        graph.put(source,sourceEdges);
        this.excessFlow += this.capacity;
        this.neighbours.add(source);
        source.neighbours.add(this);
    }

    public void push( Vertex to, Vertex sink, HashMap<Vertex, HashMap<Vertex, Edge>> graph, ArrayDeque<Vertex> activeNodes,Vertex source) {  // operates the flow from one node to another
        Edge edge = graph.get(this).get(to);
        int flow = Math.min(this.excessFlow, edge.getDifference());
        edge.flow += flow;
        this.excessFlow -= flow;
        to.excessFlow += flow;
        if (!to.equals(sink)&&!to.equals(source)&&!activeNodes.contains(to)) {
            activeNodes.add(to);
        }
        try{
            graph.get(to).get(this).flow -= flow;
        }
        catch (Exception e){

        }
    }

    public int relabel() {  // increasing height of node
        this.height++;
        return 0;
    }

    public void discharge(Vertex sink, HashMap<Vertex, HashMap<Vertex, Edge>> graph, ArrayDeque<Vertex> activeNodes,Vertex source) {  // making excess flow of node zero
        while (this.excessFlow > 0) {
            for (int i = this.index; i< this.neighbours.size();i++){
                Vertex neighbour = this.neighbours.get(i);
                if (this.height > neighbour.height && graph.get(this).get(neighbour).getDifference()>0) {
                    this.push(neighbour, sink, graph, activeNodes,source);
                    index = i;
                    if (this.excessFlow == 0) {
                        activeNodes.remove(this);
                        return;
                    }
                }
            }
            this.index = relabel();
        }
    }


}
