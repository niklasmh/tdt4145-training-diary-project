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
public class ExerciseGroupHandler {
    Scanner scan;
    ViewHandler vh;

    public ExerciseGroupHandler (Scanner scan, ViewHandler vh) {
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
                    this.listExerciseGroups();
                    break;
                case 2:
                    String name = vh.getStringFromQuestion(
                            "The exercise group name: ",
                            "[A-Za-zøæåØÆÅ ]+",
                            "Wrong format. Type group name again: "
                    );

                    String addPartOf = vh.getStringFromQuestion(
                            "Want to add this exercise group as part of another?\n[Y]es or [n]o (default if empty): ",
                            "^[YyNn]?$",
                            "Wrong format. Type [y]es or [N]o again: "
                    );

                    if (addPartOf.matches("^[Yy]$")) {
                        System.out.println("\n\nExercise groups to choose from:");
                        ExerciseGroupHandler.listExerciseGroupsIndexed();
                        int id = vh.getIntFromQuestion(
                                "The exercise group to connect it to: ",
                                "[0-9]+",
                                "Wrong format. Type id again: "
                        );
                        this.addExerciseGroup(name, id);
                    } else {
                        this.addExerciseGroup(name, 0);
                    }
                    break;
                case 3:
                    System.out.println("Handle 3");
                    break;
            }
        }
    }

    public int getMenuSelect () {
        return vh.getIntFromQuestion(
                "This is the exercise group menu." +
                        "\nMenu / Exercise group menu:" +
                        "\n[0] Go back" +
                        "\n[1] List all exercise groups" +
                        "\n[2] Add exercise group" +
                        "\n\nType number: ",
                "^[0-2]$",
                "Please provide a number between 0 and 2: "
        );
    }

    public void addExerciseGroup (String name, int parentGroupId) {
        ExerciseGroup exerciseGroup = new ExerciseGroup();
        exerciseGroup.name = name;
        exerciseGroup.parent_group_id = parentGroupId;

        ExerciseGroupService es = new ExerciseGroupService();

        try {
            es.saveNewExerciseGroup(exerciseGroup);
            System.out.println("\nAdded exercise group to database!");
        } catch (SQLException ex) {
            System.out.println("Could not save exercise group. Try again later.");
        } catch (Exception ex) {
            System.out.println("Error in code. Please ask admin.");
        }
    }

    public static void listExerciseGroupsIndexed () {
        ExerciseGroupService egs = new ExerciseGroupService();

        try {
            List<ExerciseGroup> list = egs.getAllExerciseGroups();
            for (ExerciseGroup eg : list) System.out.println("[" + (eg.ID) + "] " + eg.name);
        } catch (SQLException ex) {
            System.out.println("Could not fetch exercise groups. Try again later.");
        } catch (Exception ex) {
            System.out.println("Error in code. Please ask admin.");
        }
    }

    public static void listExerciseGroups () {
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
