package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        primaryStage.setTitle("2DO");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/app/assets/todo_icon.png"))));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}