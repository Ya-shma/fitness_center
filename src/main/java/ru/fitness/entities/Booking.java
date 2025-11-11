package ru.fitness.entities;

import java.time.LocalDateTime;

public class Booking {
    private int id;
    private int clientId;
    private int workoutId;
    private LocalDateTime bookingDate;
    private String status; // "active", "cancelled", "completed"
    private LocalDateTime createdAt;

    public Booking() {
    }

    public Booking(int id, int clientId, int workoutId, LocalDateTime bookingDate, String status) {
        this.id = id;
        this.clientId = clientId;
        this.workoutId = workoutId;
        this.bookingDate = bookingDate;
        this.status = status != null ? status : "active";
        this.createdAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return "active".equals(status);
    }

    public boolean isCancelled() {
        return "cancelled".equals(status);
    }

    public boolean isCompleted() {
        return "completed".equals(status);
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", workoutId=" + workoutId +
                ", bookingDate=" + bookingDate +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}