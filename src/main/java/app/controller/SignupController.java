package app.controller;

import app.database.DatabaseHandler;
import app.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class SignupController {
    @FXML
    private Pane pane;
    @FXML
    private Pane pane1;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signUpButton;

    @FXML
    private CheckBox signUpCheckBoxFemale;

    @FXML
    private CheckBox signUpCheckBoxMale;

    @FXML
    private TextField signUpFirstName;

    @FXML
    private TextField signUpLastName;

    @FXML
    private TextField signUpLocation;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private TextField signUpUsername;

    @FXML
    void initialize() {
        AtomicBoolean checkMale = new AtomicBoolean(true);
        AtomicBoolean checkFemale = new AtomicBoolean(true);


        signUpButton.setOnAction(event -> createUser());
        checkBoxes(checkMale, signUpCheckBoxMale, signUpCheckBoxFemale);
        checkBoxes(checkFemale, signUpCheckBoxFemale, signUpCheckBoxMale);
        


    }

    private void checkBoxes(AtomicBoolean checkMale, CheckBox signUpCheckBoxMale, CheckBox signUpCheckBoxFemale) { //Java AutoDuplicate
        signUpCheckBoxMale.setOnAction(event -> {
            if (checkMale.get()) {
                signUpCheckBoxFemale.setDisable(true);
                checkMale.set(false);
                return;
            }
            if (!checkMale.get()) {
                signUpCheckBoxFemale.setDisable(false);
                checkMale.set(true);
            }
        });
    }

    private void createUser() {
        DatabaseHandler databaseHandler = new DatabaseHandler();

        String name = signUpFirstName.getText();
        String lastName = signUpLastName.getText();
        String userName = signUpUsername.getText();
        String password = signUpPassword.getText();
        String location = signUpLocation.getText();

        String gender = "";
        if (signUpCheckBoxMale.isSelected()) gender = "Male";
        else gender = "Female";

        if (name.equals("") || lastName.equals("") || userName.equals("") || password.equals("") || location.equals("")) {

            ImageView imageView = new ImageView(String.valueOf(getClass().getResource("/app/assets/icon_fail.png")));
            ImageView imageView1 = new ImageView(String.valueOf(getClass().getResource("/app/assets/fill.png")));

            pane.getChildren().add(imageView);
            pane1.getChildren().add(imageView1);



        } else {

            User user = new User(name, lastName, userName, password, location, gender);
            databaseHandler.signUpUser(user);
        }
    }

}
