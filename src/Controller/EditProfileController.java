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

public class EditProfileController {

    @FXML
    private TextField password;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField newAddress;
    @FXML
    private ComboBox<String> shipAddress;
    @FXML
    private Button add;
    @FXML
    private Button submit;

    private User user = User.getUser();


    @FXML
    public void initialize() {
        password.setText(user.getPassword());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        phoneNumber.setText(user.getPhoneNumber());
        ObservableList<String> items = FXCollections.observableArrayList(user.getShippingAddress());
        shipAddress.setItems(items);
    }

    public void submitEdit(ActionEvent event) throws IOException {

        DbConnect connect = DbConnect.getInstance();
        StringBuilder stringBuilder = new StringBuilder("Update 'usersInfo' SET ");
        stringBuilder.append("password=").append(password.getText()).append("and ");
        stringBuilder.append("firstName=").append(firstName.getText()).append("and ");
        stringBuilder.append("lastName=").append(lastName.getText()).append("and ");
        stringBuilder.append("email=").append(email.getText()).append("and ");
        stringBuilder.append("phoneNumber=").append(phoneNumber.getText());
        try {
            connect.excuteQuery(stringBuilder.toString());
            user.setPassword(password.getText());
            user.setFirstName(firstName.getText());
            user.setLastName(lastName.getText());
            user.setEmail(email.getText());
            user.setPhoneNumber(phoneNumber.getText());
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("../View/ProfileView.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            System.out.print("here");
            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void addToAddresses(ActionEvent actionEvent) {

        if (newAddress.getText().length() > 0) {
            DbConnect connect = DbConnect.getInstance();
            String string = "insert into shippingAddress values (" + user.getUserName() + "," + newAddress.getText() + ")";
            //connect.excuteQuery(string);
        }

    }
}


