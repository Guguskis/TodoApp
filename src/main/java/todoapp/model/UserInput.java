package main.java.todoapp.model;

import java.util.List;
import java.util.Scanner;

public class UserInput {

    private Scanner scan = null;

    public UserInput() {
        scan = new Scanner(System.in);
    }

    public <T> T getSelectable(List<T> selectables) {
        while (true) {
            for (int i = 1; i <= selectables.size(); i++) {
                System.out.println(i + " - " + selectables.get(i - 1));
            }

            int selection = -1;
            do {
                var input = scan.next();
                try {
                    selection = Integer.parseInt(input);
                } catch (Exception e) {
                    System.out.println("Invalid input.");
                }
            } while (selection == -1);

            if (selection >= 1 && selection <= selectables.size()) {
                return selectables.get(selection - 1);
            }
        }
    }

    public <T> T getSelectable(List<T> objects, List<String> labels) {

        while (true) {
            var lastIndex = objects.size();
            for (int i = 1; i <= lastIndex; i++) {
                System.out.println(i + " - " + labels.get(i - 1));
            }
            System.out.println(lastIndex + 1 + " - Return");

            int selection = -1;
            do {
                var input = scan.next();
                try {
                    selection = Integer.parseInt(input);
                } catch (Exception e) {
                    System.out.println("Invalid input.");
                }
            } while (selection == -1);

            if (selection >= 1 && selection <= lastIndex) {
                return objects.get(selection - 1);
            } else if (selection == lastIndex + 1) {
                return null;
            }
        }
    }

    public String getString(String message) {
        System.out.println(message);
        var userInput = "";
        do {
            userInput = scan.nextLine();
        } while (userInput.length() == 0);

        return userInput;
    }
}
