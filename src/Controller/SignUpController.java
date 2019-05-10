package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
    private ComboBox<String> shipAddress;
    @FXML
    private Button add;
    @FXML
    private Button submit;

    private List<String> list;


    public void signup(ActionEvent actionEvent) {
        StringBuilder query = new StringBuilder("insert into usersInfo values (");
        query.append(userName.getText()).append(",");
        query.append(password.getText()).append(",");
        query.append(firstName.getText()).append(",");
        query.append(lastName.getText()).append(",");
        query.append(email.getText()).append(",");
        query.append(phoneNumber.getText()).append(");");
        DbConnect connect = DbConnect.getInstance();
        try {
            connect.excuteQuery(query.toString());
            query = new StringBuilder("insert into users values (" + userName.getText() + "," + password.getText() + ")");
            connect.excuteQuery(query.toString());
            for (String s : list) {
                query = new StringBuilder("insert into shippingAddress values (").append(userName.getText()).append(",");
                query.append(s).append(")");
                connect.excuteQuery(query.toString());
            }

        } catch (SQLException e) {
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
}
