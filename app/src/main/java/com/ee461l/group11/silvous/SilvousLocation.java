package com.ee461l.group11.silvous;

/**
 * Created by Danny Vo on 11/28/2016.
 */

public class SilvousLocation {
    private double x_coord;
    private double y_coord;

    public SilvousLocation(){}
    public SilvousLocation(double x, double y)
    {
        x_coord = x;
        y_coord = y;
    }

    public double getX_coord()
    {
        return this.x_coord;
    }

    public double getY_coord()
    {
        return this.y_coord;
    }

    public void changeLocation(double x, double y)
    {
        this.x_coord = x;
        this.y_coord = y;
    }
    public double calculateDistance(SilvousLocation location)
    {
        double xDist = Math.pow((this.x_coord - location.getX_coord()), 2);
        double yDist = Math.pow((this.y_coord - location.getY_coord()), 2);
        return Math.sqrt(xDist + yDist);
    }
}
