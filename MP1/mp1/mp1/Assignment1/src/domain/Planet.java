/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Meghashree M Ramachandra
 */
public abstract class Planet implements Relatable {

    protected String planetName; //Name of the galaxy planets.
    protected double mass; //Mass of planets in 10 power 24 kg unit.
    protected int diameter; // Diameter of planets in Km(Kilometer) Unit.
    protected double escapeVelocity; //Escape velocity in Km/s(Kilometer/sec) Unit.
    protected double revolutionPeriod; //Revolution period of planets in Hours Unit.
    protected int meanSurfaceTemperature;//Surface Temperature of planets in K(Kelvin Scale) Unit.

    //No argument Constructor
    public Planet() {
        planetName = "Null";
        mass = 0.0;
        diameter = 0;
        escapeVelocity = 0.0;
        revolutionPeriod = 0.0;
        meanSurfaceTemperature = 0;
    }

    //full argument constructor
    public Planet(String planetName, double mass, int diameter, double escapeVelocity, double revolutionPeriod, int meanSurfaceTemperature) {
        this.planetName = planetName;
        this.mass = mass;
        this.diameter = diameter;
        this.escapeVelocity = escapeVelocity;
        this.revolutionPeriod = revolutionPeriod;
        this.meanSurfaceTemperature = meanSurfaceTemperature;
    }

    //Accessors and Mutators of instance fields
    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public double getEscapeVelocity() {
        return escapeVelocity;
    }

    public void setEscapeVelocity(int escapeVelocity) {
        this.escapeVelocity = escapeVelocity;
    }

    public double getRevolutionPeriod() {
        return revolutionPeriod;
    }

    public void setRevolutionPeriod(double revolutionPeriod) {
        this.revolutionPeriod = revolutionPeriod;
    }

    public int getMeanSurfaceTemperature() {
        return meanSurfaceTemperature;
    }

    public void setMeanSurfaceTemperature(int meanSurfaceTemperature) {
        this.meanSurfaceTemperature = meanSurfaceTemperature;
    }

    @Override
    public String toString() {
        return "Planet{" + "Name = " + planetName
                + ", mass = " + mass + "x" + Math.pow(10, 24) + "Kg"
                + ", diameter = " + diameter + "km"
                + ", escapeVelocity = " + escapeVelocity + "Km/s"
                + ", revolutionPeriod = " + revolutionPeriod + "Hours"
                + ", meanSurfaceTemperature = " + meanSurfaceTemperature + "K" + '}';
    }

    //Interface methods used
    //to compare mass of two planets
    @Override
    public boolean isMassSmaller(Object other) {
        boolean result = false;
        Planet p = null;
        if (other instanceof Planet) {
            p = (Planet) other;
        }
        if (p != null) {
            if (this.getMass() < p.getMass()) {
                result = true;
            }
        }
        return result;
    }
    //Interface methods used
    //to compare diameter of two planets
    @Override
    public boolean isDiameterGreater(Object other) {
        boolean result = false;
        Planet p = null;
        if (other instanceof Planet) {
            p = (Planet) other;
        }
        if (p != null) {
            if (this.getDiameter() > p.getDiameter()) {
                result = true;
            }
        }
        return result;
    }
}
