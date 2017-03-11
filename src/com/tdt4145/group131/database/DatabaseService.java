package com.tdt4145.group131.database;

import com.mysql.jdbc.MySQLConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.activation.DataSource;
import java.sql.*;
import java.util.*;

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
    private Connection connection;


    private DatabaseService() throws SQLException {
        this.datasource = getDatasource();
        this.connection = getDatasource().getConnection();
    }



    public List<ExerciseGroup> getAllExerciseGroups() throws SQLException{
        LinkedList<ExerciseGroup> listOfExerciseGroups = new  LinkedList<ExerciseGroup>();

        ResultSet rs = connection.createStatement().executeQuery("SELECT * from exercise_group");

        // Convert all results to exercise groups
        while(rs.next()) {
            ExerciseGroup g = new ExerciseGroup();
            g.ID = rs.getInt("id");
            g.name = rs.getString("name");
            g.parent_group_id = rs.getInt("part_of");
        }

        // Link exercise parent_group_id
        for (int i = 0; i < listOfExerciseGroups.size(); i++) {

        }

        rs.close();
        return listOfExerciseGroups;
    }


    public boolean saveNewExerciseGroup(ExerciseGroup exerciseGroup){
       try {
           PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO exercise_group (name) VALUES ( ? );");
           prepStatement.setString(1, exerciseGroup.name);
           return prepStatement.execute();
       } catch (Exception e){}
       return false;

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
    public boolean saveNewWorkout(Workout workout){
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

    public void saveExerciseGroup(ExerciseGroup exerciseGroup){
        // TODO: Implement
        throw new NotImplementedException();
    }

    public ExerciseGroup getExerciseGroup(){
        try {
            Connection conn = getDatasource().getConnection();

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * from exercise_group");

            while(rs.next()) {
                System.out.println(rs.getString("name"));
            }

            rs.close();

            stmt.close();

            conn.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        throw new NotImplementedException();
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




