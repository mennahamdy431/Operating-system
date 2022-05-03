package CPU_Simulator;

public class Process {
    public String name;
    public int arrival;
    public int burst;
    public int completion;
    public int turnaround;
    public int waited;
    public int priority;
    public int starve;

    Process() {
        name = "";
        waited = 0;
        turnaround = 0;
        arrival = -1;
        burst = -1;

        starve = 0;
    }

    public void setTurnaround() {
        turnaround = completion - arrival;
    }


    public Process(String name, int arrival_time, int burst_time, int priority) {
        this.name = name;
        this.burst = burst_time;
        this.arrival = arrival_time;
        this.priority = priority;
    }


    public int getPriority() {
        return priority;
    }

    public int getArrival() {
        return arrival;
    }

}