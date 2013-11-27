/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midtermexam_Program1;

import midtermexam_domain.Animal;
import midtermexam_domain.Dog;
import midtermexam_domain.Duck;

/**
 *
 * @author Meghashree M ramachandra
 */
public class MidTermExam_Program1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
       
        //Animal animal = new Animal(); // cannot instantiate since abstract!       
       
        Dog dog = new Dog("Retriever",4);
        Dog d = new Dog();
        Duck duck = new Duck("Eider",2);
        Duck b = new Duck();

        // Upcast to Animal abstract class...
        // Polymorphism uses toString overrides at subclass level even though
        // upcasted to Animal level = superclass.
        Animal [] animals = {dog,duck};
         System.out.println("Polymorphism and Upcasting - Upcasted sub class(dog, duck) to super class(Animal abstract class):");
        for (Animal anim : animals) {
            System.out.println(anim);
        }
        
        // Upcast to Object class...
        // Polymorphism uses toString overrides at subclass level even though
        // upcasted to Object level = super-superclass.
        Object [] objects = {dog,duck};
        System.out.println("Polymorphism and Upcasting- Upcasted sub class(dog, duck) to super-superclass(Object level):");
        for (Object obj : objects) {
            System.out.println(obj);
        }
        
        
        Animal duckUpcast = duck; // Can be done implicitly without casting syntax.
        Object dogUpcast = dog;
        
        if (duckUpcast instanceof Duck) {
            System.out.println("Downcasting from superclass(Animal abstract class)to sub class(dog, duck):");
            Duck duckDowncast = (Duck)duckUpcast;
            System.out.println("Downcasting duckUpcast to Duck success");
        }
        
        if (dogUpcast instanceof Dog) {
            System.out.println("Downcasting from super-superclass(Object level to sub class(dog, duck):");
            Dog dogDowncast = (Dog)dogUpcast;
            System.out.println("Downcasting dogUpcast to Dog success");
        }             
        
        // Polymorphically obtain correct animal sound from array of Animal...
        System.out.println("Interface methods result.");
        for (Animal anim : animals) {
            System.out.println(anim.animalTalk());
        }
        
        if (duck.isLouder(dog)) {
            System.out.println("A duck is louder than a dog!");
        }
        else System.out.println("A dog is louder than a duck!");      
    
    }
}
