public class Physiotherapist implements Comparable<Physiotherapist>{
    private final int ID;
    private final double serviceTime;
    public boolean availability ;
    public double start;
    public double finish;

    public Physiotherapist(int ID, double serviceTime){
        this.ID = ID;
        this.serviceTime = serviceTime;
        this.availability = true;
        this.start = 0.0;
        this.finish = 0.0;
    }

    public int getID() {
        return ID;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public int compareTo(Physiotherapist physiotherapist) {
        if(this.availability) {
            if (physiotherapist.availability) {
                return this.ID - physiotherapist.ID;
            }
            return 1;
        }
        else {
            if (physiotherapist.availability) {
                return -1;
            }
            return this.ID - physiotherapist.ID;
        }
    }
    public void busy(double current, double finish) {  // makes physiotherapist busy
        this.start = current;
        this.finish = finish;
        this.availability = false;
    }
    public void available(){                // makes physiotherapist available
        this.start = 0.0;
        this.finish = 0.0;
        this.availability = true;
    }
}


