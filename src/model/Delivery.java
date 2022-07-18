package model;

public class Delivery {
    private String orderId;
    private String riderId;
    private String riderName;

    public Delivery() {

    }

    public Delivery(String orderId, String riderId, String riderName) {
        this.orderId = orderId;
        this.riderId = riderId;
        this.riderName = riderName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }
}
