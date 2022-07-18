package model;

public class Food {
    private String foodId;
    private String foodType;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
    private double discountedPrice;

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Food() {

    }

    public Food(String foodId,String description,double unitPrice){
        this.foodId = foodId;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public Food(String foodId, String foodType, String description, double unitPrice, int qtyOnHand) {
        this.foodId = foodId;
        this.foodType = foodType;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    public Food(String foodId, String foodType, String description, double unitPrice, int qtyOnHand,double discountedPrice) {
        this.foodId = foodId;
        this.foodType = foodType;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.discountedPrice=discountedPrice;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return foodId+" - "+description;
    }
}
