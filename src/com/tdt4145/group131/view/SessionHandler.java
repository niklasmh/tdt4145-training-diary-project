package com.tdt4145.group131.view;

import com.tdt4145.group131.database.ExerciseService;
import com.tdt4145.group131.database.SessionService;
import com.tdt4145.group131.database.models.Exercise;
import com.tdt4145.group131.database.models.Session;

import java.sql.SQLException;
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
                System.out.print(s.Type.Name + " - " + s.StartTime.toString() + " - " + s.EndTime.toString());
            }
        }catch (SQLException sqlExeption){
            System.out.println("We're unable to fetch the sessions at this time");
        }catch (Exception e) {
            System.out.println("Something's seriously wrong...");
        }
    }
}
