import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class project3main {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner in = new Scanner(new File(args[0]));
        PrintStream out = new PrintStream(new File(args[1]));

        HashMap<String, HashMap<String, Integer>> graph = new HashMap<>();  // hashMap for creating graph
        HashMap<String, HashMap<String, Integer>> honeymoon = new HashMap<>();   // hashMap for creating graph for honeymoon part

        ArrayList<String> unvisited = new ArrayList<>();   // arraylist to check whether node is visited or  not

        HashMap<String, Node> nodes = new HashMap<>();

        TreeSet<Node> nodeComp = new TreeSet<>();
        TreeSet<Node> honeyComp = new TreeSet<>();

        int time = Integer.parseInt(in.nextLine().trim());
        int nofCity = Integer.parseInt(in.nextLine().trim());

        String[] list = in.nextLine().split(" ");
        String mecnunCity = list[0];
        String leylaCity = list[1];

        String num = leylaCity.substring(1);

        int nofC = Integer.parseInt(num);
        int nofD = nofCity - nofC;

        unvisited.add(mecnunCity);

        HashMap<String, Integer> mecnunMap = new HashMap<>();
        Node mecnunNode = new Node(mecnunCity, mecnunCity, 0);
        nodes.put(mecnunCity, mecnunNode);
        nodeComp.add(mecnunNode);
        graph.put(mecnunCity, mecnunMap);

        HashMap<String, Integer> leylaMap = new HashMap<>();
        leylaMap.put(leylaCity, Integer.MAX_VALUE);
        Node leylaNode = new Node(leylaCity, leylaCity, Integer.MAX_VALUE);
        graph.put(mecnunCity, mecnunMap);
        nodeComp.add(leylaNode);

        for (int i = 0; i < nofC-1; i++) {       // reading input and creating graph
            String[] inList = in.nextLine().split(" ");
            String city = inList[0];
            HashMap<String, Integer> cityMap = new HashMap<>();
            if (!city.equals(mecnunCity)) {
                unvisited.add(city);
                Node cityNode = new Node(city, city, Integer.MAX_VALUE);
                nodeComp.add(cityNode);
                nodes.put(city, cityNode);
            }

            unvisited.remove(leylaCity);
            for (int j = 1; j < inList.length; j += 2) {
                String otherCity = inList[j];
                int length = Integer.parseInt(inList[j + 1]);
                cityMap.put(otherCity, length);
            }
            graph.put(city, cityMap);
        }
        nodes.put(leylaCity, leylaNode);

        while (!unvisited.isEmpty() && !nodeComp.isEmpty()) { // dijkstra part
            Node current = nodeComp.pollFirst();
            if (current.length == Integer.MAX_VALUE) {
                break;
            }
            if (unvisited.contains(current.vertex)) {
                String cityName = current.vertex;
                HashMap<String, Integer> cityMap = graph.get(cityName);
                for (Map.Entry<String, Integer> set : cityMap.entrySet()) {
                    Node node = nodes.get(set.getKey());
                    if (node.length > current.length + set.getValue()) {
                        nodeComp.remove(node);
                        node.length = set.getValue() + current.length;
                        node.parent = current;
                        nodeComp.add(node);
                    }
                }
                unvisited.remove(cityName);
            }
        }


        Stack<Node> output = new Stack<Node>();   // printing output
        boolean x = true;
        Node parent = leylaNode.parent;
        output.push(parent);

        if (parent == null) {
            out.print(-1);
            output.pop();
            x = false;
        }
        while (x) {
            if (parent.vertex.equals(mecnunCity)) {
                break;
            }
            parent = parent.parent;
            output.push(parent);
        }
        while (!output.isEmpty()) {
            out.print(output.pop().vertex + " ");
        }
        if (!(parent == null)) {
            out.print(leylaCity);
        }

        int limitCont = leylaNode.length;
        leylaNode.length = 0;
        leylaNode.parent = leylaNode;


        for (int i = 1; i <= nofD+1; i++) {   // reading input for honeymoon
            String[] inList = in.nextLine().split(" ");
            String city = inList[0];
            HashMap<String, Integer> cityMap = new HashMap<String,Integer>();

            if (honeymoon.get(city) != null){
                cityMap = honeymoon.get(city);
            }
            if(city.equals(leylaCity)){
                honeyComp.add(leylaNode);
            }
            else{
                Node cityNode = new Node(city, city, Integer.MAX_VALUE);
                honeyComp.add(cityNode);
                nodes.put(city,cityNode);
            }
            for (int j = 1; j < inList.length; j += 2) {
                String otherCity = inList[j];
                int length = Integer.parseInt(inList[j + 1]);
                if(!honeymoon.containsKey(otherCity)){
                    honeymoon.put(otherCity,new HashMap<String, Integer>());
                }
                HashMap<String, Integer> map = honeymoon.get(otherCity);

                if (map.containsKey(city)) {  // checking for shorter path
                    int distance = map.get(city);
                    if (length < distance) {
                        map.put(city, length);
                        honeymoon.put(otherCity,map);
                        cityMap.put(otherCity, length);
                    } else {
                        cityMap.put(otherCity, distance);
                        honeymoon.put(otherCity,map);

                    }
                } else{
                    cityMap.put(otherCity,length);
                    map.put(city, length);
                    honeymoon.put(otherCity,map);
                }
            }
            honeymoon.put(city, cityMap);
        }

        boolean noWay = false;
        int way = 0;
        while (!honeyComp.isEmpty()) {  // prim's algorithm
            Node current = honeyComp.pollFirst();
            if (current.length == Integer.MAX_VALUE) {
                noWay = true;
                break;
            }
            if (!current.visited) {
                String cityName = current.vertex;
                HashMap<String, Integer> cityMap = honeymoon.get(cityName);
                for (Map.Entry<String, Integer> set : cityMap.entrySet()) {
                    Node node = nodes.get(set.getKey());
                    if (node.vertex.charAt(0) == 'c') {
                        continue;
                    }
                    if (!node.visited && !node.equals(current)){
                        if (node.length > set.getValue()) {
                            honeyComp.remove(node);
                            node.length = set.getValue() ;
                            node.parent = current;
                            honeyComp.add(node);
                        }
                    }
                }
                way += current.length;
                current.visited = true;
                honeyComp.remove(current);
            }
        }

        if (time < limitCont){
            out.print("\n"+ (-1));
        }
        else{
            if (noWay){
                out.print("\n"+(-2));
            }
            else {
                out.print("\n"+way*2);
            }
        }
    }
}
