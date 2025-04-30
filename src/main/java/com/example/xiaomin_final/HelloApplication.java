package com.example.xiaomin_final;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 680, 760);
        stage.setTitle("Xiaomin Guo");
        stage.setScene(scene);

        // Adding an event handler for window close request
        stage.setOnCloseRequest(event -> {
            // Perform resource cleanup here
            System.out.println("Window is closing. Cleaning up resources...");

            DatabaseHelper dbHelper = DatabaseHelper.getInstance();
            dbHelper.closeConnections();
            System.out.println("dbHelper.closeConnections()..........");
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}