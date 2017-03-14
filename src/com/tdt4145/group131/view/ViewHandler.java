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
        System.out.println("Menu:\nExercises 1\nWorkout 2\nSession 3\n\nType number: ");
        int selected;
        while (true) {
            String str = this.scan.nextLine();
            if (str.matches("^[1-3]$")) {
                selected = Integer.parseInt(str);
                break;
            }
            System.out.println("Please provide a number between 1 and 3: ");
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
