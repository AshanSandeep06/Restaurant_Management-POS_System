package model;

public class PackageDetail {
    private String packageId;
    private String foodId;
    private String foodType;
    private int qty;
    private double unitPrice;

    public PackageDetail() {

    }

    public PackageDetail(String foodId, String foodType, int qty, double unitPrice) {
        this.foodId = foodId;
        this.foodType = foodType;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public PackageDetail(String packageId, String foodId, String foodType, int qty, double unitPrice) {
        this.packageId = packageId;
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

    public boolean equals(Object obj){
        return this.foodId == ((PackageDetail)obj).getFoodId();
    }
}
