package model;

import javafx.scene.control.Button;

public class CartTM {
    private String foodId;
    private String foodType;
    private String description;
    private double unitPrice;
    private int qty;
    private double totalCost;

    public CartTM() {

    }

    public CartTM(String foodId, String foodType, String description, double unitPrice, int qty, double totalCost) {
        this.foodId = foodId;
        this.foodType = foodType;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.totalCost = totalCost;
    }

    public CartTM(String foodId, String description, double unitPrice, int qty) {
        this.foodId = foodId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
