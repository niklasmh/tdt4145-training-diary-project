package com.tdt4145.group131.database;

import com.tdt4145.group131.database.models.Exercise;
import com.tdt4145.group131.database.models.Workout;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sindr on 3/14/2017.
 */
public class WorkoutExerciseService {
    public static List<Exercise> getExercisesForWorkout(Workout workout) throws SQLException {
        Connection conn = DatabaseService.getDatasource().getConnection();

        LinkedList<Exercise> exercises = new LinkedList<>();

        PreparedStatement st = conn.prepareStatement("SELECT exercise_id FROM workout_exercise WHERE workout_id=?");
        st.setInt(1, workout.Id);
        ResultSet rs = st.executeQuery();

        ExerciseService es = new ExerciseService();

        while (rs.next()){
            int exercise_id = rs.getInt(0);
            exercises.add(es.getExerciseById(exercise_id));
        }
        return exercises;
    }

    public boolean saveExercisesForWorkout(Workout workout) throws SQLException {
        Connection conn = DatabaseService.getDatasource().getConnection();

        PreparedStatement ps = conn.prepareStatement("INSERT INTO workout_exercise (workout_id, exercise_id) VALUES (?,?)");

        for (Exercise ex : workout.Exercises) {
            ps.setInt(1, workout.Id);
            ps.setInt(2, ex.id);
            ps.execute();
        }
        ps.close();
        conn.close();
        return true;
    }
}
