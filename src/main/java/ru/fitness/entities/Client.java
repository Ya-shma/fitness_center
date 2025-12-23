package ru.fitness.entities;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {
    private int id;
    private String fullName;
    private String phoneNumber;

    public Client() {
    }

    public Client(int id, String fullName, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phoneNumber + '\'' +
                '}';
    }
}
