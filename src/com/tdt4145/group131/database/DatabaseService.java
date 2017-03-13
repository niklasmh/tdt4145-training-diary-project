package com.tdt4145.group131.database;

import com.mysql.jdbc.MySQLConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.activation.DataSource;
import java.sql.*;
import java.util.*;

import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
import com.tdt4145.group131.Settings;
import com.tdt4145.group131.database.models.ExerciseGroup;
import com.tdt4145.group131.database.models.Session;
import com.tdt4145.group131.database.models.Goal;
import com.tdt4145.group131.database.models.Exercise;
import com.tdt4145.group131.database.models.Workout;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by sindr on 3/10/2017.
 */
public class DatabaseService {


    // Singleton
    private static DatabaseService instance = null;
    public static DatabaseService getInstance() throws SQLException{
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }


    private MysqlDataSource datasource;

    private DatabaseService() throws SQLException {
        this.datasource = getDatasource();
    }


    //region EXERCISE GROUP related methods
    public List<ExerciseGroup> getAllExerciseGroups() throws SQLException{

        Connection con = getDatasource().getConnection();
        ResultSet rs = con.createStatement().executeQuery("SELECT * from exercise_group");

        // Convert all results to exercise groups
        List<ExerciseGroup> listOfExerciseGroups = convertResultSetToExerciseGroups(rs);

        // Link exercise parent_group_id

        for (ExerciseGroup g : listOfExerciseGroups){
            if (g.parent_group_id != 0) {
                g.setParentGroup(getExerciseGroupById(g.parent_group_id));
            }
        }

        con.close();
        rs.close();
        return listOfExerciseGroups;
    }

    public ExerciseGroup getExerciseGroupById(int id) throws SQLException {
        Connection conn = getDatasource().getConnection();

        PreparedStatement prepStatement = conn.prepareStatement("SELECT * from exercise_group WHERE id = ?;");
        prepStatement.setInt(1, id);
        ResultSet rs = prepStatement.executeQuery();

        List<ExerciseGroup> exerciseGroups = convertResultSetToExerciseGroups(rs);

        if (exerciseGroups.size() != 1) {
            return null;
        }

        rs.close();
        conn.close();
        return exerciseGroups.get(0);
    }

    private List<ExerciseGroup> convertResultSetToExerciseGroups(ResultSet rs) throws SQLException{
        List<ExerciseGroup> exerciseGroups = new LinkedList<>();

        while(rs.next()) {
            ExerciseGroup g = new ExerciseGroup();
            g.ID = rs.getInt("id");
            g.name = rs.getString("name");
            g.parent_group_id = rs.getInt("part_of");
            exerciseGroups.add(g);
        }

        return exerciseGroups;
    }

    public boolean saveNewExerciseGroup(ExerciseGroup exerciseGroup) throws SQLException{
        try (Connection conn = getDatasource().getConnection()) {
            PreparedStatement prepStatement = conn.prepareStatement("INSERT INTO exercise_group (name, part_of) VALUES ( ?, ? );");
            prepStatement.setString(1, exerciseGroup.name);
            if(exerciseGroup.parent_group_id == 0) {
                prepStatement.setNull(2, Types.INTEGER);
            } else {
                prepStatement.setInt(2, exerciseGroup.parent_group_id);
            }
            boolean result =  prepStatement.execute();
            conn.close();
            return result;
        }
    }

    public boolean updateExerciseGroup(ExerciseGroup exerciseGroup) throws SQLException {
        try (Connection conn = getDatasource().getConnection()){

            PreparedStatement prepStatement = conn.prepareStatement("UPDATE `exercise_group` SET name=?, part_of=? WHERE id=?;");
            prepStatement.setString(1, exerciseGroup.name);
            if(exerciseGroup.parent_group_id == 0) {
                prepStatement.setNull(2, Types.INTEGER);
            } else {
                prepStatement.setInt(2, exerciseGroup.parent_group_id);
            }

            prepStatement.setInt(3, exerciseGroup.ID);

            boolean result =  prepStatement.execute();
            conn.close();
            return result;
        }
    }

