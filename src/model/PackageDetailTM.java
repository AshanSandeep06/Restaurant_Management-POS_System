package model;

import javafx.scene.control.Button;

public class PackageDetailTM {
    private String foodId;
    private String foodDescription;
    private String foodType;
    private double uniPrice;
    private int qty;
    private double totalCost;
    private Button btn;

    public PackageDetailTM() {

    }

    public PackageDetailTM(String foodId, String foodDescription, double uniPrice, int qty, double totalCost) {
        this.foodId = foodId;
        this.foodDescription = foodDescription;
        this.uniPrice = uniPrice;
        this.qty = qty;
        this.totalCost = totalCost;
    }

    public PackageDetailTM(String foodId, String foodDescription, String foodType, double uniPrice, int qty, double totalCost) {
        this.foodId = foodId;
        this.foodDescription = foodDescription;
        this.foodType = foodType;
        this.uniPrice = uniPrice;
        this.qty = qty;
        this.totalCost = totalCost;
    }

    public PackageDetailTM(String foodId, String foodDescription, String foodType, double uniPrice, int qty, double totalCost, Button btn) {
        this.foodId = foodId;
        this.foodDescription = foodDescription;
        this.foodType=foodType;
        this.uniPrice = uniPrice;
        this.qty = qty;
        this.totalCost = totalCost;
        this.btn = btn;
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

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public double getUniPrice() {
        return uniPrice;
    }

    public void setUniPrice(double uniPrice) {
        this.uniPrice = uniPrice;
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

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public String toString(){
        return foodId;
    }
}
