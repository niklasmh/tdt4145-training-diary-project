package com.tdt4145.group131.database;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import java.util.List;
import java.util.LinkedList;
import com.tdt4145.group131.database.models.Session;
import com.tdt4145.group131.database.models.Workout;
import com.tdt4145.group131.database.models.Exercise;
import com.tdt4145.group131.database.SessionService;
import com.tdt4145.group131.database.WorkoutService;

/**
 * Created by KGMK931 on 16/03/2017.
 */
public class Statistics {
    Map<String,String> getBest_session() throws SQLException {
        Map<String, String> map = new HashMap<>();
        List<Session> all_sessions = SessionService.getAllSessions();
        int bestPerformance=0;
        int bestId=0;
        for(Session element : all_sessions){
            if(element.Performance>=bestPerformance){
                bestPerformance=element.Performance;
                bestId=element.id;
            }
        }
        Session best_session = SessionService.getSessionById(bestId);
        String stringFormance=Integer.toString(best_session.Performance);
        map.put("Performance: ",stringFormance);
        map.put("Start time: ",best_session.StartTime.toString());
        map.put("End time: ",best_session.EndTime.toString());
        Workout best_workout=best_session.Type;
        List<Exercise> best_exercises = WorkoutExerciseService.getExercisesForWorkout(best_workout);
        String best_exercise_string="\n";
        for(Exercise exercise : best_exercises){
            best_exercise_string+=exercise.name;
            best_exercise_string+="\n";
        }
        map.put("Exercises:",best_exercise_string);
        map.put("Note:\n", best_session.Note);

        return map;
    }

    String printAllNotes() throws SQLException{
        List<Session> all_sessions = SessionService.getAllSessions();
        String all_notes="";
        for (Session sesh : all_sessions){
            all_notes+="Date: ";
            all_notes+=sesh.StartTime.getTime();
            all_notes+="\n";
            all_notes+=sesh.Note;
            all_notes+="\n\n";
        }
        return all_notes;
    }



}
