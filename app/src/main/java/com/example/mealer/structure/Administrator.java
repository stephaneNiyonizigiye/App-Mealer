package com.example.mealer.structure;

public class Administrator extends User {
    public Administrator(String firstName, String lastName, String email, String address) {
        super(firstName, lastName, email, address, "Administrator");
    }
}