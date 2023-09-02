package com.slice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetails {
    private String sessionToken;
    private String deviceId;
    private String customerId;
    private String phoneNumber;
    private String panNumber;
    private String name;
    private String dob;
    private String salary;
    private String accountNumber;
    private String ifscCode;
    private String loanAmount;
    private String tenure;
    private String emi;
    private static UserDetails userDetails = null;

    public UserDetails() {
    }

    public static UserDetails getInstance() {
        if (userDetails == null) {
            userDetails = new UserDetails();
        }

        return userDetails;
    }
}
