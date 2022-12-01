/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    private static final double straightAngle = 180.0;
    private static final double fullTurnAngle = 360.0;
    
    public static void drawSquare(Turtle turtle, int sideLength) {
        final double squareInnerAngle = 90.0;
        final double turnAngle = TurtleSoup.straightAngle - squareInnerAngle;
        for (int i = 0; i<4; i++){
            
            turtle.forward(sideLength);
            turtle.turn(turnAngle); 
            }
            
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return (TurtleSoup.straightAngle*Double.valueOf(sides-2)/Double.valueOf(sides));  
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        
        final double sidesDecimal = TurtleSoup.fullTurnAngle/(TurtleSoup.straightAngle- angle); 
        return (int)Math.round(sidesDecimal); 
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        final double turnAngle = TurtleSoup.straightAngle - calculateRegularPolygonAngle(sides);
        
        
        for (int i = 0; i< sides; i++){
            turtle.forward(sideLength);
            turtle.turn(turnAngle);
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
        final int yChange = targetY - currentY;
        final int xChange = targetX - currentX;
        double targetAngle = Math.toDegrees(Math.atan2(xChange, yChange));
        
        /*Since we want to return a positive angle we increment the target angle by a full turn 
         * of 360 degrees until it is larger or equal to the current heading; adding 360 degrees 
         * to a direction results in pointing in the same direction so the angles are equivalent.
         */
        while (currentHeading > targetAngle){
            targetAngle += TurtleSoup.fullTurnAngle;
        }
        return targetAngle - currentHeading;
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
        List<Double> listOfHeadings = new ArrayList<Double>();
        double currentHeading = 0.0; 
        for (int i = 0; i < xCoords.size() - 1; i++){
            currentHeading = calculateHeadingToPoint(currentHeading, xCoords.get(i), yCoords.get(i), xCoords.get(i+1), yCoords.get(i+1)); //the function calculates a heading but we are setting the angle to it. Seems a bit confusing.
            listOfHeadings.add(currentHeading);
        }
        return listOfHeadings;
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * Personal art consists of calculating the first 200 hail stone paths. For each even
     * member moving forward by 6 pixels, and for each odd member
     * turning the turtle by approximately the golden angle.
     * 
     * See https://en.wikipedia.org/wiki/Golden_angle for an overview of the golden angle.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        final double goldenAngle = 137.5077640500378546463487;
        final double turnAngle = goldenAngle;
        final int stepsForward = 6;
        
        int hailStone;
        
        for (int i = 1; i<200; i++){ 
            hailStone = i; 
            
             
            while (hailStone != 1){
                
                if (hailStone % 2 == 0){
                    
                    hailStone = hailStone/2;
                    turtle.forward(stepsForward);
                    
                } else {
                    hailStone = 3*hailStone +1;
                    turtle.turn(turnAngle);
                    
                }
            }
            
        }
        
        
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        drawPersonalArt(turtle);

        // draw the window
        turtle.draw();
    }

}
