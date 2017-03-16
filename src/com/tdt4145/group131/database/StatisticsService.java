package com.tdt4145.group131.database;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import java.util.List;
import java.util.LinkedList;

import com.tdt4145.group131.database.models.ExerciseGroup;
import com.tdt4145.group131.database.models.Session;
import com.tdt4145.group131.database.models.Workout;
import com.tdt4145.group131.database.models.Exercise;
import com.tdt4145.group131.database.SessionService;
import com.tdt4145.group131.database.WorkoutService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by KGMK931 on 16/03/2017.
 */
public class StatisticsService {
    public static Map<String,String> getBest_session() throws SQLException {
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

    public static String printAllNotes() throws SQLException{
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

    public static Map<String, String> getStatisticsForLastMonth() throws SQLException {
        Map<String, String> map = new HashMap<>();

        map.put("Number of sessions: ", String.valueOf(getCountSessionsLastThirtyDays()));
        map.put("Total session time: ", getTotalSessionTimeLastThirtyDays().toString());

        return map;
    }

    public static Map<String, String> getBestSessionLastMonthByWorkout(Workout workout){
        throw new NotImplementedException();
    }

    private static Time getTotalSessionTimeLastThirtyDays() throws SQLException {
        String totalExerciseTimeStatement = "SELECT SEC_TO_TIME(SUM(TIME_TO_SEC(TIMEDIFF(`end_datetime`,`start_datetime`)))) FROM session WHERE start_datetime BETWEEN (CURRENT_DATE() - INTERVAL 1 MONTH) AND (CURDATE() + INTERVAL 1 DAY)";

        Connection conn = DatabaseService.getDatasource().getConnection();

        Statement statement = conn.createStatement();

        ResultSet rs = statement.executeQuery(totalExerciseTimeStatement);

        if(rs.next()){
            return rs.getTime(1);
        }
        return null;

    }
    private static int getCountSessionsLastThirtyDays() throws SQLException {
        String totalExerciseTimeStatement = "SELECT COUNT(*) FROM session WHERE start_datetime BETWEEN (CURRENT_DATE() - INTERVAL 1 MONTH) AND (CURDATE() + INTERVAL 1 DAY)";

        Connection conn = DatabaseService.getDatasource().getConnection();

        Statement statement = conn.createStatement();

        ResultSet rs = statement.executeQuery(totalExerciseTimeStatement);

        if(rs.next()){
            return rs.getInt(1);
        }
        return 0;

    }



}
