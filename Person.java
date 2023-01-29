import java.awt.Point; 
import java.awt.Graphics; 
import java.awt.Color; 

public class Person {

   private int state; 
   private Point pos; 
   private int dirX; 
   private int dirY; 
   private double daysInfected; 
   
   public Person() {
      state = 0; 
      daysInfected = 0; 
      pos = new Point((int) (Math.random()*300 + 50), (int) (Math.random()*300 + 50));
      
      dirX = (int) (Math.random()*4 - 2);
      dirY = (int) (Math.random()*4 - 2);
      
      while (dirX == 0 || dirY == 0) {
         dirX = (int) (Math.random()*4 - 2);
         dirY = (int) (Math.random()*4 - 2);
      }
   }
   
   public double getDaysInfected() {
      return daysInfected; 
   }
   
   public void setDaysInfected(double days) {
      daysInfected = days; 
   }
   
   public void checkDaysInfected(double days) {
      if (state == 1 && daysInfected > days) {
         state = 2; 
      }
   }
   
   public Point getPos() {
      return pos;
   }
   
   public int getState() {
      return state; 
   }
   
   public void setState(int s) {
      state = s; 
   }
   public void move() {
      
      if ((pos.y + dirY) < 45 || (pos.y - dirY) > 350) {
         
         dirY = -dirY; 
         
         int randNum = (int) (Math.random()*4 - 2);
         
         System.out.println(randNum); 
         
         if ((dirY + randNum) != 0 && Math.abs(dirY) <= 2) {
            dirY += randNum;
         } else if (Math.abs(dirY) >= 2) {
            dirY /= 2; 
         }
         
         if (pos.y < 45)
            pos.y = 45; 
            
         if (pos.y > 350)
            pos.y = 350; 
         
         if (dirX == 0) 
            dirX = 1;
         
         if (dirY == 0)
            dirY = 1;  
         
      }

      else if ((pos.x - dirX) < 45 || (pos.x + dirX) > 350) {
         dirX = -dirX;
         
         if (pos.x < 45) 
            pos.x = 45; 
         
         if (pos.x > 350) 
            pos.x = 350; 
         
         int randNum = (int) (Math.random()*4 - 2);
         
         if ((dirX + randNum) != 0 && Math.abs(dirX) <= 2) {
            dirX += randNum;
         } else if (Math.abs(dirY) >= 2) {
            dirX /= 2; 
         }
         
      }
            
      pos.x += dirX;
      pos.y += dirY; 
      
      if (dirX == 0) 
         dirX = 1;
         
      if (dirY == 0)
         dirY = 1;  

      
   }
   
   public void draw(Graphics g) {
   
      if (state == 0) {
         g.setColor(Color.BLUE);
      } else if (state == 1) {
         g.setColor(Color.RED);
      } else if (state == 2) {
         g.setColor(Color.GRAY);
      }
      
      g.fillOval(pos.x, pos.y, 5, 5); 
   }
   
   public boolean checkPointInGrid(Point p) {
      return (p.x >= 40 && p.x < 360 
           && p.y > 40 && p.y < 360); 
   }
   
   public void infectPerson(Person p) {
      if (p.getState() == 1 && this.state != 2) {
         this.state = 1; 
      } else if (p.getState() == 0 && this.state == 1) {
         p.setState(1); 
      }
   }
   
}