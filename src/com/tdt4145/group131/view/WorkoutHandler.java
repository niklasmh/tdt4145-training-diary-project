package com.tdt4145.group131.view;

import com.tdt4145.group131.database.ExerciseService;
import com.tdt4145.group131.database.WorkoutService;
import com.tdt4145.group131.database.models.Exercise;
import com.tdt4145.group131.database.models.Workout;
import sun.awt.image.ImageWatched;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by sindr on 3/16/2017.
 */
public class WorkoutHandler {

    Scanner scan;
    ViewHandler vh;

    public WorkoutHandler (Scanner scan, ViewHandler vh) {
        this.scan = scan;
        this.vh = vh;
    }

    public void runHandler () {

        int id;
        String name = "";
        String description = "";
        LinkedList<Exercise> exercises = new LinkedList<>();

        while (true) {
            int menu = this.getMenuSelect();

            switch (menu) {
                case 0:
                    return;
                case 1:
                    listWorkouts();
                    break;
                case 2:
                    System.out.println("Add a workout:");
                    name = vh.getStringFromQuestion(
                            "The workout name: ",
                            "[A-Za-zøæåØÆÅ ]+",
                            "Wrong format. Type name again: "
                    );
                    description = vh.getStringFromQuestion(
                            "The workout description: ",
                            "[A-Za-zøæåØÆÅ. ]+",
                            "Wrong format. Type description again: "
                    );
                    System.out.println("\n\nAdd an exercise to the workout, and enter 0 when complete.");
                    ExerciseHandler.listExercisesIndexed();
                    id = -1;
                    while (id != 0){
                        id = vh.getIntFromQuestion(
                                "The exercise group to add to the workout: ",
                                "[0-9]+",
                                "Wrong format. Type id again: ");
                        Exercise ex = getExerciseById(id);
                        if (ex != null) {
                            exercises.add(ex);
                        }

                    }
                    addWorkout(name, description, exercises);
                    break;
                case 3:
                    //System.out.println("\n\nHere are all the workouts you can update:");
                    //WorkoutHandler.listWorkoutsIndexed();
                    System.out.println("Unfortunately we don't support this feature at the moment");
                    break;
                case 4:
                    listWorkoutsIndexed();
                    int workout_id = vh.getIntFromQuestion(
                            "Which workout do you wish to see?",
                            "[0-9]+",
                            "Wrong format. Type id again:");
                    printDetailedWorkout(workout_id);
                    break;
            }
        }
    }

    public int getMenuSelect () {
        return vh.getIntFromQuestion(
                "This is the Workout menu." +
                        "\nMenu / Workout menu:" +
                        "\n[0] Go back" +
                        "\n[1] List all workouts" +
                        "\n[2] Add workout" +
                        "\n[3] Update workout (Coming soon...)" +
                        "\n[4] See details of a workout" +
                        "\n\nType number: ",
                "^[0-4]$",
                "Please provide a number between 0 and 4: "
        );
    }

    public static void listWorkouts () {
        WorkoutService ws = new WorkoutService();

        try {
            List<Workout> list = ws.getAllWorkouts();
            for (Workout w : list) System.out.println(w.Name + " - " + w.Description);
        } catch (SQLException ex) {
            System.out.println("Could not fetch workout. Try again later.");
        } catch (Exception ex) {
            System.out.println("Error in code. Please ask admin.");
        }
    }

    public static void listWorkoutsIndexed () {
        WorkoutService ws = new WorkoutService();

        try {
            List<Workout> list = ws.getAllWorkouts();
            for (Workout w : list) System.out.println("[" + (w.Id) + "] " + w.Name);
        } catch (SQLException ex) {
            System.out.println("Could not fetch workouts. Try again later.");
        } catch (Exception ex) {
            System.out.println("Error in code. Please ask admin.");
        }
    }

    public static void printDetailedWorkout(int id){

        try {
            Workout w = WorkoutService.getWorkoutById(id);
            System.out.println("Name: " + w.Name);
            System.out.println("Description: " + w.Description);

            System.out.println("Exercises in workout:");
            for (Exercise e : w.Exercises) {
                System.out.println("" + e.name);
            }
        } catch (SQLException ex) {
            System.out.println("Could not fetch exercise. Try again later.");
        } catch (Exception ex) {
            System.out.println("Error in code. Please ask admin.");
        }

    }

    public static Exercise getExerciseById(int id) {
        ExerciseService es = new ExerciseService();

        try {
            return es.getExerciseById(id);
        } catch (SQLException ex) {
            System.out.println("Could not fetch exercise. Try again later.");
        } catch (Exception ex) {
            System.out.println("Error in code. Please ask admin.");
        }
        return null;
    }

    public static void addWorkout(String name, String description, List<Exercise> exercises){
        WorkoutService ws = new WorkoutService();

        Workout w = new Workout();
        w.Name = name;
        w.Description = description;
        w.Exercises = exercises;


        try {
            ws.saveNewWorkout(w);
        } catch (SQLException ex) {
            System.out.println("Could not save workout. Try again later.");
        } catch (Exception ex) {
            System.out.println("Error in code. Please ask admin.");
        }
    }
}
