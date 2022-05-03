package assighment_2;
import java.awt.EventQueue;
import java.awt.Window;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.*;

public class Network{

	public static Scanner input = new Scanner(System.in);
                     public static Device client=new Device();
	public static ArrayList<Boolean> state = new ArrayList<Boolean>();
	public static int N;
	public static int TC;
	public static String ch;

	public static void main(String[] args) throws InterruptedException, IOException {
		System.out.println("Welcome, Enter 1 for Console ");
		ch = input.next();
		if (ch.equals("1")) {

			System.out.println("What is number of WI-FI Connections?");
			N = input.nextInt();

			System.out.println("What is number of devices clients want to connect?");
			TC = input.nextInt();

			String name = "";
			String type = "";

			for (int i = 1; i <= TC; i++) {
                                                                System.out.println("Enter name and the type of devices clients : "+i );
				name = input.next();
				type = input.next();

				client.addNewClints(name, type);
			}	

			Router routerClass = new Router();
			routerClass.connect();

		} 

	}

           public synchronized static int connectionNumber(String name) {
                     int connectionNum = 1;
                     
                    for (int i = 0; i <N; i++) {
                        if (name.equals(client.getClintsName(i))) {
                                connectionNum = i + 1;
                                return connectionNum;
                        }
	}
            return connectionNum;       
    }
}
