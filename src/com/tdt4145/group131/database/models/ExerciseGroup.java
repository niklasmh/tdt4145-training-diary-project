package com.tdt4145.group131.database.models;

/**
 * Created by sindr on 3/10/2017.
 */
public class ExerciseGroup {

    public int ID;
    public String name;
    public int parent_group_id;
    public ExerciseGroup parent_group;

    public void setParentGroup(ExerciseGroup parent_group){
        this.parent_group_id = parent_group.ID;
        this.parent_group = parent_group;
    }

}
