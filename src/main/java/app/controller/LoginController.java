package app.controller;

import app.animations.Shaker;
import app.database.DatabaseHandler;
import app.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    private static int userId;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private TextField loginUsername;
    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {

        loginButton.setOnAction(event -> {
            databaseHandler = new DatabaseHandler();
            String loginUsr = this.loginUsername.getText().trim();
            String loginPsw = loginPassword.getText().trim();

            User user = new User(loginUsr, loginPsw);
            ResultSet resultSet = databaseHandler.getUser(user);

            int counter = 0;
            try {
                while (resultSet.next()) {
                    counter++;
                    userId = resultSet.getInt("userid");
                    String name = resultSet.getString("firstName");
                    System.out.println(name);
                }
                if (counter == 1) {
                    showAddItemScreen();
                } else {
                    Shaker usernameShaker = new Shaker(this.loginUsername);
                    Shaker passwordShaker = new Shaker(loginPassword);
                    usernameShaker.shake();
                    passwordShaker.shake();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


        loginSignUpButton.setOnAction(event -> {
            loginSignUpButton.getScene().getWindow().hide(); //I can use it on any item
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/app/view/signup.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/app/assets/todo_icon.png"))));
            stage.showAndWait();
        });
    }

    private void showAddItemScreen() {
        loginSignUpButton.getScene().getWindow().hide(); //I can use it on any item
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/app/view/addItem.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AddItemController addItemController = loader.getController();
        addItemController.setUserId(userId);

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/app/assets/todo_icon.png"))));
        stage.showAndWait();
    }

    public static int getUserId() {
        return userId;
    }
}
