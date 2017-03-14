package com.tdt4145.group131.database;

import com.tdt4145.group131.database.models.ExerciseGroup;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sindr on 3/14/2017.
 */
public class ExerciseGroupService {
    public List<ExerciseGroup> getAllExerciseGroups() throws SQLException {

        Connection con = DatabaseService.getDatasource().getConnection();
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
        Connection conn = DatabaseService.getDatasource().getConnection();

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
        try (Connection conn = DatabaseService.getDatasource().getConnection()) {
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
        try (Connection conn = DatabaseService.getDatasource().getConnection()){

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
        return deleteExerciseGroupById(exerciseGroup.ID);
    }

    public boolean deleteExerciseGroupById(int id) throws SQLException {
        try (Connection conn = DatabaseService.getDatasource().getConnection()) {
            PreparedStatement prepStatement = conn.prepareStatement("DELETE FROM exercise_group WHERE id=?");
            prepStatement.setInt(1, id);
            boolean result =  prepStatement.execute();
            conn.close();
            return result;
        }
    }
}
