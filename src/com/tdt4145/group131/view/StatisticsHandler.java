package com.tdt4145.group131.view;

import com.tdt4145.group131.database.StatisticsService;
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
                        "\n\nType number: ",
                "^[0-2]$",
                "Please provide a number between 0 and 2: "
        );
    }

    public static void printBestSession(){
        try {
            Map<String, String> info  = StatisticsService.getBest_session();
            System.out.println("The best session was:");
            for (Map.Entry<String, String> entry : info.entrySet())
            {
                System.out.println(entry.getKey() + entry.getValue());
            }
        } catch (SQLException e) {}

    }
}
