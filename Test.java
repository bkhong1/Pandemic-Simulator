// Author: Bradley Khong
// HN Adv Prog 1/16/23: Test
// Purpose: Allows the user to run the project and initializes key 
// components of the application (graph and inputPanel) 

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.geom.Point2D;
import java.awt.Color; 

public class Test {
   
   //top value of x-axis
   private static final int MAX_X_VALUE = 1000; 
   
   //top value of y-axis
   private static final int MAX_Y_VALUE = 100;
   
   //difference between increments (x-axis)
   private static final int INCREMENT_X_SIZE = 100; 
   
   //difference between increments (y-axis)
   private static final int INCREMENT_Y_SIZE = 10;
   
   public static void main(String[] args) {
  
     MyFrame frame = new MyFrame("Pandemic Simulation"); 
     
     ArrayList<Point2D> pointsArray = new ArrayList<Point2D>();
     
     // initialize graph of size 400 x 400 and increment size 50 
     Graph screenGraph = new Graph(new Point(0, 0), pointsArray, MAX_X_VALUE, 
                                   MAX_Y_VALUE, INCREMENT_X_SIZE, INCREMENT_Y_SIZE, Color.RED, true); 

     SimulationPanel p = new SimulationPanel(); 
     
     // adds graph and input panel 
     frame.add(new InputPanel(screenGraph, p), BorderLayout.LINE_START);
     frame.add(p);
     frame.add(screenGraph, BorderLayout.LINE_END);
     
     frame.setVisible(true);
     
  }
  
  public String toString() {
      return String.format("Max X Value: %d\n Max Y Value: %d\n" +  
                           "Increment Size X\n Increment Size Y,", 
                           MAX_X_VALUE, MAX_Y_VALUE, INCREMENT_X_SIZE, INCREMENT_Y_SIZE); 
  }
  
}