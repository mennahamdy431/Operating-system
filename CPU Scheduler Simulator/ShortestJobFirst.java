package CPU_Simulator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ShortestJobFirst {

    static CPU cpu = new CPU();

    private static int NumOfProcess = 0;
    static Process[] Processes;
    static Queue<Process> readyQue = new LinkedList<Process>();
    static Queue<Process> completed = new LinkedList<Process>();

    ShortestJobFirst() {
    }

    void RUN() {
        Inputs();
        OrderFirst();

        int T = 0;

        while (completed.size() != NumOfProcess) {
            Receive(T);
            if (readyQue.size() != 0)
                if (readyQue.peek().burst < cpu.CurrentProcess.burst && cpu.CurrentProcess.burst > 0) {


                    Insert(cpu.CurrentProcess);
                    cpu.CurrentProcess = readyQue.poll();
                }
            if (cpu.CurrentProcess.burst < 0) {
                if (readyQue.size() != 0) {
                    cpu.CurrentProcess = readyQue.poll();
                }
            }
            if (cpu.CurrentProcess.burst == 0) {
                CompletedQue(cpu.CurrentProcess, T);
                if (readyQue.size() == 0) cpu.CurrentProcess = new Process();
                else cpu.CurrentProcess = readyQue.poll();
            }
            if (cpu.CurrentProcess.burst > 0) {
                cpu.CurrentProcess.burst--;
                WaitedTime();
            }

            T++;
        }
        print();
    }

    static void Inputs() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter number of Processes: ");
        NumOfProcess = scan.nextInt();
        Initialization();


        System.out.println("Enter processes names: ");
        for (int x = 0; x < NumOfProcess; x++) {
            Processes[x].name = scan.next();
        }
        System.out.println("Enter processes Arrival times: ");
        for (int x = 0; x < NumOfProcess; x++) {
            Processes[x].arrival = scan.nextInt();
        }
        System.out.println("Enter processes Burst times: ");
        for (int x = 0; x < NumOfProcess; x++) {
            Processes[x].burst = scan.nextInt();
        }
    }

    static void Receive(int arrival_T) {
        for (int x = 0; x < NumOfProcess; x++) {
            if (Processes[x].arrival == arrival_T) {
                Insert(Processes[x]);
            }
        }
    }

    private static void OrderFirst() {
        for (int x = 0; x < NumOfProcess - 1; x++) {
            for (int i = 0; i < NumOfProcess - 1; i++) {

                if (Processes[i].arrival > Processes[i + 1].arrival) {
                    Process temp = Processes[i];
                    Processes[i] = Processes[i + 1];
                    Processes[i + 1] = temp;
                }
            }
        }
    }

    static void CompletedQue(Process p, int T) {
        completed.offer(p);
        p.completion = T;
        p.setTurnaround();
    }

    static void Insert(Process p) {
        if (readyQue.size() == 0) {
            readyQue.offer(p);
            return;
        }

        Process temp;
        boolean inserted = false;
        int n = readyQue.size();
        for (int x = 0; x < n; x++) {
            temp = readyQue.poll();
            if (p.burst < temp.burst && !inserted) {
                inserted = true;
                readyQue.offer(p);
            }
            readyQue.offer(temp);
        }
        if (!inserted) {
            readyQue.offer(p);
        }
    }

    static void Initialization() {
        Processes = new Process[NumOfProcess];
        for (int x = 0; x < NumOfProcess; x++) {
            Processes[x] = new Process();
        }
    }

    static void print() {
        System.out.println("=====================SJF====================");

        System.out.println("----------------------------------");
        for (Process p : completed) {
            System.out.print(p.name + " |");
        }
        System.out.println("\n----------------------------------");

        System.out.println("Process     Waiting Time   TurnaroundTime ");
        for (Process p : Processes) {
            int currWT = p.waited, currTT = p.turnaround;
            System.out.println(p.name + "\t\t\t\t" + currWT + "\t\t\t\t" + currTT);
        }
        System.out.print("Average Waiting Time: ");
        System.out.println(" =" + AverageWaitingTime());
        System.out.print("Average Turnaround Time: ");
        System.out.println(" =" + AverageTurnaround());

    }

    static void WaitedTime() {
        int n = readyQue.size();
        for (Process p : readyQue) {
            p.waited++;
        }
    }

    static float AverageTurnaround() {
        float sum = 0;
        for (Process p : completed) {
            sum += p.turnaround;
        }
        return (float) sum / NumOfProcess;
    }

    static float AverageWaitingTime() {
        float sum = 0;
        for (Process p : completed) {
            sum += p.waited;
        }
        return (float) sum / NumOfProcess;
    }


}
