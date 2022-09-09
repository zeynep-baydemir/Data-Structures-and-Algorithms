public class Training implements Comparable<Training>{
    private double arrivalTime;
    private int playerID;
    private double duration;

    public double getArrivalTime() {
        return arrivalTime;
    }

    public int getPlayerID() {
        return playerID;
    }

    public double getDuration() {
        return duration;
    }

    public Training(int playerID, double arrivalTime, double duration){
        this.playerID = playerID;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
    }
    public int compareTo(Training training){
        if (Double.compare(this.arrivalTime,training.arrivalTime)!=0){
            return Double.compare(this.arrivalTime,training.arrivalTime);
        }
        else{
            return this.playerID- training.playerID;
        }
    }
}
