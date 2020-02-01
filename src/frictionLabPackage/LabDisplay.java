package frictionLabPackage;

import javax.swing.*;
import com.fazecast.jSerialComm.SerialPort;

public class LabDisplay {
	PortSetupPanel portSelector;
	static SerialPort dataPipe;
	static boolean cont;
	static JFrame window;
	static double objectLength;
	
	public LabDisplay() { 						//constructor to setup visual window parameters
		window = new JFrame("Friction Lab");
		window.setSize(1600,900);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		run();									//runs sequence from setup to finish
	}
	
	public void run() {
		portSet();								//calls each part of the program in order
		System.out.println("Ports Set");
		experimentSet();
		System.out.println("Param Set");
		experimentDisp();
		System.out.println("Exp Finished");
		exportData();
		System.out.println("Data Exported");
	}
	
	public void portSet() {						//asks user to help setup port where information will come from
		cont = false;							//boolean that keeps window up until user has made a selection
		portSelector = new PortSetupPanel();	//makes a new port selector to show options to user
		window.add(portSelector);				//adds port selector to the window
		window.revalidate();						
		for(int i = 0 ; !cont ; i++) {			//loop that waits until user has made a selection
			System.out.print("rep");								//body required for loop???????
		}
		window.remove(portSelector);			//clears window of used panel to prepare for next step
	}
	
	public static void experimentSet() {				//same framework as port setup method
		cont = false;
		ExperimentSetupPanel setup = new ExperimentSetupPanel();
		window.add(setup);
		window.revalidate();
		for(int i = 0 ; !cont ; i++) {
			System.out.print("rep1");
		}
		window.remove(setup);
	}
	
	public void experimentDisp() {
		cont = false;
		SpeedDisplayPanel tracker = new SpeedDisplayPanel();
		window.add(tracker);
		window.revalidate();
		tracker.execute();
		for(int i = 0 ; !cont ; i++) {			//loop that waits until user has made a selection
			System.out.print("rep2");							//body required for loop???????
		}
		window.remove(tracker);
	}
	
	public void exportData() {
		//export recorded data to csv
	}
	
}
