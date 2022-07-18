package model;

import java.time.LocalTime;

public class ReservationTM {
    private String reservationId;
    private String customerId;
    private String customerName;
    private String customerMobileNumber;
    private String reservationDate;
    private String reservationTime;
    private int totalParticipants;

    public ReservationTM() {

    }

    public ReservationTM(String reservationId, String customerId, String customerName, String customerMobileNumber, String reservationDate, String reservationTime, int totalParticipants) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerMobileNumber = customerMobileNumber;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.totalParticipants = totalParticipants;
    }

    public ReservationTM(String reservationId, String customerName, String customerMobileNumber, String reservationTime, int totalParticipants) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.customerMobileNumber = customerMobileNumber;
        this.reservationTime = reservationTime;
        this.totalParticipants = totalParticipants;
    }

    public ReservationTM(String reservationId, String reservationDate, String reservationTime, int totalParticipants) {
        this.reservationId = reservationId;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.totalParticipants = totalParticipants;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobileNumber() {
        return customerMobileNumber;
    }

    public void setCustomerMobileNumber(String customerMobileNumber) {
        this.customerMobileNumber = customerMobileNumber;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public int getTotalParticipants() {
        return totalParticipants;
    }

    public void setTotalParticipants(int totalParticipants) {
        this.totalParticipants = totalParticipants;
    }
}
