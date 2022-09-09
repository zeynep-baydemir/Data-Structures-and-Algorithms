public class Event implements Comparable<Event>{
    private String eventType;
    private double arrivalTime;
    private double duration = 0.0;
    private int playerID;
    private int skillLevel = 0;
    private double trainingTime = 0;
    private Physiotherapist physiotherapist = null;

    public Physiotherapist getPhysiotherapist() {
        return physiotherapist;
    }

    public Event(String eventType, double arrivalTime, double duration, int playerID) {  //training
        this.eventType = eventType;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.playerID = playerID;

    }

    public Event (String eventType, double arrivalTime, double duration, int playerID, int skillLevel){ //massage
        this.eventType = eventType;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.playerID = playerID;
        this.skillLevel = skillLevel;
    }

    public Event (String eventType, double arrivalTime, int playerID, double trainingTime){ // physiotherapy
        this.eventType = eventType;
        this.arrivalTime = arrivalTime;
        this.playerID = playerID;
        this.trainingTime = trainingTime;
    }

    public Event (String eventType, double arrivalTime, int playerID){      // training and massage leaving
        this.eventType = eventType;
        this.arrivalTime = arrivalTime;
        this.playerID = playerID;
    }


    public Event (String eventType, double arrivalTime, int playerID, Physiotherapist physiotherapist){     // physiotherapy leaving
        this.eventType = eventType;
        this.arrivalTime = arrivalTime;
        this.playerID = playerID;
        this.physiotherapist = physiotherapist;
    }

    public int compareTo(Event event) {
        if (Double.compare(this.arrivalTime,event.arrivalTime)==0){
            if (this.eventType.equals(event.eventType)){
                if (this.eventType.equals("m")){
                    if((this.skillLevel == event.skillLevel)){
                        return (this.playerID-event.playerID);
                    }
                    return -(this.skillLevel-event.skillLevel);
                }
                else if (this.eventType.equals("t")){
                    return (this.playerID-event.playerID);
                }
                else if (this.eventType.equals("p")){
                    if ((Double.compare(this.trainingTime,event.trainingTime)==0)){
                        return (this.playerID-event.playerID);
                    }
                    return -(Double.compare(this.trainingTime,event.trainingTime));
                }
                else if (this.eventType.equals("lp")){
                    return (this.playerID-event.playerID);
                }
                return 1;
            }
            if (this.eventType.contains("l")){
                return -1;
            }
            return 1;
        }
        return Double.compare(this.arrivalTime,event.arrivalTime);
    }





    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public double getTrainingTime() {
        return trainingTime;
    }

    public void setTrainingTime(double trainingTime) {
        this.trainingTime = trainingTime;
    }




}
