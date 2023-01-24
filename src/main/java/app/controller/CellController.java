package app.controller;

import app.database.DatabaseHandler;
import app.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CellController extends ListCell<Task> {

    @FXML
    private Label dateLabel;

    @FXML
    private ImageView deleteButton;
    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView iconImageView;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label taskLabel;
    private FXMLLoader fxmlLoader;
    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {


    }

    @Override
    protected void updateItem(Task myTask, boolean b) {
        super.updateItem(myTask, b);

        if (b || myTask == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/app/view/cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            taskLabel.setText(myTask.getTask());
            dateLabel.setText(myTask.getDateCreated().toString().replaceAll(".0$",""));
            descriptionLabel.setText(myTask.getDescription());

            int taskId = myTask.getTaskId();

            deleteButton.setOnMouseClicked(event-> {
                getListView().getItems().remove(getItem());
                databaseHandler = new DatabaseHandler();
                databaseHandler.deleteTask(AddItemController.userId, taskId);
            });

            setText(null);
            setGraphic(rootAnchorPane);
        }

    }
}
