package com.tdt4145.group131;

import com.tdt4145.group131.database.models.ExerciseGroup;
import com.tdt4145.group131.database.DatabaseService;
import com.tdt4145.group131.database.models.Session;

import java.sql.Timestamp;

public class Main {

    public static void main(String[] args) {
        try{
            DatabaseService dbs = DatabaseService.getInstance();



            Session sesh=new Session();

            Timestamp time = new Timestamp(1234566543);



            sesh.Performance=9;
            sesh.Note="Gikk bra";
            sesh.StartTime=time;
            sesh.EndTime=time;

            //dbs.saveNewSession(sesh);
            ExerciseGroup exg = new ExerciseGroup();
            exg.name = "Legs";

            dbs.saveNewExerciseGroup(exg);

            ExerciseGroup eg = dbs.getExerciseGroupById(2);
            eg.parent_group_id = 4;
            dbs.updateExerciseGroup(eg);

            dbs.getAllExerciseGroups();
            dbs.getExerciseGroupById(2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
