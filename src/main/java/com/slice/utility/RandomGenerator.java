package com.slice.utility;

import com.github.javafaker.Faker;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Random;

public class RandomGenerator {
    public static String generateRandomMobileNumber() {
        return "66666" + RandomStringUtils.randomNumeric(5);
    }

    public static String random() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    public static String generateEmailId() {
        Faker faker = new Faker();
        String emailId = faker.name().firstName() + "@gmail.com";
        return emailId;
    }

    public static String getSaltString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public static String getSaltInteger(int length) {
        String SALTCHARS = "1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
