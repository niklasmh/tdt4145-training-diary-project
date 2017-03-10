package com.tdt4145.group131.database;

import com.mysql.jdbc.MySQLConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.activation.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

    private MysqlDataSource getDatasource(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(Settings.databaseServer);
        dataSource.setDatabaseName(Settings.databaseName);
        dataSource.setUser(Settings.databaseUsername);
        dataSource.setPassword(Settings.databaseSPassword);

        return dataSource;
    }

    public void printAllExerciseGroups(){
        MysqlDataSource dataSource = getDatasource();
        try {
            Connection conn = dataSource.getConnection();
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
    }


    public boolean saveNewExerciseGroup(ExerciseGroup exerciseGroup){
        try {
            Connection conn = getDatasource().getConnection();

            PreparedStatement prepStatement = conn.prepareStatement("INSERT INTO exercise_group (name) VALUES ( ? );");
            prepStatement.setString(1, exerciseGroup.name);

            boolean success =  prepStatement.execute();

            prepStatement.close();
            conn.close();
            return success;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
}




