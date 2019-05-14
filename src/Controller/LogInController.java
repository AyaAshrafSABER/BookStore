package Controller;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LogInController {

    @FXML
    private TextField password;
    @FXML
    private TextField userName;
    @FXML
    private Button submit;
    @FXML
    private Hyperlink signUP;

    @FXML
    public void login(ActionEvent event) throws IOException {
        try {
            if (User.loginUser(userName.getText(), password.getText()) != null)
                Navigate.goToPage("../View/HomePageView.fxml", event, getClass());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void signUp(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../View/SignUpView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
}
