package com.tdt4145.group131.view;

import com.tdt4145.group131.database.*;
import com.tdt4145.group131.database.models.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Niklas on 14.03.2017.
 */
public class ViewHandler {
    public Scanner scan;

    public ViewHandler () {
        this.scan = new Scanner(System.in);
        System.out.println("Welcome to your training diary!");
        this.getMainMenuSelect();
    }

    public int getMainMenuSelect () {
        return this.getIntFromQuestion(
                "Menu:\nExercises 1\nWorkout 2\nSession 3\n\nType number: ",
                "^[1-3]$",
                "Please provide a number between 1 and 3: "
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
        System.out.println(question);

        int selected;

        while (true) {
            String str = this.scan.nextLine();

            if (str.matches(format)) {
                selected = Integer.parseInt(str);
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
        System.out.println(question);

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

    public void listExercises () {
        System.out.println();
    }

    public void listExerciseGroups () {
        ExerciseGroupService egs = new ExerciseGroupService();
        try {
            List<ExerciseGroup> list = egs.getAllExerciseGroups();
            for (ExerciseGroup eg : list) System.out.println(eg.name);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
