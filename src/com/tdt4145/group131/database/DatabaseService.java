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

    //endregion

    // region EXERCISE related methods

    //endregion

    // region SESSION related methods


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


    public static MysqlDataSource getDatasource(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(Settings.databaseServer);
        dataSource.setDatabaseName(Settings.databaseName);
        dataSource.setUser(Settings.databaseUsername);
        dataSource.setPassword(Settings.databaseSPassword);

        return dataSource;
    }

}





