package com.tdt4145.group131.database;

import com.tdt4145.group131.database.models.Exercise;
import com.tdt4145.group131.database.models.Session;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.List;
import java.util.LinkedList;

/**
 * Created by sindr on 3/14/2017.
 */
public class SessionService {

    public List<Session> getAllSessions() throws SQLException {
        Connection conn=DatabaseService.getDatasource().getConnection();
        ResultSet rs= conn.createStatement().executeQuery("SELECT * FROM session;");

        List<Session> ListOfSessions=convertSessionResultSetToList(rs);

        rs.close();
        conn.close();
        return ListOfSessions;
    }

    public List<Session> convertSessionResultSetToList(ResultSet rs) throws SQLException{
        List<Session> sessions = new LinkedList<>();

        while(rs.next()){
            Session sesh=new Session();
            sesh.EndTime=rs.getTimestamp("end_datetime");
            sesh.StartTime=rs.getTimestamp("start_datetime");
            sesh.Note=rs.getString("note");
            sesh.Performance=rs.getInt("preformance");
            int workoutId = rs.getInt("workout_id");
            sesh.Type=getWorkoutById(workoutId);
            sesh.id=rs.getInt("id");
            sessions.add(sesh);
        }
        return sessions;
    }


    public Session getSessionById(int id) throws SQLException {
        Connection con= DatabaseService.getDatasource().getConnection();
        PreparedStatement prepstatement = con.prepareStatement("Select * from session WHERE id=?;");
        prepstatement.setInt(1,id);
        ResultSet rs=prepstatement.executeQuery();

        List<Session> sessionList=convertSessionResultSetToList(rs);

        if (sessionList.size()!=1){
            return null;
        }
        rs.close();
        con.close();

        return sessionList.get(0);
    }
    public boolean saveNewSession(Session session){
        try {
            Connection conn = DatabaseService.getDatasource().getConnection();

            PreparedStatement prepStatement = conn.prepareStatement("INSERT INTO session (preformance, note, start_datetime, end_datetime ) VALUES ( ?,?,?,? );");
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
        try (Connection conn = DatabaseService.getDatasource().getConnection()){

            PreparedStatement prepStatement = conn.prepareStatement("UPDATE `session` SET start_datetime=?, end_datetime=?, preformance=?, note=?, workout_id=? WHERE id=?;");
            prepStatement.setTimestamp(1, session.StartTime); //Usikker på om disse datatypene passer sammen.
            prepStatement.setTimestamp(2,session.EndTime);
            prepStatement.setInt(3,session.Performance);
            prepStatement.setString(4,session.Note);
            int workoutId=session.Type.Id;
            prepStatement.setInt(5,workoutId);
            prepStatement.setInt(5,session.id);
            boolean didntCockUp=prepStatement.execute();
            conn.close();
            return didntCockUp;
        }
    }
    public boolean deleteSession(Session session) throws SQLException {
        return deleteSessionById(session.id);
    }
    public boolean deleteSessionById(int id) throws SQLException {
        try (Connection conn = DatabaseService.getDatasource().getConnection()) {
            PreparedStatement prepStatement = conn.prepareStatement("DELETE FROM session WHERE id=?;");
            prepStatement.setInt(1, id);
            boolean result =  prepStatement.execute();
            conn.close();
            return result;
        }
    }
}
