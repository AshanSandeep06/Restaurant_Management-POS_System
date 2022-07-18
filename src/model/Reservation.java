package model;

public class Reservation {
    private String reservationId;
    private String customerId;
    private String reservationDate;
    private String reservationTime;
    private int participants;

    public Reservation() {

    }

    public Reservation(String reservationId, String customerId, String reservationDate, String reservationTime, int participants) {
        this.reservationId = reservationId;
        this.customerId = customerId;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.participants = participants;
    }

    public Reservation(String reservationId, String reservationDate, String reservationTime, int participants) {
        this.reservationId = reservationId;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.participants = participants;
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

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }
}
