package model;

import java.util.ArrayList;

public class Order {
    private String orderId;
    private String customerId;
    private String customerName;
    private String orderDate;
    private String orderTime;
    private String orderType;
    private double subTotal;
    private double deliveryCharges;
    private double grandTotal;
    private String orderState;
    private ArrayList<OrderDetail> orderDetails;

    public Order() {

    }

    public Order(String orderId,String customerId,String customerName,double subTotal,double deliveryCharges,double grandTotal,String orderState,ArrayList<OrderDetail> orderDetails){
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.subTotal = subTotal;
        this.deliveryCharges = deliveryCharges;
        this.grandTotal = grandTotal;
        this.orderState = orderState;
        this.orderDetails = orderDetails;
    }

    public Order(String orderId, String customerId, String customerName, String orderDate, String orderTime, String orderType, double subTotal, double deliveryCharges, double grandTotal, String orderState, ArrayList<OrderDetail> orderDetails) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderType = orderType;
        this.subTotal = subTotal;
        this.deliveryCharges = deliveryCharges;
        this.grandTotal = grandTotal;
        this.orderState = orderState;
        this.orderDetails = orderDetails;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
}
