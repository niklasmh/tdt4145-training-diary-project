package com.tdt4145.group131;

import com.tdt4145.group131.view.ViewHandler;

public class Main {

    public static void main(String[] args) {
        try {
            ViewHandler vh = new ViewHandler();
            vh.listExerciseGroups();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
