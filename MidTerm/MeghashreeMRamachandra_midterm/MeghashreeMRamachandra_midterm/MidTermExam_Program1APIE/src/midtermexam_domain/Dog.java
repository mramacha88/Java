/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midtermexam_domain;

/**
 *
 * @author Meghashree M Ramachandra
 */
public class Dog extends Animal {
    
    public Dog() {
        this.loudness = 5.0;
    }

    public Dog(String breed, int numberOfLegs) {
        super(breed, numberOfLegs);
        this.loudness = 5.0;
    }

  

    @Override
    public String toString() {
        return "Dog{" + super.toString()+ '}';
    } 

    @Override
    public String animalTalk() {
        return "Woof Woof!";
    }

    @Override
    public boolean isLouder(Object obj) {
        boolean result = false;


        if (obj instanceof Dog) {
            Dog downcastObj = (Dog) obj;
            if (this.loudness > downcastObj.loudness) {
                result = true;
            }
        } else if (obj instanceof Duck) {
            Duck downcastObj = (Duck) obj;
            if (this.loudness > downcastObj.loudness) {
                result = true;
            }
        }
        return result;
    }
}
