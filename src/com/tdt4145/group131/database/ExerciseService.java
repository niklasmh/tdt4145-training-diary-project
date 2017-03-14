package com.tdt4145.group131.database;

import com.tdt4145.group131.database.models.Exercise;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import java.util.LinkedList;

/**
 * Created by sindr on 3/14/2017.
 */
public class ExerciseService {

    public List<Exercise> getAllExercises() throws SQLException {
        Connection con= DatabaseService.getDatasource().getConnection();
        ResultSet rs=con.createStatement().executeQuery("Select * from exercise;");

        List<Exercise> ListOfExercises=convertResultSetToExercise(rs);

        rs.close();
        con.close();
        return ListOfExercises;
    }

    public List<Exercise> convertResultSetToExercise(ResultSet rs) throws SQLException{
        List<Exercise> exercises = new LinkedList<>();

        while(rs.next()){
            Exercise ex=new Exercise();
            ex.name=rs.getString("name");
            ex.description=rs.getString("description");
            ex.id=rs.getInt("id");
            exercises.add(ex);
        }
        return exercises;
    }

    public Exercise getExerciseById(int id) throws SQLException {
        Connection con= DatabaseService.getDatasource().getConnection();
        PreparedStatement prepstatement = con.prepareStatement("Select * from exercise WHERE id=?;");
        prepstatement.setInt(1,id);
        ResultSet rs=prepstatement.executeQuery();

        List<Exercise> exerciseList=convertResultSetToExercise(rs);

        if (exerciseList.size()!=1){
            return null;
        }
        rs.close();
        con.close();

        return exerciseList.get(0);

    }
    public boolean saveNewExercise(Exercise exercise) throws SQLException {
        Connection con = DatabaseService.getDatasource().getConnection();
        PreparedStatement prepstatement= con.prepareStatement("INSERT INTO exercise (name, description) VALUES (?,?) ");
        prepstatement.setString(1,exercise.name);
        prepstatement.setString(2,exercise.description);
        boolean didNotCockItUp = prepstatement.execute();
        con.close();
        return didNotCockItUp;
    }
    public boolean updateExercise(Exercise exercise) throws SQLException {
        try (Connection conn = DatabaseService.getDatasource().getConnection()){

            PreparedStatement prepStatement = conn.prepareStatement("UPDATE `exercise` SET name=?, description=? WHERE id=?;");
            prepStatement.setString(1, exercise.name);
            prepStatement.setString(2,exercise.description);
            prepStatement.setInt(3,exercise.id);
            boolean didntCockUp=prepStatement.execute();
            conn.close();
            return didntCockUp;
        }
    }
    public boolean deleteExercise(Exercise exercise) throws SQLException {
        return deleteExerciseById(exercise.id);
    }
    public boolean deleteExerciseById(int id) throws SQLException {
        try (Connection conn = DatabaseService.getDatasource().getConnection()) {
            PreparedStatement prepStatement = conn.prepareStatement("DELETE FROM exercise WHERE id=?");
            prepStatement.setInt(1, id);
            boolean result =  prepStatement.execute();
            conn.close();
            return result;
        }
    }
}
