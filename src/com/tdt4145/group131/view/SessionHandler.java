package com.tdt4145.group131.view;

import com.tdt4145.group131.database.ExerciseService;
import com.tdt4145.group131.database.SessionService;
import com.tdt4145.group131.database.WorkoutService;
import com.tdt4145.group131.database.models.Exercise;
import com.tdt4145.group131.database.models.Session;
import com.tdt4145.group131.database.models.Workout;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

/**
 * Created by sindr on 3/16/2017.
 */
public class SessionHandler {

    Scanner scan;
    ViewHandler vh;

    public SessionHandler (Scanner scan, ViewHandler vh) {
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
                    listAllSessions(true);
                    break;
                case 2:
                    createNewSession();
                    break;
                case 3:
                    break;
            }
        }
    }

    public int getMenuSelect () {
        return vh.getIntFromQuestion(
                "This is the exercise menu." +
                        "\nMenu / Exercise menu:" +
                        "\n[0] Go back" +
                        "\n[1] List all sessions" +
                        "\n[2] Add session" +
                        "\n\nType number: ",
                "^[0-2]$",
                "Please provide a number between 0 and 2: "
        );
    }

    public void listAllSessions(boolean withIndex){
        SessionService ss = new SessionService();

        try{
            List<Session> sessionList = SessionService.getAllSessions();
            System.out.println("[id] Workout performed - Session start - session end");
            for (Session s : sessionList) {
                if (withIndex){
                    System.out.print("[" + s.id + "] ");
                }
                System.out.println(s.Type.Name + " - " + s.StartTime.toString() + " - " + s.EndTime.toString());
            }
        }catch (SQLException sqlExeption){
            System.out.println("We're unable to fetch the sessions at this time");
        }catch (Exception e) {
            System.out.println("Something's seriously wrong...");
        }
    }

    public void createNewSession(){
        System.out.print("Which workout did you perform?");
        WorkoutHandler.listWorkoutsIndexed();
        int workout_id = vh.getIntFromQuestion(
                "Input workout id:",
                "[0-9]*",
                "Try again with a valid id");
        System.out.print("Time is in the following format: yyyy-mm-dd hh:mm:ss");
        Timestamp start_time = vh.getTimestampFromQuestion(
                "When did the session start: ",
                "",
                "Please enter a valid date");
        Timestamp end_time = vh.getTimestampFromQuestion(
                "When did the session end: ",
                "",
                "Please enter a valid time");
        int performance = vh.getIntFromQuestion(
                "Rate your performance [0-9]: ",
                "[0-9]{1}",
                "Wrong format. Type performance again: ");
        String note = vh.getStringFromQuestion(
                "Enter an optional note",
                "[A-Za-zøæåØÆÅ. ]+",
                "Wrong format. Type note again: ");

        try {
            Workout workout = WorkoutService.getWorkoutById(workout_id);
            Session new_session = new Session();
            new_session.StartTime = start_time;
            new_session.EndTime = end_time;
            new_session.Performance = performance;
            new_session.Type = workout;

            SessionService ss = new SessionService();
            ss.saveNewSession(new_session);
        }catch (SQLException sqlException){
            System.out.println("Something went wrong on our end. Try again");
        }
    }
}
