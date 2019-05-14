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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {

    @FXML
    private Label userName;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label email;
    @FXML
    private Label phoneNumber;
    @FXML
    private ListView<String> shipAddress;
    @FXML
    private Button edit;

    @FXML
    public void initialize() {
        User user = User.getUser();
        userName.setText(user.getUserName());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        phoneNumber.setText(user.getPhoneNumber());
        ObservableList<String> items = FXCollections.observableArrayList(user.getShippingAddress());
        shipAddress.setItems(items);
    }

    public void editProfile(ActionEvent event) throws IOException {

        Navigate.goToPage("../View/EditProfileView.fxml", event, getClass());

    }


    public void goHome(ActionEvent actionEvent) throws IOException {

        Navigate.goToPage("../View/HomePageView.fxml", actionEvent, getClass());

    }
}
