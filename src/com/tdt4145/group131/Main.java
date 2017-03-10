package com.tdt4145.group131;

import com.tdt4145.group131.database.models.ExerciseGroup;
import com.tdt4145.group131.database.DatabaseService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/com/tdt4145/group131/application.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Training Diary");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
