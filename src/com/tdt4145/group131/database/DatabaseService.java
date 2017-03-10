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




