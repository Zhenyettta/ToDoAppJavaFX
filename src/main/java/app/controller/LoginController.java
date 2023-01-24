package app.controller;

import app.animations.Shaker;
import app.database.DatabaseHandler;
import app.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController {
    private int userId;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
            String loginText = loginUsername.getText().trim();
            String loginPwd = loginPassword.getText().trim();
            User user = new User();
            user.setUserName(loginText);
            user.setPassword(loginPwd);


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
                    Shaker usernameShaker = new Shaker(loginUsername);
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
            loader.setLocation(getClass().getResource("/app/signup.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));

            stage.showAndWait();
        });
    }

    private void showAddItemScreen() {
        loginSignUpButton.getScene().getWindow().hide(); //I can use it on any item

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/app/addItem.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));

        AddItemController addItemController = loader.getController();
        addItemController.setUserId(userId);

        stage.showAndWait();
    }


}
