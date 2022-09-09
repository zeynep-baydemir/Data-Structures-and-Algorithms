import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class project2main {
    public static void main(String[] args) throws FileNotFoundException {

        Locale.setDefault(new Locale("en", "US"));
        Scanner in = new Scanner(new File(args[0]));
        PrintStream out = new PrintStream(new File(args[1]));

        double currentTime = 0.0;
        int invalidAttempts = 0;
        int canceledAttempts = 0;
        int trainingSize = 0;
        int massageSize = 0;
        int physioSize = 0;

        ArrayList<Player> players = new ArrayList<Player>();

        PriorityQueue<Training> training = new PriorityQueue<Training>();
        PriorityQueue<Massage> massages = new PriorityQueue<Massage>();
        PriorityQueue<Physiotherapy> physiotherapy = new PriorityQueue<Physiotherapy>();
        PriorityQueue<Physiotherapist> physiotherapists = new PriorityQueue<Physiotherapist>();
        PriorityQueue<Event> events = new PriorityQueue<Event>();

        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            int ID = in.nextInt();
            int skillLevel = in.nextInt();
            players.add(new Player(ID, skillLevel));
        }

        int A = in.nextInt();
        for (int i = 0; i < A; i++) {
            String input = in.next();
            int ID = in.nextInt();
            double arrivalTime = in.nextDouble();
            double duration = in.nextDouble();
            int skillLevel = players.get(ID).getSkillLevel();

            if (input.equals("m")) {       // creating events
                events.add(new Event("m", arrivalTime, duration, ID, skillLevel));
            } else {
                events.add(new Event("t", arrivalTime, duration, ID));
            }
        }

        int nofPhysio = in.nextInt();
        for (int i = 0; i < nofPhysio; i++) {
            double serviceTime = in.nextDouble();
            physiotherapists.add(new Physiotherapist(i, serviceTime));
        }

        int nofCoach = in.nextInt();

        int nofMasseur = in.nextInt();

        while (!events.isEmpty()) {
            Event event = events.poll();
            String type = event.getEventType();
            double arrivalTime = event.getArrivalTime();
            currentTime = arrivalTime;
            int ID = event.getPlayerID();
            Player player = players.get(ID);
            double eventDuration = event.getDuration();
            double eventWaitTime = currentTime - arrivalTime;
            double eventTempTime = currentTime + eventDuration;

            switch (type) {
                case "lt" -> {              // if event is leaving training
                    player.available();
                    double playerTrTime = player.trainingTime;
                    nofCoach++;
                    events.add(new Event("p", currentTime, player.getID(), playerTrTime)); // adding physiotherapy event

                    while (!training.isEmpty() && nofCoach > 0) {   // checks training queue
                        Training train = training.poll();
                        double timeTrain = train.getDuration();
                        double tempTrain = currentTime + timeTrain;
                        double arrivalTrain = train.getArrivalTime();
                        double trainWait = currentTime-train.getArrivalTime();
                        Player player1 = players.get(train.getPlayerID());
                        int tempSize;
                        if (Double.compare(arrivalTrain, currentTime) == 0) {
                            tempSize = training.size();
                        } else {
                            tempSize = training.size() + 1;
                        }
                        if (tempSize > trainingSize) {
                            trainingSize = tempSize;
                        }

                        player1.train(timeTrain, trainWait);
                        events.add(new Event("lt", tempTrain, player1.getID()));
                        nofCoach--;
                    }
                }
                case "lm" -> {              // if event is leaving massage
                    player.available();
                    nofMasseur++;
                    while (!massages.isEmpty() && nofMasseur>0) {
                        Massage massage = massages.poll();
                        double masDuration = massage.getDuration();
                        double masArrival = massage.getArrivalTime();
                        double masWaitTime = currentTime - masArrival;
                        double masTempTime = currentTime + masDuration;
                        Player masPlayer = players.get(massage.getPlayerID());
                        int tempSize;
                        if (Double.compare(masArrival, currentTime) == 0) {
                            tempSize = massages.size();
                        } else {
                            tempSize = massages.size() + 1;
                        }
                        if (tempSize > massageSize) {
                            massageSize = tempSize;
                        }


                        masPlayer.massage( masDuration, masWaitTime);
                        events.add(new Event("lm", masTempTime, masPlayer.getID()));   // adding leaving massage event
                        nofMasseur--;

                    }
                }
                case "lp" -> {   // if event is leaving physiotherapy
                    player.available();
                    event.getPhysiotherapist().available();
                    physiotherapists.add(event.getPhysiotherapist());
                    while (!physiotherapy.isEmpty() && !physiotherapists.isEmpty() && physiotherapists.peek().availability) {
                        Physiotherapy physiotherapy1 = physiotherapy.poll();
                        Player physioPlayer = players.get(physiotherapy1.getPlayerID());
                        double physioArrival = physiotherapy1.getArrivalTime();

                        int tempSize;
                        if (Double.compare(physioArrival, currentTime) == 0) {
                            tempSize = physiotherapy.size();
                        } else {
                            tempSize = physiotherapy.size() + 1;
                        }
                        if (tempSize > physioSize) {
                            physioSize = tempSize;
                        }
                        Physiotherapist physiotherapist = physiotherapists.poll();
                        double serviceTime = physiotherapist.getServiceTime();
                        double physioTempTime = currentTime + serviceTime;
                        double physioWait = currentTime - physioArrival;
                        physioPlayer.physiotherapy(serviceTime, physioWait);
                        physiotherapist.busy(currentTime, physioTempTime);
                        events.add(new Event("lp", physioTempTime, physiotherapy1.getPlayerID(), physiotherapist));
                    }
                }

                case "t" -> {           // if event is training
                    if ((player.checkAvailability(currentTime))) {
                        player.inQueue = true;
                        training.add(new Training(ID, arrivalTime, eventDuration));
                    } else {
                        canceledAttempts++;
                    }
                    int tempSize;

                    while (!training.isEmpty() && nofCoach > 0) {
                        Training train = training.poll();
                        double duration = train.getDuration();
                        double trainWT = currentTime-train.getArrivalTime();

                        if (Double.compare(train.getArrivalTime(), currentTime) == 0) {
                            tempSize = training.size();
                        } else {
                            tempSize = training.size() + 1;
                        }
                        if (tempSize > trainingSize) {
                            trainingSize = tempSize;
                        }

                        player.train( duration, trainWT);
                        events.add(new Event("lt", eventTempTime, ID));  // adding leaving massage event
                        nofCoach--;
                    }
                }
                case "m" -> {   // if event is massage
                    int skillLevel = players.get(ID).getSkillLevel();
                    if (player.validityCheck()) {
                        if (player.checkAvailability(currentTime)) {
                            player.increaseMassage();
                            player.inQueue = true;
                            massages.add(new Massage(ID, arrivalTime, eventDuration, skillLevel));
                        } else {
                            canceledAttempts++;
                            break;
                        }

                    } else {
                        invalidAttempts++;
                        break;
                    }
                    int tempSize = massages.size();
                    if (tempSize > massageSize) {
                        massageSize = tempSize;
                    }
                    while (!massages.isEmpty() && nofMasseur>0) {
                        Massage massage = massages.poll();
                        player.massage( eventDuration, eventWaitTime);
                        events.add(new Event("lm", eventTempTime, ID));
                        nofMasseur--;
                    }
                }
                case "p" -> {           // if event is physiotherapy
                    double trainingTime = player.trainingTime;
                    player.inQueue = true;
                    Physiotherapy physio = new Physiotherapy(arrivalTime, ID, trainingTime);
                    physiotherapy.add(physio);

                    int tempSize = physiotherapy.size();
                    if (physiotherapy.peek().equals(physio)) {
                        if (!physiotherapists.isEmpty() && physiotherapists.peek().availability) {
                            tempSize = physiotherapy.size() - 1;
                        }
                    }
                    if (tempSize > physioSize) {
                        physioSize = tempSize;
                    }

                    while (!physiotherapy.isEmpty() && !physiotherapists.isEmpty() && physiotherapists.peek().availability) {
                        Physiotherapy physiotherapy1 = physiotherapy.poll();
                        Physiotherapist physiotherapist = physiotherapists.poll();
                        double serviceTime = physiotherapist.getServiceTime();
                        double physioTempTime = currentTime + serviceTime;
                        player.physiotherapy(serviceTime, eventWaitTime);
                        physiotherapist.busy(currentTime, eventTempTime);
                        events.add(new Event("lp", physioTempTime, ID, physiotherapist));   // adding leaving physiotherapy event

                    }
                }
            }
        }

        out.println(trainingSize);
        out.println(physioSize);
        out.println(massageSize);
        double trainingWT = 0.0;
        double trainingTime =0.0;
        double avrTrainingWT = 0.0;
        double avrTraining = 0.0;
        double physioWT = 0.0;
        double avrPhysioWT = 0.0;
        double avrPhysio = 0.0;
        double physioTime = 0.0;
        double massageWT = 0.0;
        double massageTime = 0.0;
        double avrMassage = 0.0;
        double avrMassageWT = 0.0;
        double turnAround;
        double maxPhysioWT = 0.0;
        int maxPhysioID = 0;
        double leastMassageWT = Double.POSITIVE_INFINITY;
        int leastMassageID = 0;
        boolean isThree = false;

        int nofTraining = 0;
        int nofMassage = 0;
        int nofPhysiotherapy = 0;
        for (Player player : players) {
            trainingWT += player.trainingWT;
            physioWT += player.physiotherapyWT;
            massageWT += player.massageWT;
            nofTraining += player.nofTraining;
            nofMassage += player.nofMassage;
            nofPhysiotherapy += player.nofPhysio;
            trainingTime += player.totTraTime;
            physioTime += player.totPhysTime;
            massageTime += player.totMasTime;
            if (maxPhysioWT<player.physiotherapyWT){
                maxPhysioWT = player.physiotherapyWT;
                maxPhysioID = player.getID();
            }
            if (player.massageAttempts == 3){
                isThree = true;
                if (leastMassageWT > player.massageWT){
                    leastMassageWT = player.massageWT;
                    leastMassageID = player.getID();
                }
            }


        }
        turnAround = trainingWT + trainingTime + physioTime +physioWT;
        if (nofTraining == 0){
            avrTrainingWT = 0.000;
            avrPhysioWT = 0.000;
            avrPhysio = 0.000;
            avrTraining = 0.000;
            turnAround = 0.000;
        }
        else {
            avrTrainingWT = trainingWT / (double) (nofTraining);
            avrPhysioWT = physioWT / (double) (nofPhysiotherapy);
            avrTraining = trainingTime / (double) (nofTraining);
            avrPhysio = physioTime / (double) (nofPhysiotherapy);
        }

        if (nofMassage == 0){
            avrMassageWT = 0.000;
            avrMassage =  0.000;
        }
        else {
            avrMassageWT = massageWT / (double) (nofMassage);
            avrMassage = massageTime / (double) (nofMassage);
        }





        out.println(String.format("%.3f",avrTrainingWT));
        out.println(String.format("%.3f",avrPhysioWT));
        out.println(String.format("%.3f",avrMassageWT));
        out.println(String.format("%.3f",avrTraining));
        out.println(String.format("%.3f",avrPhysio));
        out.println(String.format("%.3f",avrMassage));
        out.println(String.format("%.3f",(turnAround/(double)(nofTraining))));
        out.println(maxPhysioID+" "+String.format("%.3f",maxPhysioWT));
        if (isThree){
            out.println(leastMassageID+" "+String.format("%.3f",leastMassageWT));
        }
        else {
            out.println("-1" +" "+"-1");
        }
        out.println(invalidAttempts);
        out.println(canceledAttempts);
        out.println(String.format("%.3f",currentTime));
    }
}

