package ru.fitness.entities;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Workout {
    private int id;
    private String name;
    private LocalDateTime dateTime;
    private int durationMinutes;
    private int maxCapacity;
    private int coachId;

    public Workout() {
    }

    public Workout(int id, String name, LocalDateTime dateTime, int durationMinutes, int maxCapacity, int coachId) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.durationMinutes = durationMinutes;
        this.maxCapacity = maxCapacity;
        this.coachId = coachId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateTime=" + dateTime +
                ", durationMinutes=" + durationMinutes +
                ", maxCapacity=" + maxCapacity +
                ", trainerId=" + coachId +
                '}';
    }
}
