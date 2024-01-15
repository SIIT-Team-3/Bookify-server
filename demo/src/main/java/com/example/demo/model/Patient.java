package com.example.demo.model;

public class Patient {

    private long id;
    private long socialNumber;
    private String firstName;
    private String lastName;
    private boolean addict;

    public Patient(long id, long socialNumber, String firstName, String lastName, boolean addict) {
        this.id = id;
        this.socialNumber = socialNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addict = addict;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSocialNumber() {
        return socialNumber;
    }

    public void setSocialNumber(long socialNumber) {
        this.socialNumber = socialNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isAddict() {
        return addict;
    }

    public void setAddict(boolean addict) {
        this.addict = addict;
    }
}
