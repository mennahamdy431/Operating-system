package CPU_Simulator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) {
        boolean work = true;
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose the desired algorithm to run\n" +
                "1. Priority Scheduling\n" +
                "2. Shortest-Job First (SJF)\n" +
                "3. Shortest-Remaining Time First (SRTF) \n" +
                "4. Round Robin\n" +
                "5. Exit");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                Priority scheduler1 = new Priority();
                scheduler1.RUN();
                break;
            case 2:
                ShortestJobFirst scheduler2 = new ShortestJobFirst();
                scheduler2.RUN();

                break;
            case 3:
                SRTF scheduler3 = new SRTF();
                scheduler3.RUN();
                break;
            case 4:
                RoundRobin scheduler4 = new RoundRobin();
                scheduler4.RUN();
            case 5:
                work = false;
                break;
            default:
                System.out.println("Choose 1-4 based on desired algorithm");
        }

    }
}
