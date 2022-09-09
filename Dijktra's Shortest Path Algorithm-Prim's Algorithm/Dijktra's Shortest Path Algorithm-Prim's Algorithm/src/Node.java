import java.util.Objects;

public class Node implements Comparable<Node>{
    String vertex;
//    String firstParent;
    boolean hasParent;
    Node parent;
    int length;
    boolean visited;

    public Node(String vertex, String parent, int length){
//        this.firstParent = parent;
        this.length = length;
        this.vertex = vertex;
        this.hasParent = false;
        this.visited = false;
    }


    public int compareTo(Node node2){
        if(this.length == node2.length){
            return this.vertex.compareTo(node2.vertex);
        }
        return this.length-node2.length;
    }



//    @Override
//    public int hashCode() {
//        return Objects.hash(vertex, parent, length);
//    }
}
