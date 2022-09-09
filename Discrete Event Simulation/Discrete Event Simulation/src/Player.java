public class Player {
    private int ID;
    private int skillLevel;
    public double trainingTime = 0.0;
    public double trainingWT =0.0;
    public double massageWT = 0.0;
    public double physiotherapyWT =0.0;
    public int massageAttempts=0;
    public double totTraTime =0.0;
    public double totMasTime = 0.0;
    public double totPhysTime = 0.0;
    public int nofTraining =0;
    public int nofMassage = 0;
    public double nofPhysio = 0;
    public boolean inQueue = false;


    public Player(int ID, int skillLevel){
        this.ID = ID;
        this.skillLevel = skillLevel;
    }

    public void train( double duration, double waitingTime) {     // player trains
        this.trainingTime = duration;
        this.totTraTime+= duration;
        this.trainingWT += waitingTime;
        this.nofTraining++;
    }

    public void massage( double duration, double waitingTime){     // player gets massage
        this.totMasTime+= duration;
        this.massageWT += waitingTime;
        this.nofMassage++;
    }

    public void increaseMassage(){
        this.massageAttempts++;
    }   // increasing massage attempts

    public void physiotherapy( double duration, double waitingTime){    //player gets physiotherapy
        this.totPhysTime+= duration;
        this.physiotherapyWT += waitingTime;
        this.nofPhysio++;
    }

    public boolean checkAvailability(double currentTime) {   // checks whether player is in queue
        return !this.inQueue;
    }

    public void available(){
        this.inQueue = false;
    }    // makes player available

    public boolean validityCheck(){             //  checks whether player gets 3 massage
        if (this.massageAttempts == 3){
            return false;
        }
        else {
            return true;
        }
    }
    public int getID() {
        return ID;
    }

    public int getSkillLevel() {
        return skillLevel;
    }



}
