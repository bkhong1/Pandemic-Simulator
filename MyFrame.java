// Author: Bradley Khong
// HN Adv Prog 1/16/23: MyFrame
// Sets up the frame of the application 

import java.awt.Color;
import javax.swing.JFrame;

public class MyFrame extends JFrame {
   
  //width of frame
  private static final int FRAME_WIDTH = 964;
  
  //height of frame
  private static final int FRAME_HEIGHT = 435; 
  
  // creates a frame with the above width and height 
  MyFrame(String title) {
  
    setTitle(title);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(FRAME_WIDTH, FRAME_HEIGHT);
    setLocationRelativeTo(null);     
    
  }
  
  public String toString() {
      return String.format("Frame width: %d\n Frame height: %d", 
                           FRAME_WIDTH, FRAME_HEIGHT); 
  }
  
}