package app.controller;

import app.animations.Shaker;
import app.database.DatabaseHandler;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddItemController {
    public static int userId;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label noTaskLabel;

    @FXML
    private ImageView addButton;

    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private ImageView viewTasksButton;

    @FXML
    void initialize() throws SQLException {
        getTaskCount();

        viewTasksButton.setOnMouseClicked(event -> {
            viewTasksButton.getScene().getWindow().hide();

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
            stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/app/assets/todo_icon.png"))));
            stage.show();
        });

        addButton.setOnMouseClicked(event ->
        {
            Shaker buttonShaker = new Shaker(addButton);
            buttonShaker.shake();

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), addButton);
            FadeTransition labelTransition = new FadeTransition(Duration.millis(2000), noTaskLabel);

            addButton.relocate(0, 0);
            noTaskLabel.relocate(0, 0);
            addButton.setDisable(true);
            addButton.setOpacity(0);
            noTaskLabel.setOpacity(0);

            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0f);
            fadeTransition.setCycleCount(1);
            fadeTransition.setAutoReverse(false);

            fadeTransition.play();

            labelTransition.setFromValue(1f);
            labelTransition.setToValue(0f);
            labelTransition.setCycleCount(1);
            labelTransition.setAutoReverse(false);
            labelTransition.play();

            try {
                AnchorPane formPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/app/view/addItemForm.fxml")));
                AddItemController.userId = getUserId();

                FadeTransition rootTransition = new FadeTransition(Duration.millis(1000), formPane);
                rootTransition.setFromValue(0f);
                rootTransition.setToValue(1f);
                rootTransition.setCycleCount(1);
                rootTransition.setAutoReverse(false);
                rootTransition.play();
                rootAnchorPane.getChildren().setAll(formPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void getTaskCount() throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        int count = databaseHandler.getAllTasksForToday();
        if (count != 0) {
            noTaskLabel.setText("You have " + count + " tasks for today");
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        AddItemController.userId = userId;
    }


}
