package com.slice.utility;

import org.testng.Reporter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellCommandExecutor {
    public static String executeCommands(String command) {
        Reporter.log(command, true);
        StringBuilder output = new StringBuilder();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                output.append(line).append("\n");
            }

            while ((line = error.readLine()) != null) {
                output.append("Error Ocurred : \n").append(line).append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
