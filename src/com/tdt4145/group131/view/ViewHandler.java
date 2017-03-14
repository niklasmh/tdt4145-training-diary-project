package com.tdt4145.group131.view;

import com.tdt4145.group131.database.*;
import com.tdt4145.group131.database.models.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Niklas on 14.03.2017.
 */
public class ViewHandler {

    public ViewHandler () {

    }
    public void listExercises () {
        System.out.println();
    }
    public void listExerciseGroups () {
        ExerciseGroupService egs = new ExerciseGroupService();
        try {
            List<ExerciseGroup> list = egs.getAllExerciseGroups();
            for (ExerciseGroup eg : list)
                System.out.println(eg);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
