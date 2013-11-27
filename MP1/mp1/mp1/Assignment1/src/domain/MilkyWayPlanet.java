/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Meghashree M Ramachandra
 */
public class MilkyWayPlanet extends Planet {

    //No argument Constructor
    public MilkyWayPlanet() {
        super();
    }

    //full argument constructor
    public MilkyWayPlanet(String planetName, double mass, int diameter, double escapeVelocity, double revolutionPeriod, int meanSurfaceTemperature) {
        super(planetName, mass, diameter, escapeVelocity, revolutionPeriod, meanSurfaceTemperature);

    }

    @Override
    public String toString() {
        return "Milkywayplanets{" + super.toString() + '}';
    }
}
