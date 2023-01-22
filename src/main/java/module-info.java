module app.todoappjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens app.todoappjavafx to javafx.fxml;
    exports app.todoappjavafx;
    exports app.todoappjavafx.controller;
    opens app.todoappjavafx.controller to javafx.fxml;
}