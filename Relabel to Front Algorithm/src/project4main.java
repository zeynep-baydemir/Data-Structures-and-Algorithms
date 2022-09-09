import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class project4main {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner in = new Scanner(new File(args[0]));
        PrintStream out = new PrintStream(new File(args[1]));

        HashMap<Vertex, HashMap<Vertex, Edge>> graph = new HashMap<>();

        ArrayList<Vertex> grTrains = new ArrayList<>();
        ArrayList<Vertex> redTrains = new ArrayList<>();
        ArrayList<Vertex> grRein = new ArrayList<>();
        ArrayList<Vertex> redRein = new ArrayList<>();
        ArrayDeque<Vertex> active = new ArrayDeque<>();
        HashSet<Vertex> relabeled = new HashSet<>();

        int giftNumber = 0;

        Vertex source = new Vertex(0, 0, 3);
        int sourceCapacity = 0;
        Vertex sink = new Vertex(0, 3, 0);
        int sinkCapacity = 0;

        int greenTrain = in.nextInt();
        int zeros = 0;
        for (int i = 0; i < greenTrain; i++) {
            int capacity = in.nextInt();
            if (capacity == 0) {
                zeros++;
                continue;
            }
            sinkCapacity += capacity;
            Vertex grTra = new Vertex(capacity, 2, 1);
            grTrains.add(grTra);
            grTra.vehicleSink(sink, graph);
        }
        int redTrain = in.nextInt();
        for (int i = 0; i < redTrain; i++) {
            int capacity = in.nextInt();
            if (capacity == 0) {
                zeros++;
                continue;
            }
            sinkCapacity += capacity;
            Vertex redTra = new Vertex(capacity, 2, 1);
            redTra.vehicleSink(sink, graph);
            redTrains.add(redTra);
        }

        int greenReindeers = in.nextInt();
        for (int i = 0; i < greenReindeers; i++) {
            int capacity = in.nextInt();
            if (capacity == 0) {
                zeros++;
                continue;
            }
            sinkCapacity += capacity;
            Vertex grReindeer = new Vertex(capacity, 2, 1);
            grReindeer.vehicleSink(sink, graph);
            grRein.add(grReindeer);
        }

        int redReindeers = in.nextInt();
        for (int i = 0; i < redReindeers; i++) {
            int capacity = in.nextInt();
            if (capacity == 0) {
                zeros++;
                continue;
            }
            sinkCapacity += capacity;
            Vertex redReindeer = new Vertex(capacity, 2, 1);
            redReindeer.vehicleSink(sink, graph);
            redRein.add(redReindeer);
        }

        sink.capacity = sinkCapacity;
        int gifts = in.nextInt();
        Vertex b = new Vertex(0, 1, 2);
        Vertex c = new Vertex(0, 1, 2);
        Vertex d = new Vertex(0, 1, 2);
        Vertex e = new Vertex(0, 1, 2);
        Vertex bd = new Vertex(0, 1, 2);
        Vertex be = new Vertex(0, 1, 2);
        Vertex cd = new Vertex(0, 1, 2);
        Vertex ce = new Vertex(0, 1, 2);

        for (int i = 0; i < gifts; i++) {
            String type = in.next();
            int capacity = in.nextInt();
            sourceCapacity += capacity;
            giftNumber += capacity;
            if (capacity == 0) {
                continue;
            }

            switch (type) {
                case "a" -> {
                    Vertex a = new Vertex(capacity, 1, 2);
                    a.aToVehicle(graph, grTrains);
                    a.aToVehicle(graph, grRein);
                    a.aToVehicle(graph, redTrains);
                    a.aToVehicle(graph, redRein);
                    a.sourceBag(source, graph);
                    active.addFirst(a);
                }
                case "b" -> b.capacity += capacity;
                case "c" -> c.capacity += capacity;
                case "d" -> d.capacity += capacity;
                case "e" -> e.capacity += capacity;
                case "ab" -> {
                    Vertex ab = new Vertex(capacity, 1, 2);
                    ab.aToVehicle(graph, grRein);
                    ab.aToVehicle(graph, grTrains);
                    ab.sourceBag(source, graph);
                    active.addFirst(ab);
                }
                case "ac" -> {
                    Vertex ac = new Vertex(capacity, 1, 2);
                    ac.aToVehicle(graph, redRein);
                    ac.aToVehicle(graph, redTrains);
                    ac.sourceBag(source, graph);
                    active.addFirst(ac);
                }
                case "ad" -> {
                    Vertex ad = new Vertex(capacity, 1, 2);
                    ad.aToVehicle(graph, grTrains);
                    ad.aToVehicle(graph, redTrains);
                    ad.sourceBag(source, graph);
                    active.addFirst(ad);
                }
                case "ae" -> {
                    Vertex ae = new Vertex(capacity, 1, 2);
                    ae.aToVehicle(graph, grRein);
                    ae.aToVehicle(graph, redRein);
                    ae.sourceBag(source, graph);
                    active.addFirst(ae);
                }
                case "bd" -> bd.capacity += capacity;
                case "be" -> be.capacity += capacity;
                case "cd" -> cd.capacity += capacity;
                case "ce" -> ce.capacity += capacity;
                case "abd" -> {
                    Vertex abd = new Vertex(capacity, 1, 2);
                    abd.aToVehicle(graph, grTrains);
                    abd.sourceBag(source, graph);
                    active.addFirst(abd);
                }
                case "abe" -> {
                    Vertex abe = new Vertex(capacity, 1, 2);
                    abe.aToVehicle(graph, grRein);
                    abe.sourceBag(source, graph);
                    active.addFirst(abe);
                }
                case "acd" -> {
                    Vertex acd = new Vertex(capacity, 1, 2);
                    acd.aToVehicle(graph, redTrains);
                    acd.sourceBag(source, graph);
                    active.addFirst(acd);
                }
                case "ace" -> {
                    Vertex ace = new Vertex(capacity, 1, 2);
                    ace.aToVehicle(graph, redRein);
                    ace.sourceBag(source, graph);
                    active.addFirst(ace);
                }
            }
        }
        source.capacity = sourceCapacity;
        int vehicleNumber = greenReindeers + greenTrain + redReindeers + redTrain - zeros;
        source.height = vehicleNumber + gifts + 2 ;

        if (b.capacity > 0) {
            b.bagVehicle(graph, grTrains);
            b.bagVehicle(graph, grRein);
            b.sourceBag(source, graph);
            active.addFirst(b);
        }
        if (c.capacity > 0) {
            c.bagVehicle(graph, redTrains);
            c.bagVehicle(graph, redRein);
            c.sourceBag(source, graph);
            active.addFirst(c);
        }
        if (d.capacity > 0) {
            d.bagVehicle(graph, grTrains);
            d.bagVehicle(graph, redTrains);
            d.sourceBag(source, graph);
            active.addFirst(d);
        }
        if (e.capacity > 0) {
            e.bagVehicle(graph, grRein);
            e.bagVehicle(graph, redRein);
            e.sourceBag(source, graph);
            active.addFirst(e);
        }
        if (bd.capacity > 0 ){
            bd.bagVehicle(graph, grTrains);
            bd.sourceBag(source, graph);
            active.addFirst(bd);
        }
        if (be.capacity > 0) {
            be.bagVehicle(graph, grRein);
            be.sourceBag(source, graph);
            active.addFirst(be);
        }
        if (cd.capacity > 0) {
            cd.bagVehicle(graph, redTrains);
            cd.sourceBag(source, graph);
            active.addFirst(cd);
        }
        if (ce.capacity > 0) {
            ce.bagVehicle(graph, redRein);
            ce.sourceBag(source, graph);
            active.addFirst(ce);
        }

        while (!active.isEmpty()) {    // distributing gifts to the proper nodes
            Vertex current = active.pollFirst();
            int height = current.height;
            current.discharge(sink, graph,active,source);
            if (current.height > height) {
                if (current.level == 2) {
                    relabeled.add(current);
                    active.addLast(current);
                    if (relabeled.size() == vehicleNumber) break;

                }
                else{
                    active.addFirst(current);
                }
            }
        }
        out.println(giftNumber - sink.excessFlow);
    }
}
