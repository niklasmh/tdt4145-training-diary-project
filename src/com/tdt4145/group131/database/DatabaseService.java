package com.tdt4145.group131.database;

import com.mysql.jdbc.MySQLConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.activation.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import com.tdt4145.group131.Settings;
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

}




