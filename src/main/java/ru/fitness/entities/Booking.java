package ru.fitness.entities;

import java.time.LocalDateTime;

public class Booking {
    private int id;
    private int clientId;
    private int workoutId;
    private LocalDateTime bookingDate;

    public Booking() {
    }

    public Booking(int id, int clientId, int workoutId, LocalDateTime bookingDate) {
        this.id = id;
        this.clientId = clientId;
        this.workoutId = workoutId;
        this.bookingDate = bookingDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", workoutId=" + workoutId +
                ", bookingDate=" + bookingDate +
                '}';
    }
}
