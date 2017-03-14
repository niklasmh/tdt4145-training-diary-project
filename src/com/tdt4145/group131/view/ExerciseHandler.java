package com.tdt4145.group131.view;

import com.tdt4145.group131.database.ExerciseGroupService;
import com.tdt4145.group131.database.ExerciseService;
import com.tdt4145.group131.database.models.Exercise;
import com.tdt4145.group131.database.models.ExerciseGroup;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Niklas on 14.03.2017.
 */
public class ExerciseHandler {
    Scanner scan;
    ViewHandler vh;

    public ExerciseHandler (Scanner scan, ViewHandler vh) {
        this.scan = scan;
        this.vh = vh;
    }

    public void runHandler () {

        while (true) {
            int menu = this.getMenuSelect();

            switch (menu) {
                case 0:
                    return;
                case 1:
                    this.listExercises();
                    break;
                case 2:
                    System.out.println("Handle 2");
                    break;
                case 3:
                    System.out.println("Handle 3");
                    break;
            }
        }
    }

    public int getMenuSelect () {
        return vh.getIntFromQuestion(
                "This is the exercise menu." +
                        "\nMenu / Exercise menu:" +
                        "\n[0] Go back" +
                        "\n[1] List all exercises" +
                        "\n[2] Add exercise" +
                        "\n\nType number: ",
                "^[0-2]$",
                "Please provide a number between 0 and 2: "
        );
    }

    public void listExercises () {
        ExerciseService es = new ExerciseService();

        try {
            List<Exercise> list = es.getAllExercises();
            for (Exercise e : list) System.out.println(e.name);
        } catch (SQLException ex) {
            System.out.println("Could not fetch exercises. Try again later.");
        } catch (Exception ex) {
            System.out.println("Error in code. Please ask admin.");
        }
    }

    public void listExerciseGroups () {
        ExerciseGroupService egs = new ExerciseGroupService();

        try {
            List<ExerciseGroup> list = egs.getAllExerciseGroups();
            for (ExerciseGroup eg : list) System.out.println(eg.name);
        } catch (SQLException ex) {
            System.out.println("Could not fetch exercise groups. Try again later.");
        } catch (Exception ex) {
            System.out.println("Error in code. Please ask admin.");
        }
    }
}
