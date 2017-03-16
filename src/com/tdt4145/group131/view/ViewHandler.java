package com.tdt4145.group131.view;

import java.util.Scanner;

/**
 * Created by Niklas on 14.03.2017.
 */
public class ViewHandler {
    public Scanner scan;

    public ViewHandler () {
        this.scan = new Scanner(System.in);
        System.out.println("Welcome to your training diary!");

        while (true) {
            int menu = this.getMainMenuSelect();

            switch (menu) {
                case 0:
                    System.out.println("Bye.");
                    System.exit(0);
                case 1:
                    ExerciseHandler eh = new ExerciseHandler(scan, this);
                    eh.runHandler();
                    break;
                case 2:
                    ExerciseGroupHandler egh = new ExerciseGroupHandler(scan, this);
                    egh.runHandler();
                    break;
                case 3:
                    WorkoutHandler wh = new WorkoutHandler(scan, this);
                    wh.runHandler();
                    System.out.println("Workout");
                    break;
                case 4:
                    System.out.println("Session");
                    break;
            }
        }
    }

    public int getMainMenuSelect () {
        return this.getIntFromQuestion(
                "This is the main menu." +
                        "\nMenu:" +
                        "\n[0] Quit" +
                        "\n[1] Exercises" +
                        "\n[2] Exercise groups" +
                        "\n[3] Workout" +
                        "\n[4] Session" +
                        "\n\nType number: ",
                "^[0-4]$",
                "Please provide a number between 0 and 4: "
        );
    }

    /**
     * Get an int from a question.
     * @param question The question to ask.
     * @param format The format or range in regex.
     * @param args Optional error and other stuff if nessesary.
     * @return int
     */
    public int getIntFromQuestion (String question, String format, String ...args) {
        System.out.println("\n\n" + question);

        int selected;

        while (true) {
            String str = this.scan.nextLine();

            if (str.matches(format)) {
                selected = str.length() > 0 ? Integer.parseInt(str) : 0;
                break;
            }

            if (args.length > 0) {
                System.out.println(args[0]);
            } else {
                System.out.println(question);
            }
        }

        return selected;
    }

    /**
     * Get a string from a question.
     * @param question The question to ask.
     * @param format The format or range in regex.
     * @param args Optional error and other stuff if nessesary.
     * @return int
     */
    public String getStringFromQuestion (String question, String format, String ...args) {
        System.out.println("\n\n" + question);

        String selected;

        while (true) {
            String str = this.scan.nextLine();

            if (str.matches(format)) {
                selected = str;
                break;
            }

            if (args.length > 0) {
                System.out.println(args[0]);
            } else {
                System.out.println(question);
            }
        }

        return selected;
    }
}
