package model;

public class Discount {
    private String discountId;
    private double discountPrice;
    private String foodId;
    private String startDate;
    private String closeDate;
    private double calculatedDisPrice;

    public double getCalculatedDisPrice() {
        return calculatedDisPrice;
    }

    public void setCalculatedDisPrice(double calculatedDisPrice) {
        this.calculatedDisPrice = calculatedDisPrice;
    }

    public Discount(String discountId, double discountPrice, String foodId, String startDate, String closeDate, double calculatedDisPrice) {
        this.discountId = discountId;
        this.discountPrice = discountPrice;
        this.foodId = foodId;
        this.startDate = startDate;
        this.closeDate = closeDate;
        this.calculatedDisPrice = calculatedDisPrice;
    }

    public Discount() {

    }

    public Discount(String discountId, double discountPrice, String foodId, String startDate, String closeDate) {
        this.discountId = discountId;
        this.discountPrice = discountPrice;
        this.foodId = foodId;
        this.startDate = startDate;
        this.closeDate = closeDate;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }
}
