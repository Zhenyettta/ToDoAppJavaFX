package app.controller;

import app.database.DatabaseHandler;
import app.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ListController {

    @FXML
    private ListView<Task> listTask;

    @FXML
    private TextField listDescriptionField;

    @FXML
    private Button listSaveTaskButton;

    @FXML
    private TextField listTaskField;

    @FXML
    private AnchorPane roorAnchorPane;

    @FXML
    private Button todosButton;
    private ObservableList<Task> tasks;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() throws SQLException {
        tasks = FXCollections.observableArrayList();

        databaseHandler = new DatabaseHandler();
        ResultSet myResult = databaseHandler.getTasksByUser(AddItemController.userId);

        while (myResult.next()) {
            Task task = new Task();
            task.setTask(myResult.getString("task"));
            task.setTaskId(myResult.getInt("taskid"));
            task.setDateCreated(myResult.getTimestamp("datecreated"));
            task.setDescription(myResult.getString("description"));
            tasks.add(task);
        }
        listTask.setItems(tasks);
        listTask.setCellFactory(CellController -> new CellController());
        listSaveTaskButton.setOnAction(event -> addNewTask());
    }

    public void addNewTask() {

        if (!listTaskField.getText().trim().equals("")) {
            Task myNewTask = new Task();
            myNewTask.setTask(listTaskField.getText().trim());
            myNewTask.setDescription(listDescriptionField.getText().trim());
            myNewTask.setUserId(AddItemController.userId);

            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            myNewTask.setDateCreated(timestamp);

            databaseHandler.insertTask(myNewTask);

            listTaskField.setText("");
            listDescriptionField.setText("");

            tasks.add(0, myNewTask);
            try {
                initialize();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}

