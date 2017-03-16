package com.tdt4145.group131.view;

import com.tdt4145.group131.database.SessionService;
import com.tdt4145.group131.database.StatisticsService;
import com.tdt4145.group131.database.WorkoutService;
import com.tdt4145.group131.database.models.Session;
import com.tdt4145.group131.database.models.Workout;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by sindr on 3/16/2017.
 */
public class StatisticsHandler {
    Scanner scan;
    ViewHandler vh;

    public StatisticsHandler (Scanner scan, ViewHandler vh) {
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
                    printBestSession();
                    break;
                case 2:
                    break;
                case 3:
                    printStatisticsLastThirtyDays();
                    break;
            }
        }
    }

    public int getMenuSelect () {
        return vh.getIntFromQuestion(
                "This is the statistics menu." +
                        "\nMenu / statistics menu:" +
                        "\n[0] Go back" +
                        "\n[1] Get best session" +
                        "\n[2] Get sessions this month" +
                        "\n[3] Get statistics last 30 days" +
                        "\n\nType number: ",
                "^[0-3]$",
                "Please provide a number between 0 and 3: "
        );
    }

    public void printBestSession(){
        try {
            WorkoutHandler.listWorkoutsIndexed();
            int workout_id = vh.getIntFromQuestion("Which workout do you want the best session for: ", "[0-9]*", "Try that again: ");
            Workout selected_workout = WorkoutService.getWorkoutById(workout_id);
            Session best_session = SessionService.getBestSessionByWorkout(selected_workout);
            System.out.println("The best session was:");
            System.out.println("Start time: " + best_session.StartTime.toString());
            System.out.println("End time: " + best_session.EndTime.toString());
            System.out.println("Performance: " + best_session.Performance);
            System.out.println("Note: " + best_session.Note);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printStatisticsLastThirtyDays(){
        try {
            Map<String, String> info  = StatisticsService.getStatisticsForLastMonth();
            System.out.println("Statistics last 30 days:");
            for (Map.Entry<String, String> entry : info.entrySet())
            {
                System.out.println(entry.getKey() + entry.getValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
