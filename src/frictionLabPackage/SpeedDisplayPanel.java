package frictionLabPackage;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.Border;

import com.fazecast.jSerialComm.SerialPort;

public class SpeedDisplayPanel extends JPanel {
	SerialPort reader;
	String v1;
	String v2;
	JLabel v1Label;
	JLabel v2Label;
	Scanner input;
	
	String[] names;
	ArrayList<String> speedData;
	JScrollPane displayTable;
	
	public SpeedDisplayPanel () {
		reader = LabDisplay.dataPipe;
		v1 = "";
		v2 = "";
		v1Label = new JLabel();
		v2Label = new JLabel();
		this.setSize(200,200);
		this.add(v1Label);
		this.add(v2Label);
		if(reader.openPort()) 
			System.out.println("Port opened successfully");
		else 
			System.out.println("Failed to open port, condier unplugging the device and trying again");
		reader.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
		input = new Scanner(reader.getInputStream());
		System.out.println("ready");
		
		names = new String[3];
		names[0] = "Trial Number";
		names[1] = "Speed 1";
		names[2] = "Speed 2";
		speedData = new ArrayList<String>();
		displayTable = new JScrollPane();
		this.add(displayTable);
	}
	
	public void execute() {
		while(input.hasNextLine() || LabDisplay.cont) {
			try {
				v1 = input.nextLine();
				v2 = input.nextLine();
			}catch(Exception e) {
				System.out.println("Error in reading data, please restart");
			}
			v1Label.setText("V1 : " + v1);
			v2Label.setText("V2 : " + v2);
			speedData.add(v1.toString() + "," + v2.toString());
			String[][] printData = new String[speedData.size()][3];
			for(int i = 0 ; i < speedData.size() ; i++) {
				int comma = speedData.get(i).indexOf(',');
				for(int j = 0 ; j < 3 ; j++) {
					if(j == 0)
						printData[i][j] = Integer.toString(i+1);
					else if(j == 1)
						printData[i][j] = speedData.get(i).substring(0,comma);
					else if(j == 2)
						printData[i][j] = speedData.get(i).substring(comma+1);
				}
			}
			displayTable = new JScrollPane(new JTable(printData,names));
			LabDisplay.window.add(displayTable);
			LabDisplay.window.revalidate();
		}
		System.out.println("Speed Printer Finsihed");
	}
}
