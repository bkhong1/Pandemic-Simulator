// Author: Bradley Khong
// HN Adv Prog 1/16/23: Graph
// Creates a graph with increments and axes 

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

public class Graph extends JPanel {

   // sets the graph size  
   private final Dimension GRAPH_SIZE = new Dimension(400, 400); 
   
   // sets the font for the increments 
   private final Font FONT = new Font(Font.SANS_SERIF, 10, 16);
   
   // sets the coordinates of top left corner of graph 
   private Point topLeftCorner;
   
   // sets the actual points inputted into the array
   private ArrayList<Point2D> pointsArray;  
   
   // stores the translated points, adjusting the points in the above array 
   // to scale onto the graph 
   private ArrayList<Point> translatedPoints;
   
   // stores the origin point of the graph (where the axes meet) 
   private Point origin;
   
   // stores the displayed max value on the x-axis
   private int maxXValue; 
   
   // stores the displaed max value on the y-axis
   private int maxYValue;
   
   // stores the difference between each increment (x-axis)
   private int incrementSizeX;
   
   // stores the difference between each increment (y-axis)
   private int incrementSizeY; 
   
   // stores the farthest x-coordinate from the origin
   private int farthestXCoord;
   
   // stores the farthest y-coordinate from the origin 
   private int farthestYCoord; 
   
   private Color color;  
   
   private boolean showAxes;
   
   public Graph(Point topLeftCorner, ArrayList<Point2D> pointsArray, int maxXValue, int maxYValue, int incrementSizeX, int incrementSizeY, Color color, boolean showAxes) {
   
      // initializes fields 
      this.topLeftCorner = topLeftCorner;
      this.pointsArray = pointsArray;
      this.maxXValue = maxXValue;
      this.maxYValue = maxYValue;
      this.incrementSizeX = incrementSizeX;
      this.incrementSizeY = incrementSizeY; 
      this.color = color; 
      this.showAxes = showAxes;
      
      // sets the origin point
      origin = new Point((int) (topLeftCorner.x + GRAPH_SIZE.width*.10), 
                         (int) (-topLeftCorner.y + GRAPH_SIZE.height*.90));
      
      // farthest X and Y coordinates from the origin
      farthestXCoord = (int) (topLeftCorner.x + GRAPH_SIZE.width*.90); 
      farthestYCoord = (int) (topLeftCorner.y + GRAPH_SIZE.height*.10);
      
      // translates the points given so that the points are at the right numbers on the graphs
      this.translatedPoints = generateTranslatedPointsArray(pointsArray); 
      
      // sets graph size and color of graph 
      setPreferredSize(new Dimension(400, 400)); 
      setBackground(Color.WHITE);
      
   }
   
   public int getMaxXValue() {
      return maxXValue;
   }
   
   public Point getOrigin() {
      return origin; 
   }
   
   public void setMaxXValue(int xVal) {
      maxXValue = xVal; 
   }
   
   public void setMaxYValue(int yVal) {
      maxYValue = yVal;
   }
   
   public void setIncrementSizeX(int incX) {
      incrementSizeX = incX; 
   }
   
   public void setIncrementSizeY(int incY) {
      incrementSizeY = incY; 
   }
   
   // adds a point onto the graph, both the actual both and translated point 
   public void addPoint(Point2D p) {
      pointsArray.add(p); 
      translatedPoints.add(generateTranslatedPoint(p));  
   }
   
   // draws increments 
   public void drawIncrements(Graphics g) {
      drawXIncrements(g); 
      drawYIncrements(g);   
   }
   
   // draws the increments on the x-axis 
   public void drawXIncrements(Graphics g) {
      
      g.setColor(Color.WHITE);
      g.setFont(FONT);
            
            // draws a String with the number on the graph for each increment 
      g.drawString("% Infected over Time (days)", 115, 25); 
      g.setColor(Color.WHITE);
      
      // stores the current increment value displayed on the graph 
      int displayX = 0;
      
      // stores current Y Coordinate on graph 
      int graphXCoord = origin.x; 
      
      // stores the distance between increments on the graph (in pixels) 
      int distBetweenIncrements = (int) ((origin.y - farthestYCoord) / ((double) maxXValue / incrementSizeX));
      
      // draws increments until the displayX reaches the maxYValue 
      
      while (displayX <= maxXValue) {
      
         // skips drawing if displayX is at the origin as origin is implied to be 0 
         if (displayX != 0) {
            
            // sets font 
            g.setFont(FONT);
            
            // draws a String with the number on the graph for each increment 
            g.drawString(displayInc(maxXValue, displayX, true), graphXCoord + adjustX(maxXValue) + 2, origin.y + 20);
            g.setColor(Color.WHITE); 
            
            // draws a line for each increment extending towards the graphs boundaries
            g.drawLine(graphXCoord, origin.y, graphXCoord, origin.y + 5); 
            g.setColor(Color.WHITE);
            
         }
         
         // moves on to the next increment 
         graphXCoord += distBetweenIncrements;  
         displayX += incrementSizeX;
         
      }
            
   }
   
   public String displayInc(int max, int display, boolean axes) {
   
      String displayI = Integer.toString(display); 
      String maxString = Integer.toString(max);   
      
      if (axes) {
         displayI = Integer.toString(display / 10); 
         maxString = Integer.toString(max / 10);
      }
      
      int diff = maxString.length() - displayI.length();
      
      for (int i = 0; i < diff; i++) {
         displayI = " " + displayI;
      }
      
      return displayI; 
      
   }
   
   public int adjustX(int max) {
      int adjustBy = 0; 
      String maxString = Integer.toString(max);
      int len = maxString.length();
      for (int i = 0; i < len; i++) {
         adjustBy -= 4; 
      }
      return adjustBy;
   }
   
