package app.controller;

import app.database.DatabaseHandler;
import app.models.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddItemFormController {


    private int userId;
    private DatabaseHandler databaseHandler;

    @FXML
    private ResourceBundle resources;

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
            Calendar calendar = Calendar.getInstance();
            Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

            Task task = new Task(AddItemController.userId,timestamp, descriptionField.getText(), taskField.getText());
            databaseHandler.insertTask(task);


        });

    }

}
