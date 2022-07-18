package model;

public class DateAndTotal {
    private String orderDate;
    private double grandTotal;

    public DateAndTotal() {

    }

    public DateAndTotal(String orderDate, double grandTotal) {
        this.setOrderDate(orderDate);
        this.setGrandTotal(grandTotal);
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }
}
