/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midtermexam_domain;

/**
 *
 * @author Meghashree M Ramachandra
 */
public abstract class Animal implements Relatable, Audible  {
    private String breed;
    protected int numberOfLegs;
    protected double loudness = 0.0;

    public Animal() {
        
    }

    public Animal(String breed, int numberOfLegs) {
        this.breed = breed;
        this.numberOfLegs = numberOfLegs;
       
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getNumberOfLegs() {
        return numberOfLegs;
    }

    public void setNumberOfLegs(int numberOfLegs) {
        this.numberOfLegs = numberOfLegs;
    }

    public double getLoudness() {
        return loudness;
    }

    public void setLoudness(double loudness) {
        this.loudness = loudness;
    }

    @Override
    public String toString() {
        return "Animal{" + "breed=" + breed + ", numberOfLegs=" + numberOfLegs + ", loudness=" + loudness + '}';
    } 
    
}
