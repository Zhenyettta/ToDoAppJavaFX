package app.todoappjavafx.controller;

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
import java.util.ResourceBundle;

public class LoginController {

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

    @FXML
    void initialize() {
        String loginText = loginUsername.getText().trim();
        String loginPwd = loginPassword.getText().trim();

        loginSignUpButton.setOnAction(event->{
            loginSignUpButton.getScene().getWindow().hide(); //I can use it on any item

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/app/todoappjavafx/signup.fxml"));

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

        loginButton.setOnAction(event -> {

            if (!loginText.equals("") || !loginPwd.equals("")) {
                loginUser(loginText, loginPwd);
            } else System.out.println("Error logging");
        });



    }

    private void loginUser(String userName, String password) {
        //Check database
    }

}
