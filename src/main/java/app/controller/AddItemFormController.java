package app.controller;

import app.database.DatabaseHandler;
import app.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddItemFormController {


    private DatabaseHandler databaseHandler;
    private int taskNumber;

    @FXML
    private ResourceBundle resources;
    @FXML
    private Label succesLabel;

    @FXML
    private URL location;
    @FXML
    private Button todosButton;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button saveTaskButton;

    @FXML
    private TextField taskField;

    @FXML
    void initialize() {
        databaseHandler = new DatabaseHandler();
        saveTaskButton.setOnAction(event ->
        {
            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));


            if (!taskField.getText().equals("")) {
                Task task = new Task(AddItemController.userId, timestamp, descriptionField.getText(), taskField.getText());
                databaseHandler.insertTask(task);

                todosButton.setVisible(true);
                succesLabel.setVisible(true);

                try {
                    taskNumber = databaseHandler.getAllTasks(AddItemController.userId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                todosButton.setText("My tasks: " + taskNumber);
                taskField.setText("");
                descriptionField.setText("");

                todosButton.setOnAction(event1 -> {
                    todosButton.getScene().getWindow().hide();

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/app/view/list.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Parent parent = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(parent));
                    stage.show();
                });
            }
        });
    }
}
