/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midtermexam_domain;

/**
 *
 * @author kimont
 */
public class Duck extends Animal {
    
    public Duck() {
        this.loudness = 4.0;
    }

    public Duck(String breed, int numberOfLegs) {
        super(breed, numberOfLegs);
        this.loudness = 4.0;
    }

   

    @Override
    public String toString() {
        return "Duck{" + super.toString() +'}';
    }
    
    @Override
    public String animalTalk() {
        return "Quack Quack!";
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
