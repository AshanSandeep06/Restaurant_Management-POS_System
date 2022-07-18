package model;

import java.util.ArrayList;

public class pkgDetails {
    private String packageId;
    private String packageName;
    private double packagePrice;
    private String foodId;
    private String foodType;
    private int qty;
    private double unitPrice;

    public pkgDetails() {

    }

    public pkgDetails(String packageId, String packageName, double packagePrice, String foodId, String foodType, int qty, double unitPrice) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.packagePrice = packagePrice;
        this.foodId = foodId;
        this.foodType = foodType;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public double getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(double packagePrice) {
        this.packagePrice = packagePrice;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
