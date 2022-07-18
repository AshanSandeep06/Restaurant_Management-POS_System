package model;

public class DiscountTM {
    private String discountId;
    private double discountPrice;
    private String foodName;
    private String startDate;
    private String closeDate;

    public DiscountTM() {

    }

    public DiscountTM(String discountId, double discountPrice, String foodName, String startDate, String closeDate) {
        this.discountId = discountId;
        this.discountPrice = discountPrice;
        this.foodName = foodName;
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

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
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
