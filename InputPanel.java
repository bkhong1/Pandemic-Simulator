// Author: Bradley Khong
// HN Adv Prog 1/16/23: InputPanel 
// Creates an Input panel so that user can interact with application 

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension; 
import javax.swing.JTextField;
import javax.swing.JLabel; 
import javax.swing.JButton; 
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent; 
import javax.swing.Timer; 
import java.awt.Point; 

public class InputPanel extends JPanel implements ActionListener {
   
   // text field to enter initial velocity
   private JTextField velInput;
   
   // text field to enter initial angle 
   private JTextField angleInput;
   
   // text field to enter initial height 
   private JTextField heightInput;
   
   private JTextField percentInput; 
   
   // labels for each text field
   private JLabel vel;
   private JLabel angle;
   private JLabel height;
   private JLabel sPercent; 
   
   // buttons to launch projectile and clear projectile off screen 
   private JButton launchButton;
   private JButton clearButton;
   
   // stores Graph and Projectile object 
   private Graph screenGraph; 
   
   private SimulationPanel p; 
   
   // creates a timer object
   private Timer timer; 
   
   // stores the time passed while projectile is in motion 
   private int timePassed; 
   
   // creates labels for output values once reaching the ground 
   
   private final int WIDTH = 150; // Input panel width
   private final int HEIGHT = 100; // Input Panel Height
   
   private int numSusceptible;
   private int numInfected;
   private int numRemoved; 

   public InputPanel (Graph screenGraph, SimulationPanel p) {
      
      this.setLayout(null);
      setPreferredSize(new Dimension(WIDTH, HEIGHT));
      
      this.p = p; 
      
      // initializes the fields for input 
      velInput = createInputField(20, 10, "Infection Radius");
      angleInput = createInputField(20, 60, "Chance of Infection");
      heightInput = createInputField(20, 110, "Days to Recover");
      percentInput = createInputField(20, 160, "Initial % Infected"); 
   
      // initalizes the buttons 
      launchButton = createButton(20, 220, "Start");
      clearButton = createButton(20, 260, "Clear");
      
      // turns off the clear button
      clearButton.setEnabled(false); 
      
      this.screenGraph = screenGraph;
      
   }
   
   // creates a textField AND a label where you can put text describing the field 
   public JTextField createInputField(int xPos, int yPos, String labelText) {
      
      JLabel label = createLabel(xPos, yPos, labelText); 
      
      JTextField textField = new JTextField(10);
      add(textField); 
      
      textField.setBounds(xPos, yPos + 20, 110, 20);
      textField.addActionListener(this);
      
      return textField;
      
   }
   
   // creates a label, given the coordinates of the top left corner and the label's text 
   public JLabel createLabel(int xPos, int yPos, String labelText) {
   
      JLabel label = new JLabel(labelText);
      add(label);
      
      label.setBounds(xPos, yPos, 110, 20);
      
      return label; 
      
   }
   
   // creates a button with text on it 
   public JButton createButton(int xPos, int yPos, String labelText) {
   
      JButton button = new JButton(); 
      add(button); 
      
      button.setBounds(xPos, yPos, 110, 30);
      button.setText(labelText); 
      button.addActionListener(this); 
      
      return button;
      
   }
   
   // checks inputs to see if projectile can be launched 
   public boolean checkCanLaunch() {
      
      // stores each JTextField 
      JTextField[] textFieldArray = {velInput, angleInput, heightInput, percentInput};
      
      // checks each textField to see if there are numbers inputted 
      for (int i = 0; i < textFieldArray.length; i++) {
         
         // if a non-valid input is detected, then canLaunch is set to false
         // which will then have this function return false  
         if (!isNumeric(textFieldArray[i].getText())) {
            return false; 
         }
      
      }
   
      // returns true if angle is between 0 and 90 degrees and velocity is
      // greater than 0, returns false otherwise 
      return (Double.parseDouble(angleInput.getText()) >= 0 
              && Double.parseDouble(angleInput.getText()) >= 0
              && Double.parseDouble(heightInput.getText()) >= 0
              && Double.parseDouble(angleInput.getText()) <= 100);
      
   }
   
