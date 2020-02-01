package frictionLabPackage;

import java.awt.event.*;
import javax.swing.*;

public class ExperimentSetupPanel extends JPanel {
	static double objectLength;
	JLabel prompt;
	JTextField input;
	JButton enter;
	BoxLayout stack;
	public ExperimentSetupPanel() {
		prompt = new JLabel("Please enter the length of the sliding object in meters"); //IMPORTANT: Object length cannot be longer than distance between speed gates
		input = new JTextField("0.05");
		enter = new JButton("Enter"); 
		enter.addActionListener(new enterButtonListener());
		enter.setActionCommand("e");
		stack = new BoxLayout(this, BoxLayout.Y_AXIS);
		//input.setSize(200,50);
		this.setSize(600,400);
		this.setLayout(stack);
		this.add(prompt);
		this.add(input);
		this.add(enter);
	}
	class enterButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			if(a.getActionCommand().equals("e")) {
				LabDisplay.objectLength = Double.parseDouble(input.getText());
				System.out.println("enter pressed");
				LabDisplay.cont = true;
			}
		}
	}
}
