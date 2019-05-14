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
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private List<String> list;


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
        String infoquery = "{CALL updateInfo(?,?,?,?,?,?)}";

        try {
            connect.getConnection().setAutoCommit(false);
            CallableStatement statement2 = connect.getConnection().prepareCall(infoquery);
            statement2.setString(1, User.getUser().getUserName());
            statement2.setString(2, password.getText());
            statement2.setString(3, firstName.getText());
            statement2.setString(4, lastName.getText());
            statement2.setString(5, email.getText());
            statement2.setString(6, phoneNumber.getText());
            statement2.executeQuery();
            if (list != null) {
                String adressquery = "{CALL insertToShippingAddress(?,?)}";
                CallableStatement statement3 = connect.getConnection().prepareCall(adressquery);
                String userName = User.getUser().getUserName();
                for (String s : list) {
                    statement3.setString(1, userName);
                    statement3.setString(2, s);
                    statement3.executeQuery();

                }
            }


            updateCurrentUser();
            Navigate.goToPage("../View/ProfileView.fxml", event, getClass());
            connect.getConnection().commit();
        } catch (SQLException e) {
            try {
                connect.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connect.getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    private void updateCurrentUser() {

        user.setPassword(password.getText());
        user.setFirstName(firstName.getText());
        user.setLastName(lastName.getText());
        user.setEmail(email.getText());
        user.setPhoneNumber(phoneNumber.getText());
        user.setShippingAddress(shipAddress.getItems());

    }

    public void addToAddresses(ActionEvent actionEvent) throws IOException {
        if (list == null) list = new ArrayList<>();
        if (newAddress.getText().length() > 0) {

            list.add(newAddress.getText());

        }
        shipAddress.getItems().add(newAddress.getText());


    }

    public void goToHome(ActionEvent actionEvent) throws IOException {

        Navigate.goToPage("../View/HomePageView.fxml", actionEvent, getClass());

    }
}