    public boolean deleteExerciseGroup(ExerciseGroup exerciseGroup) throws SQLException {
        return deleteExerciseById(exerciseGroup.ID);
    }

    public boolean deleteExerciseGroupById(int id) throws SQLException {
        throw new NotImplementedException();
    }
    //endregion

    // region EXERCISE related methods
    public List<Exercise> getAllExercises() throws SQLException {
        throw new NotImplementedException();
    }
    public Exercise getExerciseById() throws SQLException {
        throw new NotImplementedException();
    }
    public boolean saveNewExercise(Exercise exercise) throws SQLException {
        throw new NotImplementedException();
    }
    public boolean updateExercise(Exercise exercise) throws SQLException {
        throw new NotImplementedException();
    }
    public boolean deleteExercise(Exercise exercise) throws SQLException {
        throw new NotImplementedException();
    }
    public boolean deleteExerciseById(int id) throws SQLException {
        throw new NotImplementedException();
    }
    //endregion

    // region SESSION related methods

    public List<Session> getAllSessions() throws SQLException {
        throw new NotImplementedException();
    }
    public Exercise getSessionById() throws SQLException {
        throw new NotImplementedException();
    }
    public boolean saveNewSession(Session session){
        try {
            Connection conn = getDatasource().getConnection();

            PreparedStatement prepStatement = conn.prepareStatement("INSERT INTO session (performance, note, start_datetime, end_datetime ) VALUES ( ?,?,?,? );");
            prepStatement.setInt(1, session.Performance);
            prepStatement.setString(2, session.Note);
            prepStatement.setTimestamp(3, session.StartTime);
            prepStatement.setTimestamp(4, session.EndTime);

            boolean success =  prepStatement.execute();

            prepStatement.close();
            conn.close();
            return success;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateSession(Session session) throws SQLException {
        throw new NotImplementedException();
    }
    public boolean deleteSession(Session session) throws SQLException {
        throw new NotImplementedException();
    }
    public boolean deleteSessionById(int id) throws SQLException {
        throw new NotImplementedException();
    }
    // endregion

    // region WORKOUT
    public List<Workout> getAllWorkouts() throws SQLException {
        throw new NotImplementedException();
    }
    public Workout getWorkoutById() throws SQLException {
        throw new NotImplementedException();
    }
    public boolean saveNewWorkout(Workout workout) throws SQLException {
        try {
            Connection conn = getDatasource().getConnection();

            PreparedStatement prepStatement = conn.prepareStatement("INSERT INTO workout (name,description) VALUES ( ?,? );");

            prepStatement.setString(1, workout.Name);
            prepStatement.setString(2, workout.Description);

            boolean success =  prepStatement.execute();

            prepStatement.close();
            conn.close();
            return success;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateWorkout(Workout workout) throws SQLException {
        throw new NotImplementedException();
    }
    public boolean deleteWorkout(Workout workout) throws SQLException {
        throw new NotImplementedException();
    }
    public boolean deleteWorkoutById(Workout workout) throws SQLException {
        throw new NotImplementedException();
    }
    // endregion

    // region GOAL
    public boolean saveNewGoal(Goal goal){
        try {
            Connection conn = getDatasource().getConnection();

            PreparedStatement prepStatement = conn.prepareStatement("INSERT INTO goal (GoalSetTime, GoalDeadline, MaxLoadGoal, DistanceGoal, TimeGoal,SpeedGoal) VALUES ( ?,?,?,?,?,? );");

            prepStatement.setTimestamp(1, goal.goalSetTime);
            prepStatement.setTimestamp(2, goal.goalDeadline);
            prepStatement.setFloat(3,goal.maxLoadGoal);
            prepStatement.setFloat(4,goal.distanceGoal);
            prepStatement.setFloat(5,goal.timeGoal);
            prepStatement.setFloat(6,goal.speedGoal);

            boolean success =  prepStatement.execute();

            prepStatement.close();
            conn.close();
            return success;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private MysqlDataSource getDatasource(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(Settings.databaseServer);
        dataSource.setDatabaseName(Settings.databaseName);
        dataSource.setUser(Settings.databaseUsername);
        dataSource.setPassword(Settings.databaseSPassword);

        return dataSource;
    }

}





