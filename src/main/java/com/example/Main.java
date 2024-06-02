package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

public static String OPENAI_API_KEY = "";

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/transcription.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Transcription Service");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        if (args.length > 0) {
            System.out.println("Arguments passed to the main method:");
            for (int i = 0; i < args.length; i++) {
                System.out.println("Argument " + (i + 1) + ": " + args[i]);
            }

            String firstArgument = args[0];
            OPENAI_API_KEY = firstArgument;
        } else {
            System.out.println("No arguments were passed to the main method, please provide openai api key as command line argument.");
        }

        launch(args);
    }
}
