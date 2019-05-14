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


import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SignUpController {

    @FXML
    private TextField userName;
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
    private ListView<String> shipAddress;
    @FXML
    private Button add;
    @FXML
    private Button submit;

    private List<String> list;


    public void signup(ActionEvent actionEvent) {
        DbConnect connect = DbConnect.getInstance();
        String signUpQuery = "{CALL signup(?,?,?,?,?,?,?)}";
        String adressquery = "{CALL insertToShippingAddress(?,?)}"; // user id
        try {

            connect.getConnection().setAutoCommit(false);
            CallableStatement statement2 = connect.getConnection().prepareCall(signUpQuery);

            statement2.setString(1, userName.getText());
            statement2.setString(2, password.getText());
            statement2.setString(3, firstName.getText());
            statement2.setString(4, lastName.getText());
            statement2.setString(5, email.getText());
            statement2.setString(6, phoneNumber.getText());
            statement2.setBoolean(7, false);
            ResultSet rs = statement2.executeQuery();
            CallableStatement statement3 = connect.getConnection().prepareCall(adressquery);
            for (String s : list) {
                if (s.length() > 0) {
                    if (rs.next()) {
                        statement3.setString(1, String.valueOf(rs.getInt(1))); //to be updated
                    }
                    statement3.setString(2, s);
                    statement3.executeQuery();
                }
            }
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("../View/LogINView.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();

            connect.getConnection().commit();
            connect.getConnection().setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connect.getConnection() != null) {
                    connect.getConnection().rollback();
                    connect.getConnection().setAutoCommit(true);
                }
            } catch (SQLException ex) {

                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAddress(ActionEvent actionEvent) {

        if (list == null) list = new ArrayList<String>();
        if (newAddress.getText().length() > 0) {
            list.add(newAddress.getText());
        }
        shipAddress.getItems().add(newAddress.getText());


    }

    public void deleteAddress(ActionEvent actionEvent) {

        if (list == null) return;
        if (newAddress.getText().length() > 0) {
            list.remove(newAddress.getText());
            shipAddress.getItems().remove(newAddress.getText());
        }
    }

    public void exit(ActionEvent event) throws IOException {

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../View/HomePageView.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

}
