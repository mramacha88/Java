/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Meghashree M Ramachandra
 */
public interface Relatable {

    boolean isMassSmaller(Object other);

    boolean isDiameterGreater(Object other);
}
