package model;

public class OrderDetail {
    private String orderId;
    private String foodId;
    private String foodType;
    private String foodDescription;
    private double unitPrice;
    private int qty;

    public OrderDetail() {

    }

    public OrderDetail(String orderId, String foodId, String foodType, String foodDescription, double unitPrice, int qty) {
        this.orderId = orderId;
        this.foodId = foodId;
        this.foodType = foodType;
        this.foodDescription = foodDescription;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
}
