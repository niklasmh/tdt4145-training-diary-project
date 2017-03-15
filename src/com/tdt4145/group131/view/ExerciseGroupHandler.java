package com.tdt4145.group131.view;

import com.tdt4145.group131.database.ExerciseGroupService;
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

        String name;

        while (true) {
            int menu = this.getMenuSelect();

            switch (menu) {
                case 0:
                    return;
                case 1:
                    ExerciseGroupHandler.listExerciseGroups();
                    break;
                case 2:
                    name = vh.getStringFromQuestion(
                            "The exercise group name: ",
                            "[A-Za-zøæåØÆÅ ]+",
                            "Wrong format. Type group name again: "
                    );

                    String addPartOf = vh.getStringFromQuestion(
                            "Want to add this exercise group as part of another?\n[y]es or [N]o (no if empty): ",
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
                    System.out.println("\n\nHere are all exercise groups you can update:");
                    ExerciseGroupHandler.listExerciseGroupsIndexed();

                    int id = vh.getIntFromQuestion(
                            "Which do you want to update? ",
                            "[0-9]+",
                            "Wrong format. Type id again: "
                    );

                    try {
                        ExerciseGroupService es = new ExerciseGroupService();
                        ExerciseGroup oldExerciseGroup = es.getExerciseGroupById(id);

                        name = vh.getStringFromQuestion(
                                "New name: (Previous was: " + oldExerciseGroup.name + ", leave blank to remain name unchanged)",
                                "[A-Za-zøæåØÆÅ ]*",
                                "Wrong format. Type group name again: "
                        );

                        int parentGroupId = 0;

                        if (oldExerciseGroup.parent_group_id > 0) {
                            ExerciseGroup oldParentGroup = es.getExerciseGroupById(oldExerciseGroup.parent_group_id);

                            String updatePartOf = vh.getStringFromQuestion(
                                    "The current group has a parent ([" +
                                            oldExerciseGroup.parent_group_id +
                                            "] " +
                                            oldParentGroup.name +
                                            "). Want to change it?\n[y]es or [N]o (no if empty): ",
                                    "^[YyNn]?$",
                                    "Wrong format. Type [y]es or [N]o again: "
                            );

                            if (updatePartOf.matches("^[Yy]$")) {
                                System.out.println("Here is a list of available parents:");
                                ExerciseGroupHandler.listExerciseGroupsIndexed();

                                parentGroupId = vh.getIntFromQuestion(
                                        "New parent id: (Previous was: " +
                                                oldExerciseGroup.parent_group_id +
                                                " (" +
                                                oldParentGroup.name +
                                                "), leave blank to remain id unchanged)",
                                        "[0-9]*",
                                        "Wrong format. Type parent id again: "
                                );
                            }
                        } else {
                            String updatePartOf = vh.getStringFromQuestion(
                                    "The current group does not have a parent. Want to add a parent?\n[y]es or [N]o (no if empty): ",
                                    "^[YyNn]?$",
                                    "Wrong format. Type [y]es or [N]o again: "
                            );

                            if (updatePartOf.matches("^[Yy]$")) {
                                System.out.println("Here is a list of available parents:");
                                ExerciseGroupHandler.listExerciseGroupsIndexed();

                                parentGroupId = vh.getIntFromQuestion(
                                        "New parent id: (Leave blank to remain id empty)",
                                        "[0-9]*",
                                        "Wrong format. Type parent id again: "
                                );
                            }
                        }

                        this.updateExerciseGroup(oldExerciseGroup, name, parentGroupId);
                    } catch (SQLException ex) {
                        System.out.println("Could not get previous exercise group. Try again later or change another one.");
                    }

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
                        "\n[3] Update exercise group" +
                        "\n\nType number: ",
                "^[0-3]$",
                "Please provide a number between 0 and 3: "
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

    public void updateExerciseGroup (ExerciseGroup oldExerciseGroup, String name, int parentGroupId) {
        ExerciseGroupService es = new ExerciseGroupService();

        ExerciseGroup exerciseGroup = new ExerciseGroup();
        exerciseGroup.ID = oldExerciseGroup.ID;
        exerciseGroup.name = name.length() > 0 ? name : oldExerciseGroup.name;
        exerciseGroup.parent_group_id = parentGroupId > 0 ? parentGroupId : oldExerciseGroup.parent_group_id;

        try {
            es.updateExerciseGroup(exerciseGroup);
            System.out.println("\nUpdated exercise group in database!");
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
