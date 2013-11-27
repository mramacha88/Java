/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;
/**
 *
 * @author Meghashree M Ramachandra
 */
//This class encapsulates the fields from the PRODUCT_data.txt file
public class Product {
    //Fields for the contents of Product_data.txt file
    private int PRODUCT_ID;
    private int MANUFACTURER_ID;
    private String PRODUCT_CODE;
    private float PURCHASE_COST;
    private int QUANTITY_ON_HAND;
    private float MARKUP;
    private String AVAILABLE;
    private String DESCRIPTION;

    //No-arg Constructor
    public Product() {
    }
    
    //Full-arg constructor.
    public Product(int PRODUCT_ID, int MANUFACTURER_ID, String PRODUCT_CODE, float PURCHASE_COST, int QUANTITY_ON_HAND, float MARKUP, String AVAILABLE, String DESCRIPTION) {
        this.PRODUCT_ID = PRODUCT_ID;
        this.MANUFACTURER_ID = MANUFACTURER_ID;
        this.PRODUCT_CODE = PRODUCT_CODE;
        this.PURCHASE_COST = PURCHASE_COST;
        this.QUANTITY_ON_HAND = QUANTITY_ON_HAND;
        this.MARKUP = MARKUP;
        this.AVAILABLE = AVAILABLE;
        this.DESCRIPTION = DESCRIPTION;
    }

    //Accessors and Mutators of Fields
    //retrieve product id.
    public int getPRODUCT_ID() {
        return PRODUCT_ID;
    }
    //store product id.
    public void setPRODUCT_ID(int PRODUCT_ID) {
        this.PRODUCT_ID = PRODUCT_ID;
    }
    //retrieve manufacture id.
    public int getMANUFACTURER_ID() {
        return MANUFACTURER_ID;
    }
    //store manufacture id.
    public void setMANUFACTURER_ID(int MANUFACTURER_ID) {
        this.MANUFACTURER_ID = MANUFACTURER_ID;
    }
    //retrieve product code.
    public String getPRODUCT_CODE() {
        return PRODUCT_CODE;
    }
    //store product code.
    public void setPRODUCT_CODE(String PRODUCT_CODE) {
        this.PRODUCT_CODE = PRODUCT_CODE;
    }
    //retrieve purchase cost.
    public float getPURCHASE_COST() {
        return PURCHASE_COST;
    }
    //store purchase cost.
    public void setPURCHASE_COST(float PURCHASE_COST) {
        this.PURCHASE_COST = PURCHASE_COST;
    }
    //retrieve quantity on hand.
    public int getQUANTITY_ON_HAND() {
        return QUANTITY_ON_HAND;
    }
    //store quantity on hand.
    public void setQUANTITY_ON_HAND(int QUANTITY_ON_HAND) {
        this.QUANTITY_ON_HAND = QUANTITY_ON_HAND;
    }
    //retrieve mark up
    public float getMARKUP() {
        return MARKUP;
    }
    //store mark up.
    public void setMARKUP(float MARKUP) {
        this.MARKUP = MARKUP;
    }
    //retrieve the product availability. 
    public String getAVAILABLE() {
        return AVAILABLE;
    }
    //store the product availability
    public void setAVAILABLE(String AVAILABLE) {
        this.AVAILABLE = AVAILABLE;
    }
    //retrieve the product description.
    public String getDESCRIPTION() {
        return DESCRIPTION;
    }
    //store the product description.
    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    //overridden toString()
    @Override
    public String toString() {
        return "Product{" + "PRODUCT_ID=" + PRODUCT_ID + ", MANUFACTURER_ID=" + MANUFACTURER_ID + ", PRODUCT_CODE=" + PRODUCT_CODE + ", PURCHASE_COST=" + PURCHASE_COST + ", QUANTITY_ON_HAND=" + QUANTITY_ON_HAND + ", MARKUP=" + MARKUP + ", AVAILABLE=" + AVAILABLE + ", DESCRIPTION=" + DESCRIPTION + '}';
    }
}
