package com.tdt4145.group131;

import com.tdt4145.group131.database.WorkoutExerciseService;
import com.tdt4145.group131.database.WorkoutService;
import com.tdt4145.group131.database.models.ExerciseGroup;
import com.tdt4145.group131.database.DatabaseService;
import com.tdt4145.group131.database.models.Session;
import com.tdt4145.group131.database.models.Workout;

import java.sql.Timestamp;

public class Main {

    public static void main(String[] args) {
        try{
            WorkoutService ws = new WorkoutService();

            Workout w = new Workout();
            w.Name = "B routine";
            w.Description = "B routine in starting strength program";

            ws.saveNewWorkout(w);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