   public int adjustY(int max) {
      int adjustBy = 0; 
      String maxString = Integer.toString(max);
      int len = maxString.length();
      for (int i = 0; i < len; i++) {
         adjustBy -= 11; 
      }
      return adjustBy;
   }
   
   // draws the increments on the y-axis 
   public void drawYIncrements(Graphics g) {
      
      // stores the current increment value displayed on the graph 
      int displayY = 0;
      
      // stores current Y Coordinate on graph 
      int graphYCoord = origin.y; 
      
      // stores the distance between increments on the graph (in pixels) 
      int distBetweenIncrements = (int) ((origin.y - farthestYCoord) / ( (double) maxYValue / incrementSizeY));
      
      // draws increments until the displayY reaches the maxYValue 
      while (displayY <= maxYValue) {
      
         // skips drawing if displayY is at the origin as origin is implied to be 0 
         if (displayY != 0) {
            
            // sets font 
            g.setFont(FONT);
            
            // draws a String with the number on the graph for each increment 
            g.drawString(displayInc(maxYValue, displayY, false), origin.x + adjustY(maxYValue), graphYCoord + 5);
            g.setColor(Color.WHITE); 
            
            // draws a line for each increment parellel extending towards the graphs boundaries
            g.drawLine(origin.x, graphYCoord, origin.x - 5, graphYCoord); 
            g.setColor(Color.WHITE);
            
         }
         
         // moves on to the next increment 
         graphYCoord -= distBetweenIncrements;  
         displayY += incrementSizeY;
         
      }
      
   }
   
   // draws each point 
   public void drawPoints(int pointRadius, Color color, Graphics g) {
      
      g.setColor(color); 
      
      // iterates through all the points 
      for (int p = 0; p < translatedPoints.size(); p++) {
         
         // stores the coordinates on the screen for each point
         // -pointRadius is necessary to make oval centered around (x, y) 
         int screenPointX = translatedPoints.get(p).x - pointRadius;
         int screenPointY = translatedPoints.get(p).y - pointRadius;
         
         Point screenPoint = new Point(screenPointX, screenPointY);
         
         // draws the point depending on whether its on the graph 
         if (checkPointInGrid(screenPoint)) {
            g.fillOval(screenPoint.x, screenPoint.y, pointRadius*2, pointRadius*2); 
         }
      
      }
      
   }
   
   // generates a translated point, so that the point can be drawn on the screen 
   public Point generateTranslatedPoint(Point2D p) {
      
      double scalingFactorX = (double) (origin.y - farthestYCoord) / maxXValue;
      double scalingFactorY = (double) (origin.y - farthestYCoord) / maxYValue;
      
      // calculates the positions of the point given the origin 
      int screenPointX = (int) (p.getX() * scalingFactorX + origin.x);
      int screenPointY = (int) (origin.y - p.getY() * scalingFactorY); 
      
      return (new Point(screenPointX, screenPointY));
      
   }
   
   // given a points Array, iterates through each point such that each point is translated
   // and can be drawn on the screen 
   public ArrayList<Point> generateTranslatedPointsArray(ArrayList<Point2D> pointsArray) {
   
      ArrayList<Point> translatedPoints = new ArrayList<Point>();
      
      // for each point, generate a translated point and add at it to translatedPoints Array 
      for (int p = 0; p < pointsArray.size(); p++) {
         Point screenPoint = generateTranslatedPoint(pointsArray.get(p)); 
         translatedPoints.add(screenPoint); 
      }
      
      return translatedPoints; 
      
   }
   
   // connects each point on the graph
   public void connectPoints(Color color, Graphics g) {
   
      g.setColor(color); 
      
      // makes a line for every pair of adjacent points 
      for (int p = 0; p < translatedPoints.size() - 1; p++) {
         if (checkPointInGrid(translatedPoints.get(p))) {
            g.drawLine(translatedPoints.get(p).x, translatedPoints.get(p).y, 
                       translatedPoints.get(p+1).x, translatedPoints.get(p+1).y);
         } 
      }
      
   }
   
   // method to draw the graph 
   public void drawGraph(Graphics g) {
   
      g.fillRect(topLeftCorner.x, topLeftCorner.y, GRAPH_SIZE.width, GRAPH_SIZE.height); 
      
      if (showAxes) {
         
         g.setColor(Color.WHITE);
         
         //draws x-axis
         g.drawLine(origin.x, origin.y, farthestXCoord, origin.y); 
         
         //draws y-axis
         g.drawLine(origin.x, origin.y, origin.x, farthestYCoord); 
         
         drawIncrements(g); 
      }
      
      // draws and connects the points 
      drawPoints(1, color, g);
      connectPoints(color, g); 
      
   }
   
   // checks if point is on the grid
   public boolean checkPointInGrid(Point p) {
      return (p.x >= origin.x && p.x < farthestXCoord 
           && p.y < origin.y && p.y > farthestYCoord); 
   }
   
   // clears the graph of projectile and its path 
   public void clearGraph() {
      pointsArray.clear();
      translatedPoints.clear(); 
      repaint(); 
   }
   
   // draws the graph 
   @Override 
   public void paintComponent(Graphics g) {
      drawGraph(g); 
   }
   
   // allows other classes to call Graph's repaint
   public void callRepaint() {
      repaint(); 
   }
   
   public String toString() {
      return String.format("Top Left Corner: (%d, %d), Max X Value: %d, Max Y Value: %d,"
                           + "Increment Size (x-axis): %d, Increment Size (y-axis): %d", 
                           topLeftCorner.x, topLeftCorner.y, maxXValue, maxYValue, 
                           incrementSizeX, incrementSizeY); 
                           
   }
   
}