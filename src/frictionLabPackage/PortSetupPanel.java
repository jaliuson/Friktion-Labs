package frictionLabPackage;

import java.awt.Color;
import java.awt.Desktop.Action;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import com.fazecast.jSerialComm.SerialPort;

public class PortSetupPanel extends JPanel{
	SerialPort[] devicePorts;	//array containing all available ports on the device
	JButton[] portButtons;		//contains buttons that will represent 1 port each
	GridLayout blocks;			
	SerialPort selectedPort;	//will contain user selected port
	
	public PortSetupPanel(){ 		
		devicePorts = SerialPort.getCommPorts();							//retrieves all possible ports to read from
		portButtons = new JButton[devicePorts.length];										
		JLabel prompt = new JLabel("Please select the correct port");		//instructs user of what to do
		this.add(prompt);
		blocks = new GridLayout(3,3,10,10);
		this.setLayout(blocks);												//assigns layout to panel
		for(int i = 0 ; i < portButtons.length ; i++) {						//creates and arranges as many buttons are available
			portButtons[i] = new JButton((String)devicePorts[i].getSystemPortName());
			portButtons[i].addActionListener(new SelectionListener());		//assigns action listener to each button with custom command
			portButtons[i].setActionCommand("button" + i);					//sets action command to corespond with that button
			this.add(portButtons[i]);										//adds each button to the panel
		}
	}
	
	class SelectionListener implements ActionListener{			//action listener for the possible ports
		public void actionPerformed(ActionEvent e) {			
			for(int i = 0 ; i < devicePorts.length ; i++) {
				if(e.getActionCommand().equals("button" + i)) {	//finds selected button
					LabDisplay.dataPipe = devicePorts[i];		//sets selected port to the one that the lab will read from
					LabDisplay.cont = true;						//"unlocks" loop in LabDisplay to continue to the next step
					System.out.println(LabDisplay.cont);
				}
			}
		}
	}
}
