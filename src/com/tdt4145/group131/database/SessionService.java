package com.tdt4145.group131.database;

import com.tdt4145.group131.database.models.Exercise;
import com.tdt4145.group131.database.models.Session;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by sindr on 3/14/2017.
 */
public class SessionService {

    public List<Session> getAllSessions() throws SQLException {
        throw new NotImplementedException();
    }
    public Exercise getSessionById() throws SQLException {
        throw new NotImplementedException();
    }
    public boolean saveNewSession(Session session){
        try {
            Connection conn = DatabaseService.getDatasource().getConnection();

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
}
