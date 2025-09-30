package ru.fitness.entities;

public class Coach {
    private int id;
    private String fullName;
    private Specialization specialization;

    public Coach() {
    }

    public Coach(int id, String fullName, Specialization specialization) {
        this.id = id;
        this.fullName = fullName;
        this.specialization = specialization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", specialization=" + specialization +
                '}';
    }
}