   /* 
      Checks if the argument is numeric or not 
      from CodeAhoy, "How to check if a string is Numeric in Java?"
      https://codeahoy.com/q/11/check-if-a-string-is-numeric-in-java
   */
   
   public static boolean isNumeric(String str) {
      
      // Double.parseDouble turns a double in a string
      // to an actual double. So, if it throws an exception, we know
      // that the string is not a number. 
      
      if (str == null) {
         return false;
      } 
      try {
         Integer.parseInt(str);
         return true;   
      } catch (NumberFormatException nfe) {
         return false;
      }
      
   }  
   
   // enables input if parameter is true and disables input if false
   // clear button is enabled when input is disabled and vice versa
   public void enableInput(boolean isEnabled) {
   
      velInput.setEnabled(isEnabled);
      angleInput.setEnabled(isEnabled);
      heightInput.setEnabled(isEnabled); 
      launchButton.setEnabled(isEnabled);
      percentInput.setEnabled(isEnabled);
      clearButton.setEnabled(!isEnabled);
      
   }
   
   //Triggered whenever any GUI is clicked 
   @Override 
   public void actionPerformed(ActionEvent e) {
      
      //Checks launched button is clicked and all fields are filled out 
   
      if (e.getSource().equals(launchButton) && checkCanLaunch()) {
         startProgram(30); 
         enableInput(false); 
         timer.start(); 
      } else if (e.getSource().equals(clearButton)) {
         reset();
      }
      
   }
   
   // resets textFields, output labels, graph, and time passed 
   public void reset() {
      
      resetJTextFields(); 
      
      screenGraph.clearGraph(); 
      p.clearPanel(); 
      timer.stop();
      enableInput(true); 
      timePassed = 0; 
      
   }
   
   // makes the text fields blank 
   public void resetJTextFields() {
   
      JTextField[] textFieldArray = {velInput, angleInput, heightInput, percentInput};
      
      for (JTextField field : textFieldArray) {
         field.setText(""); 
      }
      
   }
   
      
   // creates a timer and updates the graph every time the timer is called
   // so that the projectile appears to be moving on the screen 
   private void startProgram(int ms) {
   
        // creates timer object that is called every millisecond 
      
      for (int i = 0; i < Integer.parseInt(percentInput.getText()); i++) {
         Person addPers = new Person();
         addPers.setState(1);
         p.getPersons().add(addPers); 
      }
      
      for (int i = 0; i < 100 - Integer.parseInt(percentInput.getText()); i++) {
         Person addPers = new Person();
         addPers.setState(0);
         p.getPersons().add(addPers); 
      }
      
      System.out.println(p.getPersons().size()); 
              
      timer = new Timer(ms, 
         
         new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
               
               int infPersons = 0;
               
               for (int i = 0; i < p.getPersons().size(); i++) {
                  if (p.getPersons().get(i).getState() == 1)
                     infPersons += 1;
                     
               }
               
               numInfected = infPersons; 
               
               //repaints the screen and stores timed passed 
               screenGraph.callRepaint(); 
               p.infectPersons(Integer.parseInt(velInput.getText()), Integer.parseInt(percentInput.getText()));
               p.checkInfectedPersons(Integer.parseInt(heightInput.getText())); 
               
               for (int i = 0; i < p.getPersons().size(); i++) {
                  if (p.getPersons().get(i).getState() == 1) {
                     p.getPersons().get(i).setDaysInfected(p.getPersons().get(i).getDaysInfected() + 0.1);
                  }
               } 
               
               screenGraph.addPoint(new Point(timePassed, numInfected));  
               
               timePassed += 1; 
               
               p.moveAll(); 
               
               if (numInfected == 0) {
                  timer.stop();
               }
               
            }
            
         });
   
   }
   
   public String toString() {
      return String.format("Input Angle: %.2f\n Input Velocity: %.2f\n Input Height: %.2f\n",
                           velInput.getText(), angleInput.getText(), heightInput.getText()); 
   }
   
}