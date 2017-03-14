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
                    // Handle the add exercise group.
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
