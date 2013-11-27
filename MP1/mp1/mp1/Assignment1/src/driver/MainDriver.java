/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package driver;

import domain.MilkyWayPlanet;
import domain.Planet;

/**
 *
 * @author Meghashree M Ramachandra
 */
public class MainDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Instance of each MilkyWayPlanet class
        MilkyWayPlanet Mercury = new MilkyWayPlanet("Mercury", 0.3302, 4879, 4.25, 1407.6, 623);
        MilkyWayPlanet Venus = new MilkyWayPlanet("Venus", 4.87, 12104, 10.4, 5832.24, 750);
        MilkyWayPlanet Earth = new MilkyWayPlanet("Earth", 5.976, 12756, 11.2, 23.93, 293);
        MilkyWayPlanet Mars = new MilkyWayPlanet("Mars", 64.2, 6794, 5, 24, 293);// revolution period inhours
        MilkyWayPlanet Jupiter = new MilkyWayPlanet("Jupiter", 1898, 139822, 60, 10, 163);
        MilkyWayPlanet Saturn = new MilkyWayPlanet("Saturn", 568.3, 120000, 35.6, 10, 93);
        MilkyWayPlanet Uranus = new MilkyWayPlanet("Uranus", 86.81, 51120, 21.2, 17.2, 57);
        MilkyWayPlanet Neptune = new MilkyWayPlanet("Neptune", 102.4, 49528, 23.6, 16.11, 57);
        MilkyWayPlanet Pluto = new MilkyWayPlanet("Pluto", 1.30, 2290, 1, 153.29, 50);

        //Print the instance of each MilkyWayPlanet created
        /*System.out.println("Planets Data : \n"
         + Mercury + "\n"
         + Venus + "\n"
         + Earth + "\n"
         + Mars + "\n"
         + Jupiter + "\n"
         + Saturn + "\n"
         + Uranus + "\n"
         + Neptune + "\n"
         + Pluto + "\n");*/

        // Planet objects into an array of Planets called solarSystem
        // ie.Upcast to Planet abstract class.
        //(Polymorphism : overrides toString at subclass level(MilkyWayPlanet) 
        //even though upcasted to superclass(Planets))
        Planet[] solarSystem = {Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune, Pluto};
        System.out.println("Polymorphism(Upcasted to Planet abstract superclass) :");
        for (Planet galaxy : solarSystem) {
            System.out.println(galaxy);
        }

        // Upcast to Object class...
        //(Polymorphism : overrides toString at subclass level(MilkyWayPlanet) 
        //even though upcasted to Super-superclass(Objects))
        Object[] objects = {Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune, Pluto};
        System.out.println("\nPolymorphism(Upcasted to Object super-superclass) :");
        for (Object obj : objects) {
            System.out.println(obj);
        }

        //Comapring Mass of Mercury and Jupiter
        if (Mercury.isMassSmaller(Jupiter)) {
            System.out.println("\nMercury Mass : " + Mercury.getMass() + "x" + Math.pow(10, 24) + "Kg" + "\n"
                    + "Jupiter Mass : " + Jupiter.getMass() + "x" + Math.pow(10, 24) + "Kg" + "\n"
                    + Mercury.getPlanetName() + "'s mass smaller than " + Jupiter.getPlanetName() + "'s mass");
        } else {
            System.out.println("\nMercury Mass : " + Mercury.getMass() + "x" + Math.pow(10, 24) + "Kg" + "\n"
                    + "Jupiter Mass : " + Jupiter.getMass() + "x" + Math.pow(10, 24) + "Kg" + "\n"
                    + Jupiter.getPlanetName() + "'s mass smaller than " + Mercury.getPlanetName() + "'s mass");
        }

        //Comaparing Diameter of Mercury and Jupiter
        if (Mercury.isDiameterGreater(Jupiter)) {
            System.out.println("\nMercury Diameter : " + Mercury.getDiameter() + "km" + "\n"
                    + "Jupiter Diameter : " + Jupiter.getDiameter() + "km" + "\n"
                    + Mercury.getPlanetName() + "'s diameter greater than " + Jupiter.getPlanetName() + "'s diameter");
        } else {
            System.out.println("\nMercury Diameter : " + Mercury.getDiameter() + "km" + "\n"
                    + "Jupiter Diameter : " + Jupiter.getDiameter() + "km" + "\n"
                    + Jupiter.getPlanetName() + "'s diameter greater than " + Mercury.getPlanetName() + "'s diameter");
        }

    }
}
