public class Physiotherapy implements Comparable<Physiotherapy>{
    private double arrivalTime;
    private int playerID;
    private double PlayerTrTime;

    public Physiotherapy(double arrivalTime, int playerID, double playerTrTime) {
        this.arrivalTime = arrivalTime;
        this.playerID = playerID;
        this.PlayerTrTime = playerTrTime;
    }

    public int compareTo(Physiotherapy physiotherapy) {
        if (Double.compare(this.PlayerTrTime,physiotherapy.PlayerTrTime) == 0) {
            if (Double.compare(this.arrivalTime,physiotherapy.arrivalTime) == 0){
                return this.playerID-physiotherapy.playerID;
            }
            return Double.compare(this.arrivalTime,physiotherapy.arrivalTime);
        }
        return -(Double.compare(this.PlayerTrTime,physiotherapy.PlayerTrTime));
    }

    public boolean equals(Physiotherapy physiotherapy){
        if (this.arrivalTime == physiotherapy.arrivalTime && this.PlayerTrTime == physiotherapy.PlayerTrTime && this.getPlayerID() == physiotherapy.getPlayerID()){
            return true;
        }
        return false;


    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public double getPlayerTrTime() {
        return PlayerTrTime;
    }

    public void setPlayerTrTime(double playerTrTime) {
        PlayerTrTime = playerTrTime;
    }
}

