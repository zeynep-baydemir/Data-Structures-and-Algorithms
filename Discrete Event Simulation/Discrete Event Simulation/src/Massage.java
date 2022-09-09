public class Massage implements Comparable<Massage>{
    private double arrivalTime;
    private int playerID;
    private int skillLevel;
    private double duration;

    public Massage(int playerID,double arrivalTime, double duration, int skillLevel){
        this.playerID = playerID;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.skillLevel = skillLevel;
    }
    public int compareTo(Massage massage){
        if (this.skillLevel == massage.skillLevel){
            if (Double.compare(this.arrivalTime,massage.arrivalTime)==0){
                return this.playerID- massage.playerID;
            }
            return Double.compare(this.arrivalTime,massage.arrivalTime);
        }
        else{
            return -(this.skillLevel- massage.skillLevel);
        }
    }



    public int getPlayerID() {
        return playerID;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public double getDuration() {
        return duration;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }



}

