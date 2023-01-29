import java.awt.Point;
import java.awt.Dimension;
import java.util.ArrayList; 
import java.awt.Graphics; 
import javax.swing.JPanel;
import java.awt.Color; 
import java.awt.Font;
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent; 
import java.awt.geom.Point2D;

public class SimulationPanel extends JPanel {

   private ArrayList<Person> persons;

   public SimulationPanel() {
   
      this.persons = new ArrayList(); 
      
      setPreferredSize(new Dimension(400, 400));
      
   }
   
   public void moveAll() {
      for (Person per : persons) {
         per.move(); 
         repaint(); 
      }
   } 
   
   public ArrayList<Person> getPersons() {
      return persons; 
   }
   
   public void setPersons(ArrayList<Person> p) {
      persons = p; 
   }
   
   @Override
   public void paintComponent(Graphics g) {
      g.fillRect(0, 0, 400, 400); 
      g.setColor(Color.WHITE); 
      g.drawRect(40, 40, 320, 320); 
      
      for (Person per : persons) {
         per.draw(g);
      } 
      
      for (int i = 0; i < 3; i++) {
         g.setFont(new Font(Font.SANS_SERIF, 10, 16));
         if (i == 0) {
            g.setColor(Color.BLUE);
            g.drawString("Susceptible", i*(320/3) + 56, 385);
         } else if (i == 1) {
            g.setColor(Color.RED);
            g.drawString("Infected", i*(320/3) + 56, 385);
         } else if (i == 2) {
            g.setColor(Color.GRAY);
            g.drawString("Recovered", i*(320/3) + 56, 385);
         }
         
         g.fillOval(i*(320/3) + 40, 370, 15, 15);
         
      }
      
   }
   
   public void infectPersons(int r, int chance) {
      for (int i = 0; i < persons.size(); i++) {
         for (int j = 1; j < persons.size(); j++) {
            if (calcDistance(persons.get(i).getPos().x, persons.get(i).getPos().y, persons.get(j).getPos().x, persons.get(j).getPos().y) < r) {
            
               int randNum = (int) (Math.random()*100 + 1); 
               
               if (chance <= randNum) 
                  persons.get(i).infectPerson(persons.get(j)); 
                  
            }
         }
      }
   }
   
   public void checkInfectedPersons(int days) {
      for (int i = 0; i < persons.size(); i++) {
         persons.get(i).checkDaysInfected(days); 
      }
   }
   
   public int calcDistance(int x1, int y1, int x2, int y2) {
      return (int) (Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2))); 
   }
   
   public void clearPanel() {
      persons.clear(); 
      repaint(); 
   }  
   
   public void callRepaint() {
      repaint(); 
   }
   
} 