package com.tdt4145.group131.database;

import com.tdt4145.group131.database.models.Exercise;
import com.tdt4145.group131.database.models.ExerciseGroup;
import com.tdt4145.group131.database.models.Session;
import com.tdt4145.group131.database.models.Workout;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sindr on 3/14/2017.
 */
public class WorkoutService {
    public List<Workout> getAllWorkouts() throws SQLException {
        Connection conn = DatabaseService.getDatasource().getConnection();

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM workout");

        List<Workout> workouts = convertResultSetToWorkout(rs);
        return workouts;
    }

    public static Workout getWorkoutById(int id) throws SQLException {
        Connection con= DatabaseService.getDatasource().getConnection();
        PreparedStatement prepstatement = con.prepareStatement("Select * from workout WHERE id=?;");
        prepstatement.setInt(1,id);
        ResultSet rs=prepstatement.executeQuery();

        List<Workout> workoutList=convertResultSetToWorkout(rs);

        if (workoutList.size()!=1){
            return null;
        }
        rs.close();
        con.close();

        return workoutList.get(0);
    }


    private static List<Workout> convertResultSetToWorkout(ResultSet rs) throws SQLException{
        List<Workout> workouts = new LinkedList<>();

        WorkoutExerciseService wes = new WorkoutExerciseService();

        while(rs.next()) {
            Workout w = new Workout();
            w.Id = rs.getInt("id");
            w.Name = rs.getString("name");
            w.Description = rs.getString("description");
            w.Exercises = wes.getExercisesForWorkout(w);
            workouts.add(w);
        }

        return workouts;
    }

    public boolean saveNewWorkout(Workout workout) throws SQLException{
        try (
                Connection connection = DatabaseService.getDatasource().getConnection();
                PreparedStatement ps = connection.prepareStatement("INSERT INTO workout (name, description) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ) {

            Connection conn = DatabaseService.getDatasource().getConnection();

            // First save workout
            ps.setString(1, workout.Name);
            ps.setString(2, workout.Description);

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating workout failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    workout.Id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating workout failed, no ID obtained.");
                }
            }

            WorkoutExerciseService wes = new WorkoutExerciseService();
            wes.saveExercisesForWorkout(workout);

            // Then save the exercises
        }
        return true;
    }
    public boolean updateWorkout(Workout workout) throws SQLException {
        throw new NotImplementedException();
    }
    public boolean deleteWorkout(Workout workout) throws SQLException {
        return deleteWorkoutById(workout.Id);
    }
    public boolean deleteWorkoutById(int id) throws SQLException {
        throw new NotImplementedException();
    }
}
