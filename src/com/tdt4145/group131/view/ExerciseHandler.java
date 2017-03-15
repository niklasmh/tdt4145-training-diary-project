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

        int id;
        String name;
        String description;

        while (true) {
            int menu = this.getMenuSelect();

            switch (menu) {
                case 0:
                    return;
                case 1:
                    ExerciseHandler.listExercises();
                    break;
                case 2:
                    name = vh.getStringFromQuestion(
                            "The exercise name: ",
                            "[A-Za-zøæåØÆÅ ]+",
                            "Wrong format. Type name again: "
                    );
                    description = vh.getStringFromQuestion(
                            "The exercise description: ",
                            "[A-Za-zøæåØÆÅ ]+",
                            "Wrong format. Type description again: "
                    );
                    System.out.println("\n\nExercise groups to choose from:");
                    ExerciseGroupHandler.listExerciseGroupsIndexed();
                    id = vh.getIntFromQuestion(
                            "The exercise group to connect it to: ",
                            "[0-9]+",
                            "Wrong format. Type id again: "
                    );
                    this.addExercise(name, description, id);
                    break;
                case 3:
                    System.out.println("\n\nHere are all exercises you can update:");
                    ExerciseHandler.listExercisesIndexed();

                    id = vh.getIntFromQuestion(
                            "Which do you want to update? ",
                            "[0-9]+",
                            "Wrong format. Type id again: "
                    );

                    try {
                        ExerciseService es = new ExerciseService();
                        Exercise oldExercise = es.getExerciseById(id);

                        name = vh.getStringFromQuestion(
                                "New name: (Previous was: " + oldExercise.name + ", leave blank to remain name unchanged)",
                                "[A-Za-zøæåØÆÅ ]*",
                                "Wrong format. Type name again: "
                        );

                        description = vh.getStringFromQuestion(
                                "New description: (Previous was: \n -> " + oldExercise.description + "\n, leave blank to remain name unchanged)",
                                "[A-Za-zøæåØÆÅ ]*",
                                "Wrong format. Type description again: "
                        );

                        this.updateExercise(oldExercise, name, description);
                    } catch (SQLException ex) {
                        System.out.println("Could not get previous exercise. Try again later or change another one.");
                    }

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
                        "\n[3] Update exercise" +
                        "\n\nType number: ",
                "^[0-3]$",
                "Please provide a number between 0 and 3: "
        );
    }

    public static void listExercises () {
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

    public static void listExercisesIndexed () {
        ExerciseService es = new ExerciseService();

        try {
            List<Exercise> list = es.getAllExercises();
            for (Exercise e : list) System.out.println("[" + (e.id) + "] " + e.name);
        } catch (SQLException ex) {
            System.out.println("Could not fetch exercises. Try again later.");
        } catch (Exception ex) {
            System.out.println("Error in code. Please ask admin.");
        }
    }

    public void addExercise (String name, String description, int groupId) {
        Exercise exercise = new Exercise();
        exercise.name = name;
        exercise.description = description;

        ExerciseService es = new ExerciseService();

        try {
            es.saveNewExercise(exercise, groupId);
            System.out.println("\nAdded exercise to database!");
        } catch (SQLException ex) {
            System.out.println("Could not save exercise. Try again later.");
        } catch (Exception ex) {
            System.out.println("Error in code. Please ask admin.");
        }
    }

    public void updateExercise (Exercise oldExercise, String name, String description) {
        ExerciseService es = new ExerciseService();

        Exercise exercise = new Exercise();
        exercise.id = oldExercise.id;
        exercise.name = name.length() > 0 ? name : oldExercise.name;
        exercise.description = description.length() > 0 ? description : oldExercise.description;

        try {
            es.updateExercise(exercise);
            System.out.println("\nUpdated exercise in database!");
        } catch (SQLException ex) {
            System.out.println("Could not save exercise. Try again later.");
        } catch (Exception ex) {
            System.out.println("Error in code. Please ask admin.");
        }
    }
}
